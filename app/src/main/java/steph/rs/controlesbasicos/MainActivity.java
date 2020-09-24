package steph.rs.controlesbasicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DataBase db;
    Cursor c;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    ArrayList<String> copyStringArrayList = new ArrayList<String>();
    ArrayAdapter<String> stringArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton apdBAgregar = (FloatingActionButton)findViewById(R.id.apdBAgregar);
        apdBAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar("nuevo", new String[]{});
            }
        });
        obbtenerDatos();
        buscar();
    }

    private void buscar() {
        final TextView tempVal = (TextView)findViewById(R.id.txtBuscarProductos);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                stringArrayList.clear();
                if( tempVal.getText().toString().trim().length()<1 ){//no hay texto para buscar
                    stringArrayList.addAll(copyStringArrayList);
                } else{//hacemos la busqueda
                    for (String amigo : copyStringArrayList){
                        if(amigo.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase())){
                            stringArrayList.add(amigo);
                        }
                    }
                }
                stringArrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.mimenu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        c.moveToPosition(info.position);
        menu.setHeaderTitle(c.getString(1));
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnxAgregar:
                agregar("nuevo", new String[]{});
                return true;

            case R.id.mnxModificar:
                String[] dataAmigo = {
                       c.getString(0),//idAmigo
                       c.getString(1),//nombre
                        c.getString(2),//telefono
                       c.getString(3),//direccion
                       c.getString(4) //email
                };
                agregar("modificar",dataAmigo);
                return true;

            case R.id.mnxEliminar:
                AlertDialog eliminar =  eliminarProducto();
                eliminar.show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    AlertDialog eliminarProducto() {
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(MainActivity.this);
        confirmacion.setTitle(c.getString(1));
        confirmacion.setMessage("SEGURO DE ELIMINAR ESTE REGISTRO?");
        confirmacion.setPositiveButton(" SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.mantenimientoProductos("ELIMINAR",new String[]{c.getString(0)});
                obbtenerDatos();
                Toast.makeText(getApplicationContext(), "EL REGISTRO SE HA ELIMINADO SATISFACTORIAMENTE.",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        confirmacion.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                Toast.makeText(getApplicationContext(), "ACCION CANCELADA",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        return confirmacion.create();

    }

    private void obbtenerDatos() {
        db = new DataBase(getApplicationContext(), "", null, 1);
        c = db.mantenimientoProductos("consultar", null);
        if( c.moveToFirst() ){ //hay registro en la BD que mostrar
            mostrarDatosP();
        } else{ //No tengo registro que mostrar.
            Toast.makeText(getApplicationContext(), "No hay registros de amigos que mostrar",Toast.LENGTH_LONG).show();
            agregar("nuevo", new String[]{});
        }
    }

    private void mostrarDatosP() {
        stringArrayList.clear();
        ListView ltsProductos = (ListView)findViewById(R.id.ltsProductos);
        stringArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, stringArrayList);
        ltsProductos.setAdapter(stringArrayAdapter);
        do {
            stringArrayList.add(c.getString(1));
        }while(c.moveToNext());
        copyStringArrayList.clear();//limpiamos la lista de amigos
        copyStringArrayList.addAll(stringArrayList);//creamos la copia de la lista de amigos...
        stringArrayAdapter.notifyDataSetChanged();
        registerForContextMenu(ltsProductos);
    }

    private void agregar(String accion, String[] dataProducto) {
        Bundle enviarParametros = new Bundle();
        enviarParametros.putString("accion",accion);
        enviarParametros.putStringArray("dataAmigo",dataProducto);
        Intent agregarp = new Intent(MainActivity.this, Productos.class);
        agregarp.putExtras(enviarParametros);
        startActivity(agregarp);
    }


}

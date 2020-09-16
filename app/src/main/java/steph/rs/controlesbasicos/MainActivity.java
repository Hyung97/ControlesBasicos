package steph.rs.controlesbasicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DataBase db;
    public Cursor c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        obbtenerDatos();
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
            case R.id.mnxModificar:
                try {
                    String productos[] = {
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getString(4)
                    };
                    Bundle bundle = new Bundle();
                    bundle.putString("accion", "modificar");
                    bundle.putString("id", c.getString(0));
                    bundle.putStringArray("productos", productos);

                    Intent iproducto = new Intent(MainActivity.this, Productos.class);
                    iproducto.putExtras(bundle);
                    startActivity(iproducto);
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "ERROR: " +e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.mxnEliminar:
                AlertDialog confirmacion = eliminarProducto();
                confirmacion.show();
                return  true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private AlertDialog eliminarProducto() {
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(MainActivity.this);
        confirmacion.setTitle(c.getString(1));
        confirmacion.setMessage("SEGURO DE ELIMINAR ESTE REGISTRO?");
        confirmacion.setPositiveButton(" SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.eliminarProducto(c.getString(0));
                dialogInterface.cancel();
                Toast.makeText(MainActivity.this, "EL REGISTRO HA SIDO ELIMINADO SATISFACTORIAMENTE",Toast.LENGTH_SHORT).show();
            }
        });
        confirmacion.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.cancel();
                Toast.makeText(MainActivity.this, "ACCION CANCELADA", Toast.LENGTH_LONG).show();
            }
        });
        return confirmacion.create();

    }

    private void obbtenerDatos() {
        db = new DataBase(MainActivity.this,"", null, 1);
        c = db.consultarProductos();
        if (c.moveToFirst()){
            ListView ltsProductos = findViewById(R.id.ltsProductos);
            final ArrayList<String> alProductos = new ArrayList<>();
            final ArrayAdapter<String> aaProductos = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, alProductos);
            ltsProductos.setAdapter(aaProductos);
            do{
                alProductos.add(c.getString(1));
            } while (c.moveToNext());
            aaProductos.notifyDataSetChanged();
            registerForContextMenu(ltsProductos);
        }else{
            Toast.makeText(MainActivity.this, "NO HAY REGISTROS QUE MOSTRAR", Toast.LENGTH_LONG).show();
        }
    }

    public void registrarProductos(View view) {
        Intent iagregar = new Intent (MainActivity.this, Productos.class);
        startActivity(iagregar);
    }
}

package steph.rs.controlesbasicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
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
    prod producs;
    ArrayList<prod> stringArrayList = new ArrayList<prod>();
    ArrayList<prod> copyStringArrayList = new ArrayList<prod>();
    ListView ltsProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton apdBAgregar =(FloatingActionButton)findViewById(R.id.apdBAgregar);
        apdBAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar("nuevo", new String[]{});
            }
        });
        obbtenerDatos();
        buscar();
    }

    void buscar() {
        final TextView tempVal = (TextView)findViewById(R.id.txtBuscarProductos);
        tempVal.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    stringArrayList.clear();
                    if (tempVal.getText().toString().trim().length() < 1) {//no hay texto para buscar
                        stringArrayList.addAll(copyStringArrayList);
                    } else {//hacemos la busqueda
                        for (prod pr : copyStringArrayList) {
                            String producto = pr.getProducto();
                            String codigo = pr.getCodigo();
                            String marca = pr.getMarca();
                            if (producto.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase())) {
                                stringArrayList.add(pr);
                            }
                        }
                    }
                    AdapterImage adaptadorImage = new AdapterImage(getApplicationContext(), stringArrayList);
                    ltsProductos.setAdapter(adaptadorImage);
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
                }
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
        inflate.inflate(R.menu.menu, menu);
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
                String[] dataProd = {
                        c.getString(0),//id
                        c.getString(1),//producto
                        c.getString(2),//codigo
                        c.getString(3),//marca
                        c.getString(4),//descripcion
                        c.getString(5), //presentacion
                        c.getString(6), //precio
                        c.getString(7)  //urlImg
                };
                agregar("modificar",dataProd);
                return true;

            case R.id.mnxEliminar:
                android.app.AlertDialog eliminar =  eliminarProducto();
                eliminar.show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    android.app.AlertDialog eliminarProducto() {
        android.app.AlertDialog.Builder confirmacion = new android.app.AlertDialog.Builder(MainActivity.this);
        confirmacion.setTitle(c.getString(1));
        confirmacion.setMessage("ESTA SEGURO DE ELIMINAR ESTE PRODUCTO?");
        confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.mantenimientoProductos("eliminar",new String[]{c.getString(0)});
                obbtenerDatos();
                Toast.makeText(getApplicationContext(), "PRODUCTO ELIMINADO CON EXITO",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        confirmacion.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "ELIMINACION CANCELADA SATISFACTORIAMENTE",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        return confirmacion.create();
    }

    void obbtenerDatos() {
        db = new DataBase(getApplicationContext(), "", null, 1);
        c = db.mantenimientoProductos("consultar", null);
        if( c.moveToFirst() ){ //hay registro en la BD que mostrar
            mostrarDatosP();
        } else{ //No tengo registro que mostrar.
            Toast.makeText(getApplicationContext(), "NO HAY REGISTROS QUE MOSTRAR",Toast.LENGTH_LONG).show();
            agregar("nuevo",    new String[]{});
        }
    }

    void mostrarDatosP() {
        stringArrayList.clear();
        ltsProductos = (ListView)findViewById(R.id.ltsProductos);
        do {
            producs = new prod(c.getString(0),c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));
            stringArrayList.add(producs);
        }while(c.moveToNext());
        AdapterImage adaptadorImg = new AdapterImage(getApplicationContext(), stringArrayList);
        ltsProductos.setAdapter(adaptadorImg);
        copyStringArrayList.clear();//limpiamos la lista de los productos
        copyStringArrayList.addAll(stringArrayList);//creamos la copia de la lista...
        registerForContextMenu(ltsProductos);
    }
    void agregar(String accion, String[] dataProducto) {
        Bundle enviarParametros = new Bundle();
        enviarParametros.putString("accion",accion);
        enviarParametros.putStringArray("data",dataProducto);
        Intent agregarProducto = new Intent(MainActivity.this, Agregar_Producto.class);
        agregarProducto.putExtras(enviarParametros);
        startActivity(agregarProducto);
    }

}

class prod {
    String id;
    String producto;
    String codigo;
    String marca;
    String descripcion;
    String presentacion;
    String precio;
    String urlImg;

    public prod(String id, String producto, String marca, String descripcion, String precio, String urlImg) {
        this.id = id;
        this.producto = producto;
        this.codigo = codigo;
        this.marca = marca;
        this.descripcion = descripcion;
        this.presentacion = presentacion;
        this.precio = precio;
        this.urlImg = urlImg;
    }

    public String getId() {
        return id;
    }
    public void setId(String id)    {
        this.id = id;
    }


    public String getProducto() {
        return producto;
    }
    public void setProducto(String producto) {
        this.producto = producto;
    }


   public  String getCodigo(){
        return  codigo;
   }
   public void setCodigo(String codigo){
        this. codigo= codigo;
   }


    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.producto = marca;
    }


    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }


    public String getPrecio() {
        return precio;
    }
    public void setPrecio(String precio) {
        this.precio = precio;
    }


    public String getUrlImg() {
        return urlImg;
    }
    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}





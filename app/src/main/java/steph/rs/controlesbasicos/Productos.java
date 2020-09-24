package steph.rs.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Productos extends AppCompatActivity {
    DataBase productos;
    String accion="nuevo";
    String id ="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        Button apdBAgregar = (Button)findViewById(R.id.apdBAgregar);
        apdBAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tempVal = (TextView)findViewById(R.id.producto);
                String nombre = tempVal.getText().toString();
                tempVal = (TextView)findViewById(R.id.marca);
                String tel = tempVal.getText().toString();
                tempVal = (TextView)findViewById(R.id.descripcion);
                String direccion = tempVal.getText().toString();
                tempVal = (TextView)findViewById(R.id.precio);
                String email = tempVal.getText().toString();
                String[] data = {id,nombre,tel,direccion,email};
                productos = new DataBase(getApplicationContext(),"", null, 1);
                productos.mantenimientoProductos(accion, data);
                Toast.makeText(getApplicationContext(),"Registro de amigo insertado con exito", Toast.LENGTH_LONG).show();
            }
        });
        apdBAgregar = (Button)findViewById(R.id.btnMostrarAmigos);
        apdBAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarListaProducto();
            }
        });
        mostrarDatos();
    }

    private void mostrarListaProducto() {
        Intent mostarProduct = new Intent(Productos.this, MainActivity.class);
        startActivity(mostarProduct);
    }

    private void mostrarDatos() {
        try {
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");
            if (accion.equals("modificar")) {
                String[] dataProductos = recibirParametros.getStringArray("dataProductos");

                id = dataProductos[0];

                TextView tempVal =(TextView)findViewById(R.id.producto);
                tempVal.setText(dataProductos[1]);
                tempVal = (TextView)findViewById(R.id.marca);
                tempVal.setText(dataProductos[2]);
                tempVal = (TextView)findViewById(R.id.descripcion);
                tempVal.setText(dataProductos[3]);
                tempVal = (TextView)findViewById(R.id.precio);
                tempVal.setText(dataProductos[4]);
            }
        }catch (Exception e){
            Toast.makeText(Productos.this, "Error: "+
                    e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }

    }



}
package steph.rs.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class Productos extends AppCompatActivity {
    DataBase productos;
    String accion="nuevo";
    String id ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        mostrarDatos();
    }

    private void mostrarDatos() {
        try {
            Bundle bundle = getIntent().getExtras();
            accion = bundle.getString("accion");
            if (accion.equals("modificar")) {
                id = bundle.getString("id");

                String product[] = bundle.getStringArray("product");

                TextView tempVal = findViewById(R.id.producto);
                tempVal.setText(product[0].toString());
                tempVal =  findViewById(R.id.marca);
                tempVal.setText(product[1].toString());
                tempVal =  findViewById(R.id.descripcion);
                tempVal.setText(product[2].toString());
                tempVal =  findViewById(R.id.precio);
                tempVal.setText(product[2].toString());
            }
        }catch (Exception e){
            Toast.makeText(Productos.this, "Error: "+
                    e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void saveInfo(View view) {
       try {
           TextView tempVal = findViewById(R.id.producto);
           String prod = tempVal.getText().toString();

           tempVal = findViewById(R.id.marca);
           String marc = tempVal.getText().toString();

           tempVal =  findViewById(R.id.descripcion);
           String descrip = tempVal.getText().toString();

           tempVal = (TextView) findViewById(R.id.precio);
           String precio = tempVal.getText().toString();

           productos = new DataBase(Productos.this, "", null, 1);
           productos.guardarProducto(prod, precio, descrip, marc, id, accion);

           Intent imostrar = new Intent(Productos.this, MainActivity.class);
           startActivity(imostrar);
                 } catch (Exception ex) {
                     Toast.makeText(Productos.this, "ERROR: "+ ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
              }


}
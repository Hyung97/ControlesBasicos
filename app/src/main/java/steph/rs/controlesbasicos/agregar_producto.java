package steph.rs.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class agregar_producto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        Button btnMostrar = findViewById(R.id.btnMostarProducto);
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarProducto();
            }
        });
        Button btnGuardar = findViewById(R.id.btnAgregarProducto);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarProducto();
            }
        });
    }

    private void mostrarProducto(){
        Intent mostrarAmigos = new Intent(agregar_producto.this, MainActivity.class);
        startActivity(mostrarAmigos);
    }

    private void guardarProducto(){
        TextView tempVal = findViewById(R.id.producto);
        String producto = tempVal.getText().toString();

        tempVal = findViewById(R.id.marca);
        String marca = tempVal.getText().toString();

        tempVal = findViewById(R.id.descripcion);
        String descripcion = tempVal.getText().toString();

        tempVal = findViewById(R.id.precio);
        String precio = tempVal.getText().toString();


        try {
            JSONObject datosPr = new JSONObject();
            datosPr.put("codigo", producto);
            datosPr.put("nombre", marca);
            datosPr.put("direccion", descripcion);
            datosPr.put("telefono", precio);

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private class enviarDatos extends AsyncTask<String,String, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(String... parametros) {
            StringBuilder stringBuilder = new StringBuilder();
            String jsonResponse = null;
            String jsonDatos = parametros[0];
            BufferedReader reader;
            try {
                URL url = new URL("http://192.168.1.15:5984/db_agenda/");
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setRequestProperty("Accept","application/json");

                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(jsonDatos);
                writer.close();

                InputStream inputStream = urlConnection.getInputStream();
            }catch (Exception ex){
                //
            }
            return null;
        }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }
}
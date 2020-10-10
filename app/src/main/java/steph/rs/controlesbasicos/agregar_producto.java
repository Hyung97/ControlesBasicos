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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class agregar_producto extends AppCompatActivity {
    String resp, accion, id, rev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        try {
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
            mostrarDatos();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "ERROR AL AGREGAR PRODUCTO: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
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
            JSONObject DBProducto = new JSONObject();
            if (accion.equals("modificar")){
                DBProducto.put("_id",id);
                DBProducto.put("_rev",rev);
            }
            DBProducto.put("producto", producto);
            DBProducto.put("marca", marca);
            DBProducto.put("descripcion", descripcion);
            DBProducto.put("precio", precio);
            enviarDatos objGuardar = new enviarDatos();
            objGuardar.execute(DBProducto.toString());
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "ERROR: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void mostrarProducto(){
        Intent mostrarProductos = new Intent(agregar_producto.this, MainActivity.class);
        startActivity(mostrarProductos);
    }
    void mostrarDatos(){
        try {
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");
            if (accion.equals("modificar")){
                JSONObject DBProducto = new JSONObject(recibirParametros.getString("DB")).getJSONObject("value");
                TextView tempVal = (TextView)findViewById(R.id.producto);
                tempVal.setText(DBProducto.getString("producto"));

                tempVal = (TextView)findViewById(R.id.marca);
                tempVal.setText(DBProducto.getString("marca"));

                tempVal = (TextView)findViewById(R.id.descripcion);
                tempVal.setText(DBProducto.getString("descripcion"));

                tempVal = (TextView)findViewById(R.id.precio);
                tempVal.setText(DBProducto.getString("precio"));
                id = DBProducto.getString("_id");
                rev = DBProducto.getString("_rev");
            }
        }catch (Exception ex){
            ///
        }
    }
    private class enviarDatos extends AsyncTask<String,String, String>{
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(String... parametros) {
            StringBuilder stringBuilder = new StringBuilder();
            String jsonResponse = null;
            String jsonDatos = parametros[0];
            BufferedReader reader;
            try {
                URL url = new URL("http://192.168.1.7:5984/db_agenda/");
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
                if(inputStream==null){
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                resp = reader.toString();
                String inputLine;
                StringBuffer stringBuffer = new StringBuffer();
                while ((inputLine=reader.readLine())!= null){
                    stringBuffer.append(inputLine+"\n");
                }
                if(stringBuffer.length()==0){
                    return null;
                }
                jsonResponse = stringBuffer.toString();
                return jsonResponse;
            }catch (Exception ex){
                //
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject jsonObject = new JSONObject(s);
                if(jsonObject.getBoolean("ok")){
                    Toast.makeText(getApplicationContext(), "DATOS DEL PRODUCTO GUARDADOS CON ÉXITO", Toast.LENGTH_SHORT).show();
                    mostrarProducto();
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR AL INTENTAR GUARDAR LOS DATOS", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "ERROR AL GUARDAR PRODUCTO: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
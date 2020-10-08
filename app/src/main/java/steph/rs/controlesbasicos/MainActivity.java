package steph.rs.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    JSONArray datosJSON;
    JSONObject jsonObject;
    int position = 0;


    //Read text input stream
    InputStreamReader isReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        obtenerDatos objObtener = new obtenerDatos();
        objObtener.execute();

        FloatingActionButton btn = findViewById(R.id.btnAgregar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                producto_nuevo();
            }
        });
    }

    public void producto_nuevo(){
        Intent agregar_producto = new Intent(MainActivity.this, agregar_producto.class);
        startActivity(agregar_producto);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.menu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        try {
            datosJSON.getJSONObject(info.position);
            menu.setHeaderTitle(datosJSON.getJSONObject(info.position).getJSONObject("value").getString("nombre").toString());
            position=info.position;
        } catch (Exception ex) {

        }
    }

    private class obtenerDatos extends AsyncTask<Void, Void, String>{
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null){
                    result.append(line);
                }
            } catch (Exception ex){
              //
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                jsonObject = new JSONObject(s);
                datosJSON = jsonObject.getJSONArray("rows");
                mostrarDatos();
            } catch (Exception ex) {
                Toast.makeText(MainActivity.this, "ERROR EN LA TRANSFERENCIA DE DATOS " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
    }

    }

    private void mostrarDatos(){
        ListView ltsproductos = findViewById(R.id.ltsProductos);
        try {
            final ArrayList<String> arrayList = new ArrayList<>();
            final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
            ltsproductos.setAdapter(stringArrayAdapter);

            for (int i = 0; i < datosJSON.length(); i++) {
                stringArrayAdapter.add(datosJSON.getJSONObject(i).getJSONObject("value").getString("nombre"));
            }
            stringArrayAdapter.notifyDataSetChanged();
            registerForContextMenu(ltsproductos);
        }catch (Exception ex){
            Toast.makeText(MainActivity.this, "ERROR AL MOSTRAR LOS DATOS " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
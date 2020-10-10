package steph.rs.controlesbasicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
    Integer positionn;
    ArrayList<String> arrayList =new ArrayList<String>();
    ArrayList<String> copyStringArrayList = new ArrayList<String>();
    ArrayAdapter<String> stringArrayAdapter;

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
                agregarNuevoProducto("nuevo", jsonObject);
            }
        });
        buscarProducto();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        try {
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
            positionn = adapterContextMenuInfo.position;
            menu.setHeaderTitle(datosJSON.getJSONObject(positionn).getString("producto"));
        }catch (Exception ex){
            //
        }
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mxnAgregar:
                agregarNuevoProducto("NUEVO", jsonObject);
                return true;
            case R.id.mxnModificar:
                try {
                    agregarNuevoProducto("MODIFICAR", datosJSON.getJSONObject(positionn));
                }catch (Exception ex){}
                return true;
            case R.id.mxnEliminar:
                AlertDialog eliminarPrdt =  eliminarPrdt();
                eliminarPrdt.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    private class obtenerDatos extends AsyncTask<Void,Void, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("http://192.168.1.7:5984/db_agenda/");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea;
                while ((linea = reader.readLine()) != null) {
                    result.append(linea);
                }
            } catch (Exception ex) {
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
                Toast.makeText(MainActivity.this, "ERROR AL ENVIAR LOS DATOS " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
    void buscarProducto(){
        final TextView tempVal = (TextView)findViewById(R.id.txtBuscarProductos);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayList.clear();
                if( tempVal.getText().toString().trim().length()<1 ){
                    arrayList.addAll(copyStringArrayList);
                } else{
                    for (String prdt : copyStringArrayList){
                        if(prdt.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase())){
                            arrayList.add(prdt);
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

    private void mostrarDatos(){
        ListView ltsPrdt = findViewById(R.id.ltsProductos);
        try {
            arrayList.clear();
            stringArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
            ltsPrdt.setAdapter(stringArrayAdapter);
            for (int i = 0; i < datosJSON.length(); i++) {
                stringArrayAdapter.add(datosJSON.getJSONObject(i).getJSONObject("value").getString("producto"));
            }
            copyStringArrayList.clear();
            copyStringArrayList.addAll(arrayList);
            stringArrayAdapter.notifyDataSetChanged();
            registerForContextMenu(ltsPrdt);
        }catch (Exception ex){
            Toast.makeText(MainActivity.this, "ERROR AL MOSTRAR LOS DATOS " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void agregarNuevoProducto(String accion, JSONObject jsonObject){
        try {
            Bundle enviarParametros = new Bundle();
            enviarParametros.putString("accion",accion);
            enviarParametros.putString("DB",jsonObject.toString());
            Intent agregarProdcto = new Intent(MainActivity.this, agregar_producto.class);
            agregarProdcto.putExtras(enviarParametros);
            startActivity(agregarProdcto);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "ERROR "+ e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private class eliminarDatosProductos extends AsyncTask<String,String, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(String... parametros) {
            StringBuilder stringBuilder = new StringBuilder();
            String jsonResponse = null;
            try {
                URL url = new URL("" +
                        datosJSON.getJSONObject(positionn).getJSONObject("value").getString("_id") + "?rev=" +
                        datosJSON.getJSONObject(positionn).getJSONObject("value").getString("_rev"));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("DELETE");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String inputLine;
                StringBuffer stringBuffer = new StringBuffer();
                while ((inputLine = reader.readLine()) != null) {
                    stringBuffer.append(inputLine + "\n");
                }
                if (stringBuffer.length() == 0) {
                    return null;
                }
                jsonResponse = stringBuffer.toString();
                return jsonResponse;
            } catch (Exception ex) {
                //
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("ok")) {
                    Toast.makeText(getApplicationContext(), "PRODUCTO GUARDADO CON ÉXITO", Toast.LENGTH_SHORT).show();
                    obtenerDatos objObtener = new obtenerDatos();
                    objObtener.execute();
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR AL INTENTAR GUARDAR EL PRODUCTO", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "ERROR AL GUARDAR EL PRODUCTO: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
    AlertDialog eliminarPrdt(){
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(MainActivity.this);
        try {
            confirmacion.setTitle(datosJSON.getJSONObject(positionn).getJSONObject("value").getString("producto"));
            confirmacion.setMessage("¿ESTÁS SEGURO DE ELIMINAR EL PRODUCTO?");
            confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    eliminarDatosProductos objEliminar = new eliminarDatosProductos();
                    objEliminar.execute();
                    Toast.makeText(getApplicationContext(), "PRODUCTO ELIMINADO CON ÉXITO.", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            });
            confirmacion.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(), "ELIMINACION DEL PRODUCTO CANCELADA.", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            });
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "ERROR AL MOSTRAR "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return confirmacion.create();
    }
}

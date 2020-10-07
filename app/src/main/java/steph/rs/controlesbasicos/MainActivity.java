package steph.rs.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    JSONArray datosJSON;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        obtenerDatos objObtener = new obtenerDatos();
        objObtener.execute();
    }

    private class obtenerDatos extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            return null;
        }
    }

}
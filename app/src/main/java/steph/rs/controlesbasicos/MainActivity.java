package steph.rs.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //direcciones direccion = new direcciones();
    dirrecciones_paises miDireccion = new dirrecciones_paises();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tbhConversores = (TabHost)findViewById(R.id.tbhDirecciones);
        tbhConversores.setup();

        tbhConversores.addTab(tbhConversores.newTabSpec("Direccion").setContent(R.id.tabDireccion).setIndicator("DIRECCION", null));
        tbhConversores.addTab(tbhConversores.newTabSpec("Pais").setContent(R.id.tabPais).setIndicator("PAIS", null));

        final Spinner spnMun = (Spinner)findViewById(R.id.spnMun);

        Spinner spnDepto = (Spinner)findViewById(R.id.spnDepto);
        spnDepto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spnMun.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,miDireccion.obtenerMunicipio(position) ));
                Toast.makeText(getApplicationContext(), "Indice: "+ position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btnObtenerPais = (Button)findViewById(R.id.btnObtenerPais);
        btnObtenerPais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spnPaises = (Spinner)findViewById(R.id.spnPaises);
                spnPaises.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,miDireccion.obtenerPaises()));
            }
        });


    }



}
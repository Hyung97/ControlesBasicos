package steph.rs.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (RelativeLayout) findViewById(R.id.activity_main);
        Button btnMostrar =(Button)findViewById(R.id.button);
        Toast.makeText(getApplicationContext(),"Este Es Un Mensaje con Toast",Toast.LENGTH_LONG).show();
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(layout,"Tutorial de Snackbar",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

}
package steph.rs.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void calcular(View view) {
        TextView tempval = (TextView) findViewById(R.id.txtNum1);
        double num1 = Double.parseDouble(tempval.getText().toString());

        tempval = (TextView) findViewById(R.id.txtNum2);
        double num2 = Double.parseDouble(tempval.getText().toString());

        double respuesta = num1 + num2;
        tempval = (TextView) findViewById(R.id.lblRespuesta);
        tempval.setText("Respuesta: " + respuesta);
    }
}
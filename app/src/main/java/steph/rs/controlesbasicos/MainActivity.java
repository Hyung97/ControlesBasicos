package steph.rs.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button AppM = (Button) findViewById(R.id.Messaging_Apps);
        AppM.setOnClickListener((View.OnClickListener) this);
        Button PhoneM = (Button) findViewById(R.id.phoneM);
        PhoneM.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.Messaging_Apps :
                Intent moveIntent = new Intent(MainActivity.this, Menu.class);
                startActivity(moveIntent);
                break;
            case R.id.phoneM:
                String phoneNumber = "(+503)7849-3319";
                Intent dialPhoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNumber));
                startActivity(dialPhoneIntent);
                break;
        }
    }
}
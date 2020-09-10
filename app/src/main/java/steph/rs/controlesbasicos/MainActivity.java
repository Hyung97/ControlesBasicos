package steph.rs.controlesbasicos;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity{
    Button Play, btnRep;
    MediaPlayer Multiplayer;
    ImageView ImageV;
    MediaPlayer MultplayerVector [] = new MediaPlayer[4];
    int Rptir = 2, Position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Play = (Button)findViewById(R.id.btnPlay);
        btnRep = (Button)findViewById(R.id.btnRepeat);
        ImageV = (ImageView)findViewById(R.id.imageView);
        MultplayerVector[0] = MediaPlayer.create(this,R.raw.bomboasesino);
        MultplayerVector[1] = MediaPlayer.create(this,R.raw.tiemblo);
        MultplayerVector[2] = MediaPlayer.create(this,R.raw.atrapalosya);
        MultplayerVector[3] = MediaPlayer.create(this,R.raw.yoquierounheroe);
    }

    public void PLayPause(View view) {
        if(MultplayerVector[Position].isPlaying()){
            MultplayerVector[Position].pause();
            Play.setBackgroundResource(R.drawable.play);
            Toast.makeText(this,"Pausa",Toast.LENGTH_SHORT).show();
        }else {
            MultplayerVector[Position].start();
            Play.setBackgroundResource(R.drawable.pausa);
            Toast.makeText(this,"Play",Toast.LENGTH_SHORT).show();
        }
    }

    public void Stop(View view) {
        if(MultplayerVector[Position] != null){
            MultplayerVector[Position].stop();
            MultplayerVector[0] = MediaPlayer.create(this,R.raw.bomboasesino);
            MultplayerVector[1] = MediaPlayer.create(this,R.raw.tiemblo);
            MultplayerVector[2] = MediaPlayer.create(this,R.raw.atrapalosya);
            MultplayerVector[3] = MediaPlayer.create(this,R.raw.yoquierounheroe);
            Position = 0;
            Play.setBackgroundResource(R.drawable.play);
            ImageV.setImageResource(R.drawable.portada1);
            Toast.makeText(this,"Stop",Toast.LENGTH_SHORT).show();
        }
    }

    public void Repetir(View view) {
        if(Rptir == 1) {
            btnRep.setBackgroundResource(R.drawable.repetirno);
            Toast.makeText(this, "No repetir",Toast.LENGTH_SHORT).show();
            MultplayerVector[Position].setLooping((false));
            Rptir = 2;
        }else{
            btnRep.setBackgroundResource(R.drawable.repetir);
            Toast.makeText(this,
                    "Repetir",Toast.LENGTH_SHORT).show();
            MultplayerVector[Position].setLooping((true));
            Rptir = 1;
        }
    }

    public void Anterior(View view) {
        if(Position >= 1) {
            if(MultplayerVector[Position].isPlaying()){
                MultplayerVector[Position].stop();
                MultplayerVector[0] = MediaPlayer.create(this,R.raw.bomboasesino);
                MultplayerVector[1] = MediaPlayer.create(this,R.raw.tiemblo);
                MultplayerVector[2] = MediaPlayer.create(this,R.raw.atrapalosya);
                MultplayerVector[3] = MediaPlayer.create(this,R.raw.yoquierounheroe);
                Position--;
                if(Position == 0){
                    ImageV.setImageResource(R.drawable.portada1);
                }else if (Position == 1){
                    ImageV.setImageResource(R.drawable.portada2);
                }else if (Position == 2){
                    ImageV.setImageResource(R.drawable.portada3);
                }
                MultplayerVector[Position].start();
            }else {
                Position--;
                if(Position == 0){
                    ImageV.setImageResource(R.drawable.portada1);
                }else if (Position == 1){
                    ImageV.setImageResource(R.drawable.portada2);
                }else if (Position == 2){
                    ImageV.setImageResource(R.drawable.portada3);
                }
                else if (Position == 3){
                ImageV.setImageResource(R.drawable.portada4);
            }
            }
        }else{
            Toast.makeText(this,"No hay más canciones",Toast.LENGTH_SHORT).show();
        }
    }

    public void Siguiente(View view) {
        if(Position <MultplayerVector.length -1){
            if(MultplayerVector[Position].isPlaying()){
                MultplayerVector[Position].stop();
                Position++;
                MultplayerVector[Position].start();
                if(Position == 0){
                    ImageV.setImageResource(R.drawable.portada1);
                }else if (Position == 1){
                    ImageV.setImageResource(R.drawable.portada2);
                }else if (Position == 2){
                    ImageV.setImageResource(R.drawable.portada3);
                }
                else if (Position == 3){
                    ImageV.setImageResource(R.drawable.portada4);
                }
            }else{
                Position++;
                if(Position == 0){
                    ImageV.setImageResource(R.drawable.portada1);
                }else if (Position == 1){
                    ImageV.setImageResource(R.drawable.portada2);
                }else if (Position == 2){
                    ImageV.setImageResource(R.drawable.portada3);
                }
                else if (Position == 3){
                    ImageV.setImageResource(R.drawable.portada4);
                }
            }
        }else {
            Toast.makeText(this,"No hay más canciones",Toast.LENGTH_SHORT).show();
        }
    }
}

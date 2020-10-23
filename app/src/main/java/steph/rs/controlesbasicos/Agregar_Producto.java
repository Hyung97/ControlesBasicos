package steph.rs.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Agregar_Producto extends AppCompatActivity {
    DataBase productos;
    String accion="nuevo";
    String id ="0";
    ImageView imgFoto;
    String urlCompletaImg;
    Intent takePictureIntent;
    Button btnProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar__producto);
        imgFoto = findViewById(R.id.imgFoto);

        btnProducto = findViewById(R.id.apdBAgregarProducto);
        btnProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarLista();
            }
        });
        guardarDatos();
        mostrarDatos();
        tomarFoto();
    }

    private void mostrarDatos() {
        try {
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");
            if (accion.equals("modificar")){
                String[] data = recibirParametros.getStringArray("dataAmigo");

                id = data[0];

                TextView tempVal = (TextView)findViewById(R.id.producto);
                tempVal.setText(data[1]);

                tempVal = (TextView)findViewById(R.id.marca);
                tempVal.setText(data[2]);

                tempVal = (TextView)findViewById(R.id.descripcion);
                tempVal.setText(data[3]);

                tempVal = (TextView)findViewById(R.id.precio);
                tempVal.setText(data[4]);

                urlCompletaImg = data[5];
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                imgFoto.setImageBitmap(imageBitmap);
            }
        }catch (Exception ex){
            ///
        }
    }

    private void guardarDatos() {
        btnProducto = findViewById(R.id.apdBAgregarProducto);
        btnProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tempVal = findViewById(R.id.producto);
                String producto= tempVal.getText().toString();

                tempVal = findViewById(R.id.marca);
                String marca = tempVal.getText().toString();

                tempVal = findViewById(R.id.descripcion);
                String descripcion = tempVal.getText().toString();

                tempVal = findViewById(R.id.precio);
                String precio = tempVal.getText().toString();

                String[] data = {id,producto,marca,descripcion,precio,urlCompletaImg};

                productos = new DataBase(getApplicationContext(),"", null, 1);
                productos.mantenimientoProductos(accion, data);

                Toast.makeText(getApplicationContext(),"REGISTRO REALIZADO SATISFACTORIAMENTE", Toast.LENGTH_LONG).show();
                mostrarLista();
            }
        });
    }

    private void tomarFoto() {
        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    //guardando la imagen
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    }catch (Exception ex){}
                    if (photoFile != null) {
                        try {
                            Uri photoURI = FileProvider.getUriForFile(Agregar_Producto.this, "steph.rs.controlesbasicos.fileprovider", photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, 1);
                        }catch (Exception ex){
                            Toast.makeText(getApplicationContext(), "Error Toma Foto: "+ ex.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    private void mostrarLista() {
        Intent mostrarProductos = new Intent(Agregar_Producto.this, MainActivity.class);
        startActivity(mostrarProductos);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "imagen_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if( storageDir.exists()==false ){
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        urlCompletaImg = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                imgFoto.setImageBitmap(imageBitmap);
            }
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }





}
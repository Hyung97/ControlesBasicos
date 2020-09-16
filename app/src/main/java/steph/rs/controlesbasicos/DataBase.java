package steph.rs.controlesbasicos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {
    public static final String nameDB = "DB_TIENDA"; //declaracion de la instancia de la BD
    public static final int v=1;
    String tblProductos = "CREATE TABLE Productos(idProducto integer primary key autoincrement, producto text, marca text, descripcion text, precio text, imagen text)";

    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nameDB, factory, version); //nameDB -> Creacion de la BD en SQLite...
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblProductos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void eliminarProducto (String id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from productos where idProducto ='"+ id +"'");
    }

    public Cursor consultarProductos (){
        String sql = "select * from productos order by producto asc";
        SQLiteDatabase db  = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public void guardarProducto(String prod, String precio, String descrip, String marc, String id, String accion) {
        SQLiteDatabase db = getWritableDatabase();
        if (accion.equals("modificar")) {
            db.execSQL("UPDATE PRODUCTOS SET PRODUCTOS ='" + prod + "', marca='" + marc + "', descripcion='" + descrip + ", 'precio='" + precio + "'where idProducto = '" + id + "'");
        }else{
            db.execSQL("insert into productos (producto, marca, descripcion, precio) values ('" + prod + "','" + marc + "','" + descrip + "','" + precio + "')");
        }
    }
}

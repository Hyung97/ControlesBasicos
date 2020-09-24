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

    public Cursor mantenimientoProductos(String accion, String[] data){
        SQLiteDatabase sqLiteDatabaseReadable = getReadableDatabase();
        SQLiteDatabase sqLiteDatabaseWritable=getWritableDatabase();
        Cursor cursor = null;
        switch (accion){
            case "consultar":
                cursor=sqLiteDatabaseReadable.rawQuery("SELECT * FROM Productos ORDER BY producto ASC", null);
                break;
            case "nuevo":
                sqLiteDatabaseWritable.execSQL("INSERT INTO Productos (producto, marca, descripcion, precio) VALUES('"+ data[1] +"','"+data[2]+"','"+data[3]+"','"+data[4]+"')");
                break;
            case "modificar":
                sqLiteDatabaseWritable.execSQL("UPDATE Productos SET producto='"+ data[1] +"',marca='"+data[2]+"',descripcion='"+data[3]+"',producto='"+data[4]+"' WHERE idProducto='"+data[0]+"'");
                break;
            case "eliminar":
                sqLiteDatabaseWritable.execSQL("DELETE FROM Productos WHERE idProducto='"+ data[0] +"'");
                break;
        }
        return cursor;
    }
}

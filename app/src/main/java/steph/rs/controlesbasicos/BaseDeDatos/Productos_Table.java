package steph.rs.controlesbasicos.BaseDeDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Productos_Table extends SQLiteOpenHelper {
    static String nameDB = "DB_PRODUCTOS";
    static String TableProductos = "CREATE TABLE Productos(Product_ID, Product_Name, Product_Price, Product_Photo_Path, Product_Brand)";

    public Productos_Table(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nameDB, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableProductos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void ManteniminetoProductos (String accion){
        switch (accion){
            case "Query":
                break;

            case "Insert":
                break;

            case "Update":
                break;

            case "Delete":
                break;

            default:
                break;
        }
    }
}

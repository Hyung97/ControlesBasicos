package steph.rs.controlesbasicos.esquemaSqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import steph.rs.controlesbasicos.esquemaSqlite.tablas.ProductoTabla;


public class ConexionSqliteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "venta_simple";

    public ConexionSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProductoTabla.CREAR_TABLA_PRODUCTO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ProductoTabla.ELIMINAR_TABLA_PRODUCTO);

    }
}
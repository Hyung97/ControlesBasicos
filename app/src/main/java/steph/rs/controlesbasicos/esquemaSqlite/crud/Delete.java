package steph.rs.controlesbasicos.esquemaSqlite.crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import steph.rs.controlesbasicos.esquemaSqlite.ConexionSqliteHelper;
import steph.rs.controlesbasicos.esquemaSqlite.tablas.ProductoTabla;

public class Delete {

    public static void eliminar(Context context, int codigo, String tabla){
        ConexionSqliteHelper con = new ConexionSqliteHelper(context);
        SQLiteDatabase db = con.getWritableDatabase();
        switch (tabla){
            case ProductoTabla.TABLA:
                db.delete(ProductoTabla.TABLA, ProductoTabla.PROD_ID + " = ?", new String[]{String.valueOf(codigo)});
                break;
        }
    }
}

package steph.rs.controlesbasicos.esquemaSqlite.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import steph.rs.controlesbasicos.data.modelo.Producto;
import steph.rs.controlesbasicos.esquemaSqlite.ConexionSqliteHelper;
import steph.rs.controlesbasicos.esquemaSqlite.tablas.ProductoTabla;

public class Update {

    public static void actualizar(Context context, Object param, String tabla) {

    ConexionSqliteHelper con = new ConexionSqliteHelper(context);

    SQLiteDatabase db = con.getWritableDatabase();

    ContentValues values = new ContentValues();

    switch (tabla)
    {
        case ProductoTabla.TABLA:

            Producto prod = (Producto)param;

            values.put(ProductoTabla.PROD_NOMBRE,prod.getProd_nombre());
            values.put(ProductoTabla.PROD_COD,prod.getProd_codigo());
            values.put(ProductoTabla.PROD_MARCA,prod.getProd_marca());
            values.put(ProductoTabla.PROD_PRES,prod.getProd_presentacion());
            values.put(ProductoTabla.PROD_DESC,prod.getProd_descripcion());
            values.put(ProductoTabla.PROD_PRECIO,prod.getProd_precio());
            values.put(ProductoTabla.PROD_RUTA_FOTO,prod.getProd_ruta_foto());

            db.update(ProductoTabla.TABLA,values, ProductoTabla.PROD_ID + " = ?", new String[] {String.valueOf(prod.getProd_id())});

            break;
    }
}
}

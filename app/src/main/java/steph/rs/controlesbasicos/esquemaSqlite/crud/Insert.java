package steph.rs.controlesbasicos.esquemaSqlite.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import steph.rs.controlesbasicos.data.modelo.Producto;
import steph.rs.controlesbasicos.data.preferencia.SessionPreferences;
import steph.rs.controlesbasicos.esquemaSqlite.ConexionSqliteHelper;
import steph.rs.controlesbasicos.esquemaSqlite.tablas.ProductoTabla;

public class Insert {

    public static void registrar(Context context, Object param, String tabla) {
        ConexionSqliteHelper con = new ConexionSqliteHelper(context);
        SQLiteDatabase db = con.getWritableDatabase();
        ContentValues values = new ContentValues();

        switch (tabla)
        {
            case ProductoTabla.TABLA:
                Producto producto = (Producto)param;
                values.put(ProductoTabla.PROD_ID, producto.getProd_id());
                values.put(ProductoTabla.PROD_COD, producto.getProd_codigo());
                values.put(ProductoTabla.PROD_NOMBRE, producto.getProd_nombre());
                values.put(ProductoTabla.PROD_MARCA, producto.getProd_marca());
                values.put(ProductoTabla.PROD_PRES, producto.getProd_presentacion());
                values.put(ProductoTabla.PROD_DESC, producto.getProd_descripcion());
                values.put(ProductoTabla.PROD_PRECIO, producto.getProd_precio());
                values.put(ProductoTabla.PROD_RUTA_FOTO, producto.getProd_ruta_foto());
                db.insert(ProductoTabla.TABLA, ProductoTabla.PROD_ID,  values);
                SessionPreferences.get(context).setProducto(producto.getProd_id());
                break;
        }
    }
}

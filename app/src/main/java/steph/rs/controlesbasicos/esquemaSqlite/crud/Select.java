package steph.rs.controlesbasicos.esquemaSqlite.crud;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import steph.rs.controlesbasicos.data.modelo.Producto;
import steph.rs.controlesbasicos.esquemaSqlite.ConexionSqliteHelper;
import steph.rs.controlesbasicos.esquemaSqlite.tablas.ProductoTabla;

public class Select {

    private static ConexionSqliteHelper con = null;
    private static SQLiteDatabase db = null;

    private static List<Object> seleccionarRegistros(Context context, String tabla){
        List<Object> listaRetorno = new ArrayList<>();

        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        Cursor cLista = db.query(tabla,null, null,null,null,null,null);

        while (cLista.moveToNext()){
            switch (tabla){
                case ProductoTabla.TABLA:
                    listaRetorno.add(
                            new Producto(
                                    cLista.getInt(cLista.getColumnIndex(ProductoTabla.PROD_ID)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_COD)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_NOMBRE)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_MARCA)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_PRES)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_DESC)),
                                    cLista.getDouble(cLista.getColumnIndex(ProductoTabla.PROD_PRECIO)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_RUTA_FOTO)),
                                    false
                            )
                    );
                    break;
            }
        }
        return listaRetorno;
    }


    public static void seleccionarProductos(Context context, List<Producto> lista, String buscar){
        lista.clear();
        List<Object> tempLista = seleccionarRegistros(context, ProductoTabla.TABLA);
        for (Object item : tempLista){
            Producto _item = (Producto)item;
            if (buscar.length() > 0){
                if (_item.getProd_nombre().length() >= buscar.length()){
                    String cadenaRecortada = _item.getProd_nombre().substring(0, buscar.length());
                    if (buscar.equals(cadenaRecortada))lista.add(_item);
                }
            }else{
                lista.add(_item);
            }
        }
    }

}

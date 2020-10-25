package steph.rs.controlesbasicos.esquemaSqlite.tablas;

public class ProductoTabla {

    public static final String TABLA = "producto";

    public static final String PROD_ID = "prod_id";
    public static final String PROD_COD = "prod_codigo";
    public static final String PROD_NOMBRE = "prod_nombre";
    public static final String PROD_MARCA = "prod_marca";
    public static final String PROD_PRES = "prod_presentacion";
    public static final String PROD_DESC = "prod_descripcion";
    public static final String PROD_PRECIO = "prod_precio";
    public static final String PROD_RUTA_FOTO = "prod_ruta_foto";

    public static final String CREAR_TABLA_PRODUCTO
            = "CREATE TABLE " + TABLA + "("
            + PROD_ID + " INT PRIMARY KEY,"
            + PROD_COD + " INT PRIMARY KEY,"
            + PROD_NOMBRE + " TEXT,"
            + PROD_MARCA + " TEXT,"
            + PROD_PRES + " TEXT,"
            + PROD_DESC + " TEXT,"
            + PROD_PRECIO + " NUMERIC,"
            + PROD_RUTA_FOTO + " TEXT "
            + ") ;";

    public static final String ELIMINAR_TABLA_PRODUCTO = "DROP TABLE IF EXISTS " + TABLA +";";


}

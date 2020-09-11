package steph.rs.controlesbasicos.BaseDeDatos;

public class Productos_Table {
    public static final String TABLA = "PRODUCTO";
    public static final String Product_ID = "ProductoID";
    public static final String Product_Name = "ProductoNombre";
    public static final String Product_Price = "ProductoPrecio";
    public static final String Product_Photo_Path = "RutaPhoto";
    public static final String Product_Brand = "ProductoMarca";

    public static final String Create_Product_Table
            = "CREATE TABLE " + TABLA + "("
            + Product_ID + " INT PRIMARY KEY,"
            + Product_Name + " TEXT,"
            + Product_Price + " NUMERIC,"
            + Product_Photo_Path + " TEXT "
            + Product_Brand + " TEXT "
            + ") ;";
    public static final String Delete_Product_Table = "DROP TABLE IF EXISTS " + TABLA +";";
}

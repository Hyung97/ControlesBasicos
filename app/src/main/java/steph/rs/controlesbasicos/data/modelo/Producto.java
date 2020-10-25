package steph.rs.controlesbasicos.data.modelo;


import java.io.Serializable;

import steph.rs.controlesbasicos.data.util.Metodos;

public class Producto implements Serializable {
    private int prod_id;
    private String prod_nombre;
    private String prod_codigo;
    private String prod_marca;
    private String prod_presentacion;
    private String prod_descripcion;
    private Double prod_precio;
    private String prod_ruta_foto;
    private Boolean prod_seleccionado;

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_nombre() {
        return prod_nombre;
    }

    public void setProd_nombre(String prod_nombre) {
        this.prod_nombre = prod_nombre;
    }

    public String getProd_codigo() {
        return prod_codigo;
    }
    public void setProd_codigo(String prod_codigo) {
        this.prod_nombre = prod_codigo;
    }

    public String getProd_marca() {
        return prod_marca;
    }

    public void setProd_marca(String prod_marca) {
        this.prod_marca = prod_marca;
    }

    public String getProd_presentacion() {
        return prod_presentacion;
    }

    public void setProd_presentacion(String prod_presentacion) {
        this.prod_presentacion = prod_presentacion;
    }

    public String getProd_descripcion() {
        return prod_descripcion;
    }

    public void setProd_descripcion(String prod_descripcion) {
        this.prod_descripcion = prod_descripcion;
    }

    public Double getProd_precio() {
        return prod_precio;
    }

    public void setProd_precio(Double prod_precio) {
        this.prod_precio = prod_precio;
    }

    public String getProd_ruta_foto() {
        return prod_ruta_foto;
    }

    public void setProd_ruta_foto(String prod_ruta_foto) {
        this.prod_ruta_foto = prod_ruta_foto;
    }

    public Boolean getProd_seleccionado() {
        return prod_seleccionado;
    }

    public void setProd_seleccionado(Boolean prod_seleccionado) {
        this.prod_seleccionado = prod_seleccionado;
    }

    public Producto(int prod_id, String prod_nombre, String prod_codigo, String prod_marca, String prod_presentacion, String prod_descripcion, Double prod_precio, String prod_ruta_foto, Boolean prod_seleccionado) {
        this.prod_id = prod_id;
        this.prod_codigo = prod_codigo;
        this.prod_nombre = prod_nombre;
        this.prod_marca = prod_marca;
        this.prod_presentacion = prod_presentacion;
        this.prod_descripcion = prod_descripcion;
        this.prod_precio = prod_precio;
        this.prod_ruta_foto = prod_ruta_foto;
        this.prod_seleccionado = prod_seleccionado;
    }

    public Producto(int prod_id, String s, String toString, String string, String s1, double v, String pathUri, boolean b) {

    }

    public String componer(String caracter){
        return Metodos.cadenaComponer(caracter, new Object[]{
                prod_id,
                prod_codigo,
                prod_nombre,
                prod_marca,
                prod_presentacion,
                prod_descripcion,
                prod_precio,
                prod_ruta_foto
        });
    }

    public Producto(String cadenaLeida,String caracter){
        this.prod_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida, 1, caracter));
        this.prod_nombre = Metodos.cadenaDescomponer(cadenaLeida, 2, caracter);
        this.prod_codigo = Metodos.cadenaDescomponer(cadenaLeida, 3, caracter);
        this.prod_marca = Metodos.cadenaDescomponer(cadenaLeida, 4, caracter);
        this.prod_presentacion = Metodos.cadenaDescomponer(cadenaLeida, 5, caracter);
        this.prod_descripcion = Metodos.cadenaDescomponer(cadenaLeida, 6, caracter);
        this.prod_precio = Double.parseDouble(Metodos.cadenaDescomponer(cadenaLeida, 7, caracter));
        this.prod_ruta_foto = Metodos.cadenaDescomponer(cadenaLeida, 8, caracter);
    }
}

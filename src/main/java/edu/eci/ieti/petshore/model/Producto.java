package edu.eci.ieti.petshore.model;

import org.springframework.data.annotation.Id;

public class Producto {
    @Id
    public String id;
    public String nombre;
    public String descripcion;
    public String precio;
    public boolean disponible;
    public Categoria categoria;

    public Producto(){}

    public Producto(String nombre, String descripcion, String precio, boolean disponible, Categoria categoria){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = disponible;
        this.categoria = categoria;
    }
    public Producto(String id, String nombre, String descripcion, String precio, boolean disponible, Categoria categoria){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = disponible;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

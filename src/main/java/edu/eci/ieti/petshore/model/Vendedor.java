package edu.eci.ieti.petshore.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Vendedor {

    @Id
    public String id;
    public String nombre, nombreTienda, direccion, username;
    public ArrayList<Producto> productosDisponibles;
    public ArrayList<Producto> productosVendidos;
    public Calificacion calificacionPromedio;
    public Estado estado;
    public boolean estaAbierta;
    public ArrayList<Calificacion> calificacions;

    public Vendedor() {
        this.calificacionPromedio = null;
        this.calificacions = new ArrayList<Calificacion>();
        this.productosDisponibles = new ArrayList<Producto>();
        this.productosVendidos = new ArrayList<Producto>();
    }

    public Vendedor(String id, String nombre, ArrayList<Producto> productosDisponibles, ArrayList<Producto> productosVendidos,
                    String nombreTienda, String direccion, Estado estado, Calificacion calificacionPromedio, boolean estaAbierta, ArrayList<Calificacion> calificacions) {
        this.id = id;
        this.nombre = nombre;
        this.productosDisponibles = productosDisponibles;
        this.productosVendidos = productosVendidos;
        this.nombreTienda = nombreTienda;
        this.direccion = direccion;
        this.estado = estado;
        this.calificacionPromedio = calificacionPromedio;
        this.estaAbierta = estaAbierta;
        this.calificacions = calificacions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Producto> getProductosDisponibles() {
        return productosDisponibles;
    }

    public void setProductosDisponibles(ArrayList<Producto> productosDisponibles) {
        this.productosDisponibles = productosDisponibles;
    }

    public ArrayList<Producto> getProductosVendidos() {
        return productosVendidos;
    }

    public void setProductosVendidos(ArrayList<Producto> productosVendidos) {
        this.productosVendidos = productosVendidos;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Calificacion getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(Calificacion calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    public boolean isEstaAbierta() {
        return estaAbierta;
    }

    public void setEstaAbierta(boolean estaAbierta) {
        this.estaAbierta = estaAbierta;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Calificacion> getCalificacions() {
        return calificacions;
    }

    public void setCalificacions(ArrayList<Calificacion> calificacions) {
        this.calificacions = calificacions;
    }

    public void  addProductoVendido(Producto producto){
        productosVendidos.add(producto);
    }
}

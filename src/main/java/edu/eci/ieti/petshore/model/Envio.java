package edu.eci.ieti.petshore.model;

import edu.eci.ieti.petshore.model.Producto;

import java.util.ArrayList;

public class Envio {
    private String precio;
    private ArrayList<String> origen;
    private ArrayList<String> destino;
    private ArrayList<Producto> productos;

    public Envio(){
    }

    public Envio(String precio, ArrayList<String> origen, ArrayList<String> destino, ArrayList<Producto> productos) {
        this.precio = precio;
        this.origen = origen;
        this.destino = destino;
        this.productos = productos;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public ArrayList<String> getOrigen() {
        return origen;
    }

    public void setOrigen(ArrayList<String> origen) {
        this.origen = origen;
    }

    public ArrayList<String> getDestino() {
        return destino;
    }

    public void setDestino(ArrayList<String> destino) {
        this.destino = destino;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
}

package edu.eci.ieti.petshore.model;

public class NuevoEstado {
    public String vendedorUsername;
    public boolean estado;
    public NuevoEstado(){}

    public NuevoEstado(String vendedorUsername, boolean estado) {
        this.vendedorUsername = vendedorUsername;
        this.estado = estado;
    }

    public String getVendedorUsername() {
        return vendedorUsername;
    }

    public void setVendedorUsername(String vendedorUsername) {
        this.vendedorUsername = vendedorUsername;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}

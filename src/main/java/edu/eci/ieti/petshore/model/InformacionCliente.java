package edu.eci.ieti.petshore.model;

public class InformacionCliente {
    public String clienteUsername,direccion;
    public InformacionCliente(){}
    public InformacionCliente(String clienteUsername, String direccion) {
        this.clienteUsername = clienteUsername;
        this.direccion = direccion;
    }

    public String getClienteUsername() {
        return clienteUsername;
    }

    public void setClienteUsername(String clienteUsername) {
        this.clienteUsername = clienteUsername;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}

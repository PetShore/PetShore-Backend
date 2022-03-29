package edu.eci.ieti.petshore.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Cliente {
    @Id
    public String id;
    public String username;
    public String direccionEntrega;
    public Estado estado;
    public Calificacion calificacionGeneral;
    public ArrayList<Calificacion> calificacions;

    public Cliente(){
        this.calificacionGeneral = null;
        this.calificacions = new ArrayList<Calificacion>();
    }

    public Cliente(String direccionEntrega, Estado estado, Calificacion calificacionGeneral, ArrayList<Calificacion> calificacions){
        this.direccionEntrega = direccionEntrega;
        this.estado = estado;
        this.calificacionGeneral = calificacionGeneral;
        this.calificacions = calificacions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Calificacion getCalificacionGeneral() {
        return calificacionGeneral;
    }

    public void setCalificacionGeneral(Calificacion calificacionGeneral) {
        this.calificacionGeneral = calificacionGeneral;
    }

    public ArrayList<Calificacion> getCalificacions() {
        return calificacions;
    }

    public void setCalificacions(ArrayList<Calificacion> calificacions) {
        this.calificacions = calificacions;
    }
}

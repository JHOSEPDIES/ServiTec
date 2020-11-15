package com.example.servitec.clases.Modelos;

public class POJOServiciosGet {

    String nombre_comun,servicio,fecha_registro;
    private boolean expanded;
    public POJOServiciosGet() {
    }

    public POJOServiciosGet(String nombre,  String servicio, String fecha_registro) {
        this.nombre_comun = nombre;
        this.servicio = servicio;
        this.fecha_registro = fecha_registro;
        this.expanded = false;
    }

    public String getNombre() {
        return nombre_comun;
    }

    public void setNombre(String nombre) {
        this.nombre_comun = nombre;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }
}

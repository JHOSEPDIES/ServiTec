package com.example.servitec.clases.Modelos;

public class POJOServicios {

    String nombre_comun,dependencia,modelo,marca,ns,color;

    public POJOServicios() {
    }

    public POJOServicios(String nombre_comun, String dependencia, String modelo, String marca, String ns, String color) {
        this.nombre_comun = nombre_comun;
        this.dependencia = dependencia;
        this.modelo = modelo;
        this.marca = marca;
        this.ns = ns;
        this.color = color;
    }

    public String getNombre() {
        return nombre_comun;
    }

    public void setNombre(String nombre) {
        this.nombre_comun = nombre;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSn() {
        return ns;
    }

    public void setSn(String sn) {
        this.ns = sn;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}

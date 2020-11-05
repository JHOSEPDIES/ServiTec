package com.example.servitec.clases;

public class responseServicios {

    String nombre,dependencia,modelo,marca,sn,color;

    public responseServicios() {
    }

    public responseServicios(String nombre, String dependencia, String modelo, String marca, String sn, String color) {
        this.nombre = nombre;
        this.dependencia = dependencia;
        this.modelo = modelo;
        this.marca = marca;
        this.sn = sn;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}

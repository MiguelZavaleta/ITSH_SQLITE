package com.firebase.itsh_sqlite.Recursos;

import java.io.Serializable;

public class Alumno implements Serializable {
String NumeroControl,Nombre,Apoellidos,Carrera,Correo,Direccion,Genero,Telefono;

    public Alumno(String numeroControl, String nombre, String apellidos,String G,String carrera, String correo, String direccion, String telefono) {
        NumeroControl = numeroControl;
        Nombre = nombre;
        Apoellidos = apellidos;
        Carrera = carrera;
        Correo = correo;
        Direccion = direccion;
        Telefono = telefono;
        this.Genero=G;
    }
    public Alumno(){}
    public String Imprimir(){
        return "Numero Ctrl: "+getNumeroControl()+"\n";

    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getNumeroControl() {
        return NumeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        NumeroControl = numeroControl;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apoellidos;
    }

    public void setApellidos(String apoellidos) {
        Apoellidos = apoellidos;
    }

    public String getCarrera() {
        return Carrera;
    }

    public void setCarrera(String carrera) {
        Carrera = carrera;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
}

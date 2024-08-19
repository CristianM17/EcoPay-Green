package com.example.login.modelo;

public class User {
    int id;
    String nombre;
    String correo;
    String usuario;
    String clave;

    public User(int id, String nombre, String correo, String usuario, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.usuario = usuario;
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMombre() {
        return nombre;
    }

    public void setMombre(String mombre) {
        this.nombre = mombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}


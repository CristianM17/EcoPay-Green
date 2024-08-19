package com.example.login;

import android.content.Context;

import com.example.login.DB.DBconexion;

public class Sesion {
    protected int user_id;
    protected String usuario;

    public Sesion(int user_id, String usuario) {
        this.user_id = user_id;
        this.usuario = usuario;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}

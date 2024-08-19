package com.example.login;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {
    private static final String PREF_NAME = "UserPrefs";
    //Claves que se usan para recuperar datos
    private static final String KEY_EMAIL = "correo";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_USUARIO = "usuario";
    private static final String KEY_NOMBRE = "nombre";

    //variables a instanciar
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void RegisterUSer(String usuario, String password, String nombre, String correo) {
        editor.putString(KEY_EMAIL, correo);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_USUARIO, usuario);
        editor.putString(KEY_NOMBRE, nombre);

        editor.apply();
    }

    public boolean loginUser(String email, String password){
        String RegisteredUsuario = sharedPreferences.getString(KEY_USUARIO, null);
        String RegisteredPassword = sharedPreferences.getString(KEY_PASSWORD, null);
        return email.equals(RegisteredUsuario) && password.equals(RegisteredPassword);
    }


}

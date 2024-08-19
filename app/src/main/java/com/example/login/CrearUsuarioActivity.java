package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.DB.DBmanager;

public class CrearUsuarioActivity extends AppCompatActivity {
    private EditText usuario, password, editNombre, editCorreo;
    private CheckBox checkTratamiento,checkTerminos;
    private Button btncrear;
    private UserManager userManager;

    private DBmanager dBmanager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        dBmanager = new DBmanager(getApplicationContext());

        usuario = findViewById(R.id.user);
        password = findViewById(R.id.password);
        editNombre = findViewById(R.id.nombre);
        editCorreo = findViewById(R.id.correo);

        checkTratamiento = findViewById(R.id.tratamiento);
        checkTerminos = findViewById(R.id.terminos);

        userManager = new UserManager(this);

        //Intent para boton Iniciar Sesion
        btncrear = findViewById(R.id.crear);
        btncrear.setOnClickListener(view -> Registro());

    }
    private void Registro(){
        String nombre = editNombre.getText().toString();
        String correo = editCorreo.getText().toString();
        String EditUsuario = usuario.getText().toString();
        String EditTextPassword = password.getText().toString();

        if (TextUtils.isEmpty(EditUsuario)){
            Toast.makeText(CrearUsuarioActivity.this,"Ingrese un usuario",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(nombre)){
            Toast.makeText(CrearUsuarioActivity.this,"Ingrese un nombre",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(correo)){
            Toast.makeText(CrearUsuarioActivity.this,"Ingrese un correo",Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(correo)) {
            Toast.makeText(CrearUsuarioActivity.this,"Ingrese un Correo Electronico Valido",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(EditTextPassword)) {
            Toast.makeText(CrearUsuarioActivity.this,"Ingrese una contraseña",Toast.LENGTH_SHORT).show();
        } else if (EditTextPassword.length() < 3) {
            Toast.makeText(CrearUsuarioActivity.this,"La contraseña debe ser de mas de 3 caracteres",Toast.LENGTH_SHORT).show();
        } else if (!checkTratamiento.isChecked()) {
            Toast.makeText(CrearUsuarioActivity.this,"Debe aceptar el tratamiento de datos",Toast.LENGTH_SHORT).show();
        } else if (!checkTerminos.isChecked()) {
            Toast.makeText(CrearUsuarioActivity.this,"Debe aceptar los terminos y condiciones",Toast.LENGTH_SHORT).show();
        } else {
            registrarUsuario(EditUsuario,EditTextPassword,nombre,correo);
        }

    }
    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private void registrarUsuario(String usuario, String password, String nombre, String correo) {

        dBmanager.open();

        //userManager.RegisterUSer(usuario, password, nombre, correo);
        try {
            dBmanager.insertarUser(nombre, correo, usuario, password);
            Toast.makeText(CrearUsuarioActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            finish();
            //crea el objeto intent para hacer el cambio atraves de las pantallas
            Intent intent = new Intent(CrearUsuarioActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(CrearUsuarioActivity.this,"Datos guardados",Toast.LENGTH_SHORT).show();
            finish();
        } catch (SQLException e) {
            Log.e("insercion", "Error de validacion: " + e.getMessage());
        }

        dBmanager.close();

    }
}
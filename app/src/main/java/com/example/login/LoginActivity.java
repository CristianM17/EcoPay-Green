package com.example.login;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.DB.DBmanager;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsuario, editTextPassword;
    private Button btnIngresar, btncrearCuenta;
    private DBmanager dBmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicialización de variables
        editTextUsuario = findViewById(R.id.user);
        editTextPassword = findViewById(R.id.txtPassword);
        btnIngresar = findViewById(R.id.btningresar);
        btncrearCuenta = findViewById(R.id.btncrearCuenta);

        dBmanager = new DBmanager(getApplicationContext());

        // Evento del botón de iniciar sesión
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = editTextUsuario.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (usuario.isEmpty() || password.isEmpty()) {
                    if (usuario.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "El usuario no ha sido diligenciado", Toast.LENGTH_SHORT).show();
                    }
                    if (password.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "La contraseña no ha sido diligenciada", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

                dBmanager.open();

                Cursor cursor = dBmanager.validarCredencialesUser(usuario, password);

                if (cursor != null && cursor.moveToFirst()) {
                    // Guarda los datos de la sesión
                    int nombreIndex = cursor.getColumnIndex("nombre");
                    int idIndex = cursor.getColumnIndex("id");

                    if (nombreIndex != -1 && idIndex != -1) {
                        String nombre = cursor.getString(nombreIndex);
                        int idUser = cursor.getInt(idIndex);

                        Log.d("LoginActivity", "ID: " + idUser + ", Nombre: " + nombre);
                        Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                        intent.putExtra("EXTRA_USER_ID", idUser);
                        intent.putExtra("EXTRA_USER_NAME", nombre);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e("LoginActivity", "Error: Columnas 'id' o 'nombre' no encontradas.");
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario o password incorrectos", Toast.LENGTH_SHORT).show();
                }

                dBmanager.close();
            }
        });

        btncrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CrearUsuarioActivity.class);
                startActivity(intent);
            }
        });
    }
}


package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class PrincipalActivity extends AppCompatActivity {
    ImageButton btncategoria, btnestadistica, btnconsejos, btnregistro, btnsalir;
    private int userId;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Capturar el Intent y los datos extras
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            userId = intent.getIntExtra("EXTRA_USER_ID", -1);
            userName = intent.getStringExtra("EXTRA_USER_NAME");
        }

        // Log para verificar
        Log.d("insercion","userId: " + userId + ", userName: " + userName);

        // Intent para botón categoría
        btncategoria = findViewById(R.id.categoria);
        btncategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(PrincipalActivity.this, CategoriaActivity.class);
                intent.putExtra("EXTRA_USER_ID", userId);
                intent.putExtra("EXTRA_USER_NAME", userName);
                startActivity(intent);
            }
        });

        // Intent para botón estadística
        btnestadistica = findViewById(R.id.estadistica);
        btnestadistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(PrincipalActivity.this, EstadisticasActivity.class);
                intent.putExtra("EXTRA_USER_ID", userId);
                intent.putExtra("EXTRA_USER_NAME", userName);
                startActivity(intent);
            }
        });

        // Intent para botón consejos
        btnconsejos = findViewById(R.id.consejos);
        btnconsejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(PrincipalActivity.this, ConsejosActivity.class);
                intent.putExtra("EXTRA_USER_ID", userId);
                intent.putExtra("EXTRA_USER_NAME", userName);
                startActivity(intent);
            }
        });

        // Intent para botón registro
        btnregistro = findViewById(R.id.registro);
        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(PrincipalActivity.this, RegistroActivity.class);
                intent.putExtra("EXTRA_USER_ID", userId);
                intent.putExtra("EXTRA_USER_NAME", userName);
                startActivity(intent);
            }
        });

        // Intent para salir
        btnsalir = findViewById(R.id.cerrarSesion);
        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(PrincipalActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Cierra la actividad actual
            }
        });
    }
}

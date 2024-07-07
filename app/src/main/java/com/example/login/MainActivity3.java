package com.example.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {
    ImageButton btncategoria, btnestadistica, btnconsejos, btnregistro, btnsalir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //Intent para boton categoria
        btncategoria = findViewById(R.id.categoria);
        btncategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //crea el objeto intent para hacer el cambio atraves de las pantallas
                Intent intent = new Intent(MainActivity3.this,MainActivity4.class);
                startActivity(intent);
            }
        });

        //Intent para boton estadisticas
        btnestadistica = findViewById(R.id.estadistica);
        btnestadistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //crea el objeto intent para hacer el cambio atraves de las pantallas
                Intent intent = new Intent(MainActivity3.this,MainActivity7.class);
                startActivity(intent);
            }
        });

        //Intent para boton consejos
        btnconsejos = findViewById(R.id.consejos);
        btnconsejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //crea el objeto intent para hacer el cambio atraves de las pantallas
                Intent intent = new Intent(MainActivity3.this,MainActivity5.class);
                startActivity(intent);
            }
        });

        //Intent para boton registro
        btnregistro = findViewById(R.id.registro);
        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //crea el objeto intent para hacer el cambio atraves de las pantallas
                Intent intent = new Intent(MainActivity3.this,MainActivity6.class);
                startActivity(intent);
            }
        });

        //Intent para salir
        btnsalir = findViewById(R.id.cerrarSesion);
        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //crea el objeto intent para hacer el cambio atraves de las pantallas
                Intent intent = new Intent(MainActivity3.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
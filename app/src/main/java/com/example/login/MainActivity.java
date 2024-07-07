package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button btnIngresar, btncrearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent para boton Iniciar Sesion
        btnIngresar = findViewById(R.id.btningresar);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //crea el objeto intent para hacer el cambio atraves de las pantallas
                Intent intent = new Intent(MainActivity.this,MainActivity3.class);
                startActivity(intent);
            }
        });
        //Intent para boton Crear Cuenta
        btncrearCuenta = findViewById(R.id.btncrearCuenta);
        btncrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //crea el objeto intent para hacer el cambio atraves de las pantallas
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });
    }
}
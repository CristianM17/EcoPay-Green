package com.example.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity4 extends AppCompatActivity {

    ImageButton btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        //Intent para boton volver al principal
        btnHome = findViewById(R.id.casa);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //crea el objeto intent para hacer el cambio atraves de las pantallas
                Intent intent = new Intent(MainActivity4.this,MainActivity3.class);
                startActivity(intent);
            }
        });
    }
}
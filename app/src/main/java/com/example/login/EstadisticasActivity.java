package com.example.login;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.DB.DBmanager;

public class EstadisticasActivity extends AppCompatActivity {

    private TableLayout myTableLayout;
    private Button buttonClear;
    private ImageButton btnHome;
    private Spinner spinnerMaterial;
    private DBmanager dbManager;
    private int userId;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        btnHome = findViewById(R.id.casa);
        buttonClear = findViewById(R.id.buttonClear);
        spinnerMaterial = findViewById(R.id.listaCategorias);

        // Inicializa el TableLayout
        myTableLayout = findViewById(R.id.tableLayout);

        // Obtener el Intent y los datos extras
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            userId = intent.getIntExtra("EXTRA_USER_ID", -1);
            userName = intent.getStringExtra("EXTRA_USER_NAME");
        }

        Log.d("insercion", "user" + userId + ", " + userName);

        // Inicializa el DBmanager y abre la base de datos
        dbManager = new DBmanager(this);
        dbManager.open();

        // Configurar el Spinner
        cargarMaterialesSpinner();

        // Configurar el listener para el Spinner
        spinnerMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMaterial = (String) parent.getItemAtPosition(position);
                if (selectedMaterial.equals(getString(R.string.material_mejor_pagado))) {
                    mostrarDatosMaterialMejorPagado();
                } else {
                    // Manejo para otras opciones, si es necesario
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        // Configurar el botón de regreso
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EstadisticasActivity.this, PrincipalActivity.class);
                // Obtener datos del Intent
                intent.putExtra("EXTRA_USER_ID", userId);
                intent.putExtra("EXTRA_USER_NAME", userName);
                Log.d("insercion", "user" + userId + ", " + userName);
                startActivity(intent);
                finish(); // Opcional, para cerrar la actividad actual
            }
        });

        // Configurar el botón para limpiar la tabla
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarTabla();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cierra la base de datos cuando la actividad se destruye
        dbManager.close();
    }

    private void cargarMaterialesSpinner() {
        // Cargar los materiales desde el array definido en strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.materiales_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaterial.setAdapter(adapter);
    }

    private void mostrarDatosMaterialMejorPagado() {
        // Limpiar la tabla antes de mostrar nuevos datos
        limpiarTabla();

        // Obtener el registro con el mayor valor_ganado
        Cursor cursor = obtenerRegistroConMayorValorGanado();
        if (cursor != null && cursor.moveToFirst()) {
            Log.d("EstadisticasActivity", "Datos obtenidos del cursor");

            String categoria = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
            double valorGanado = cursor.getDouble(cursor.getColumnIndexOrThrow("valor_ganado"));

            // Crear una nueva fila en la tabla
            TableRow tableRow = new TableRow(this);

            // Crear TextViews para mostrar cada dato
            TextView textViewUserId = new TextView(this);
            textViewUserId.setText(String.valueOf(userId));
            textViewUserId.setPadding(10, 10, 10, 10);
            textViewUserId.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

            TextView textViewCategoria = new TextView(this);
            textViewCategoria.setText(categoria);
            textViewCategoria.setPadding(10, 10, 10, 10);
            textViewCategoria.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

            TextView textViewValorGanado = new TextView(this);
            textViewValorGanado.setText(String.valueOf(valorGanado));
            textViewValorGanado.setPadding(10, 10, 10, 10);
            textViewValorGanado.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

            // Agregar los TextViews a la fila
            tableRow.addView(textViewUserId);  // Agregar el userId a la fila
            tableRow.addView(textViewCategoria);
            tableRow.addView(textViewValorGanado);

            // Agregar la fila al TableLayout
            myTableLayout.addView(tableRow);

            cursor.close(); // No olvides cerrar el cursor
        } else {
            Log.d("EstadisticasActivity", "No se encontraron registros");
        }
    }

    private void limpiarTabla() {
        // Mantener la primera fila (cabecera) y eliminar las demás
        int childCount = myTableLayout.getChildCount();
        if (childCount > 1) {
            myTableLayout.removeViews(1, childCount - 1);
        }
    }

    private Cursor obtenerRegistroConMayorValorGanado() {
        Cursor cursor = dbManager.obtenerRegistroConMayorValorGanado();
        if (cursor != null && cursor.getCount() > 0) {
            Log.d("EstadisticasActivity", "Cursor contiene datos");
        } else {
            Log.d("EstadisticasActivity", "Cursor vacío o null");
        }
        return cursor;
    }
}

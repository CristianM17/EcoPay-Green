package com.example.login;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.DB.DBmanager;

import java.util.ArrayList;
import java.util.List;

public class CategoriaActivity extends AppCompatActivity {

    private ImageButton btnHome;
    private TextView textViewCategoria, textViewDescripcion;
    private Button btnCrear;
    private DBmanager dBmanager;
    private int userId;
    private String userName;
    private Spinner spinner;
    private List<String> categorias = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        // Inicialización de vistas
        btnHome = findViewById(R.id.casa);
        btnCrear = findViewById(R.id.crearCat);
        textViewCategoria = findViewById(R.id.material);
        textViewDescripcion = findViewById(R.id.descripcion);
        spinner = findViewById(R.id.listaCategorias);

        // Configurar el adaptador del Spinner
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Inicializar DBmanager
        dBmanager = new DBmanager(this);
        dBmanager.open();

        // Obtener el Intent y los datos extras
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            userId = intent.getIntExtra("EXTRA_USER_ID", -1);
            userName = intent.getStringExtra("EXTRA_USER_NAME");
        }

        Log.d("insercion", "user" + userId + ", " + userName);

        // Evento del botón Crear
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoria = textViewCategoria.getText().toString();
                String descripcion = textViewDescripcion.getText().toString();

                if (TextUtils.isEmpty(categoria)) {
                    Toast.makeText(CategoriaActivity.this, "Ingrese una categoría", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(descripcion)) {
                    Toast.makeText(CategoriaActivity.this, "Ingrese una descripción", Toast.LENGTH_SHORT).show();
                } else {
                    dBmanager.insertarCategoria(categoria, userId, descripcion);
                    Toast.makeText(CategoriaActivity.this, "Registro de la categoría exitoso", Toast.LENGTH_SHORT).show();
                    actualizarSpinner(); // Llama a la función para actualizar el Spinner
                    textViewDescripcion.setText("");
                    textViewCategoria.setText("");
                }
            }
        });

        // Configurar el listener para el Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String categoriaSeleccionada = parentView.getItemAtPosition(position).toString();
                Toast.makeText(CategoriaActivity.this, "Seleccionaste: " + categoriaSeleccionada, Toast.LENGTH_SHORT).show();

                // Lógica adicional en función de la categoría seleccionada
                if (categoriaSeleccionada.equals("Elige una categoría")) {
                    Toast.makeText(CategoriaActivity.this, "Por favor, selecciona una categoría válida", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Spinner", "Categoría seleccionada: " + categoriaSeleccionada);
                    // Realiza alguna acción en función de la categoría seleccionada
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Aquí puedes manejar el caso en que no se selecciona ningún ítem
                Log.d("Spinner", "Ninguna categoría seleccionada");
            }
        });

        // Configurar el botón para volver a PrincipalActivity
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriaActivity.this, PrincipalActivity.class);
                intent.putExtra("EXTRA_USER_ID", userId);
                intent.putExtra("EXTRA_USER_NAME", userName);
                startActivity(intent);
                finish(); // Opcional, para cerrar la actividad actual
            }
        });
    }

    public void actualizarSpinner() {
        Cursor cursor = dBmanager.obtenerCategorias();
        if (cursor.moveToFirst()) {
            categorias.clear(); // Limpiar la lista antes de actualizarla
            do {
                categorias.add(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
        Log.d("insercion", "Spinner actualizado correctamente");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dBmanager.close(); // Cerrar la base de datos cuando la actividad se destruya
    }
}

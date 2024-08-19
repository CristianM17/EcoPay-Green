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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.DB.DBmanager;
import com.example.login.modelo.Categoria;

import java.util.ArrayList;
import java.util.List;

public class RegistroActivity extends AppCompatActivity {

    ImageButton btnHome;
    Button btnCrear;
    private DBmanager dBmanager;
    private int userId;
    private String userName;
    EditText editTextCantMaterial, editTextValorGanado, editTextFecha;
    private Spinner spinnerCategorias;
    private String categoriaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar vistas
        btnHome = findViewById(R.id.casa);
        editTextCantMaterial = findViewById(R.id.promedioReciclado);
        editTextValorGanado = findViewById(R.id.ganado);
        editTextFecha = findViewById(R.id.fecha);
        btnCrear = findViewById(R.id.crearCat);
        spinnerCategorias = findViewById(R.id.listaCategoriasId);

        // Inicializar DBManager
        dBmanager = new DBmanager(this);
        dBmanager.open();

        // Obtener datos del Intent
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            userId = intent.getIntExtra("EXTRA_USER_ID", -1);
            userName = intent.getStringExtra("EXTRA_USER_NAME");
        }

        Log.d("insercion", "user" + userId + ", " + userName);

        // Obtener el cursor con las categorías usando el método query
        Cursor cursor = dBmanager.obtenerCategorias();

        List<Categoria> listaCategorias = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Obtener el id y el nombre de la categoría desde el cursor
                int idIndex = cursor.getColumnIndex("id");
                int nombreIndex = cursor.getColumnIndex("nombre");

                if (idIndex != -1 && nombreIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String nombreCat = cursor.getString(nombreIndex);
                    listaCategorias.add(new Categoria(id, nombreCat));
                }
            } while (cursor.moveToNext());

            // Cerrar el cursor después de su uso
            cursor.close();

            // Crear un ArrayAdapter usando la lista de categorías
            ArrayAdapter<Categoria> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    listaCategorias
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Establecer el adaptador en el Spinner
            spinnerCategorias.setAdapter(adapter);

        } else {
            Log.e("insercion", "El cursor obtenido es nulo o está vacío.");
        }

// Listener para el botón Crear
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cantidad = 0;
                int valorGanado = 0;

                try {
                    cantidad = Integer.parseInt(editTextCantMaterial.getText().toString());
                    valorGanado = Integer.parseInt(editTextValorGanado.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(RegistroActivity.this, "Ingrese valores numéricos válidos", Toast.LENGTH_SHORT).show();
                    return;
                }

                String fecha = editTextFecha.getText().toString();

                if (spinnerCategorias.getSelectedItem() == null) {
                    Toast.makeText(RegistroActivity.this, "Ingrese una categoría", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(fecha)) {
                    Toast.makeText(RegistroActivity.this, "Ingrese una fecha", Toast.LENGTH_SHORT).show();
                } else {
                    // Obtener la categoría seleccionada
                    Categoria categoriaSeleccionada = (Categoria) spinnerCategorias.getSelectedItem();
                    int categoriaId = categoriaSeleccionada.getId();

                    // Insertar el registro con el ID de la categoría seleccionada
                    dBmanager.insertarRegistros(categoriaId, cantidad, valorGanado, fecha, userId);
                    Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Log.d("insercion", "registro exitoso");
                    editTextCantMaterial.setText("");
                    editTextValorGanado.setText("");
                    editTextFecha.setText("");
                }
            }
        });



        // Listener para el botón Home
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                // Crear el Intent para regresar a la actividad principal
                Intent intent = new Intent(RegistroActivity.this, PrincipalActivity.class);
                intent.putExtra("EXTRA_USER_ID", userId);
                intent.putExtra("EXTRA_USER_NAME", userName);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dBmanager.close(); // Cerrar la base de datos cuando la actividad se destruya
    }
}

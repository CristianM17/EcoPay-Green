package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConsejosActivity extends AppCompatActivity {

    ImageButton btnHome;
    private int userId;
    private String userName;
    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<String>> listItem;
    CustomExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejos);

        // Intent para botón volver al principal
        btnHome = findViewById(R.id.casa);

        // Obtener el Intent y los datos extras
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            userId = intent.getIntExtra("EXTRA_USER_ID", -1);
            userName = intent.getStringExtra("EXTRA_USER_NAME");
        }

        Log.d("insercion", "user" + userId + ", " + userName);

        expandableListView = findViewById(R.id.listaConsejos);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new CustomExpandableListAdapter(this, listGroup, listItem);
        expandableListView.setAdapter(adapter);
        initData();

        // Accion del boton de regreso a la pantalla principal
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConsejosActivity.this, PrincipalActivity.class);
                intent.putExtra("EXTRA_USER_ID", userId);
                intent.putExtra("EXTRA_USER_NAME", userName);
                startActivity(intent);
            }
        });
    }

    // Método para inicializar los datos del acordeón
    private void initData() {
        listGroup.add("Consejo dia 1");
        listGroup.add("Consejo dia 2");
        listGroup.add("Consejo dia 3");

        List<String> list1 = new ArrayList<>();
        list1.add("Clasifica los residuos: Separa los residuos en diferentes categorías: " +
                "plásticos, vidrio, papel/cartón, metal, y orgánicos. Usa contenedores " +
                "específicos para cada tipo.");

        List<String> list2 = new ArrayList<>();
        list2.add("Limpia los envases: Antes de reciclar envases de plástico, vidrio o metal," +
                " enjuágalos para eliminar restos de comida o líquidos. Esto evita malos olores" +
                " y facilita el proceso de reciclaje.");

        List<String> list3 = new ArrayList<>();
        list3.add("Aplasta los envases: Aplasta botellas de plástico, latas y cajas de cartón antes" +
                " de reciclarlas para ahorrar espacio en los contenedores.");

        listItem.put(listGroup.get(0), list1);
        listItem.put(listGroup.get(1), list2);
        listItem.put(listGroup.get(2), list3);

        adapter.notifyDataSetChanged();
    }
}

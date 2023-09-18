package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;
import com.dagnerchuman.miaplicativonegociomicroservice.adapter.ProductoAdapter;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceProductos;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ConfigApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntradaActivity extends AppCompatActivity {

    private ImageButton btnBackToLogin;
    private ImageButton btnNavigation;

    private RecyclerView recyclerViewProductos;

    private ProductoAdapter adapter;
    private List<Producto> productosList;

    private AlertDialog popupDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);

        btnBackToLogin = findViewById(R.id.btnBackToLogin);
        recyclerViewProductos = findViewById(R.id.recyclerViewProductos);
        btnNavigation = findViewById(R.id.btnNavigation);

        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));
        productosList = new ArrayList<>();
        adapter = new ProductoAdapter(this, productosList);
        recyclerViewProductos.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", "");
        String userName = sharedPreferences.getString("userName", "");
        String userApellido = sharedPreferences.getString("userApellido", "");
        String userTelefono = sharedPreferences.getString("userTelefono", "");
        Long userId = sharedPreferences.getLong("userId", -1);
        Long userNegocioId = sharedPreferences.getLong("userNegocioId", -1);

        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarPopupMisDatos();
            }
        });

        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(EntradaActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        ApiServiceProductos apiService = ConfigApi.getInstanceProducto();
        Call<List<Producto>> call = apiService.getAllProductos();
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()) {
                    List<Producto> primerosProductos = response.body().subList(0, Math.min(10, response.body().size()));
                    productosList.addAll(primerosProductos);
                    adapter.notifyDataSetChanged();
                    Log.d("API Response", "Response successful");
                } else {
                    Log.e("API Response", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("API Failure", "API request failed", t);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                String userEmail = data.getStringExtra("userEmail");
                String userName = data.getStringExtra("userName");
                String userApellido = data.getStringExtra("userApellido");
                String userTelefono = data.getStringExtra("userTelefono");
                Long userId = data.getLongExtra("userId", -1);
                Long userNegocioId = data.getLongExtra("userNegocioId", -1);
            }
        }
    }

    private void mostrarPopupMisDatos() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (popupDialog != null && popupDialog.isShowing()) {
                    popupDialog.dismiss();
                }
            }
        });

        // Agregar el botón "Ver Mis Datos" al diálogo emergente
        alertDialogBuilder.setNeutralButton("Ver Mis Datos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                verMisDatos(null); // Llamar al método verMisDatos
            }
        });

        // Agregar el botón "Ver Negocios" al diálogo emergente
        alertDialogBuilder.setNegativeButton("Ver Negocios", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                verNegocios(null); // Llamar al método verNegocios
            }
        });

        popupDialog = alertDialogBuilder.create();
        popupDialog.show();
    }


    // Método para manejar el clic del botón "Ver Mis Datos"
    public void verMisDatos(View view) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    // Método para manejar el clic del botón "Ver Negocios"
    public void verNegocios(View view) {
        Intent mainIntentN = new Intent(this, NegociosActivity.class);
        startActivity(mainIntentN);
    }

}

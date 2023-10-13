package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.adapter.CompraAdapter;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceCompras;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ConfigApi;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Compra;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class ListadoDeComprasActivity extends AppCompatActivity {

    private ImageButton btnBackToEntrada;

    private ApiServiceCompras apiServiceCompras;
    private RecyclerView recyclerView;
    private CompraAdapter comprasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_de_compras);

        // Inicializa las vistas
        btnBackToEntrada = findViewById(R.id.btnBackToEntrada);

        recyclerView = findViewById(R.id.recyclerViewCompras);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        comprasAdapter = new CompraAdapter();
        recyclerView.setAdapter(comprasAdapter);

        // Inicializa apiServiceCompras aquí
        apiServiceCompras = ConfigApi.getInstanceCompra(this);

        // Obtén el userId del Intent
        Intent intent = getIntent();
        Long userId = intent.getLongExtra("userId", 0);

        // Realiza la solicitud para obtener la lista de compras
        obtenerListaDeCompras(userId);

        btnBackToEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(ListadoDeComprasActivity.this, EntradaActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }

    private void obtenerListaDeCompras(Long userId) {
        Call<List<Compra>> call = apiServiceCompras.getAllCompras();

        call.enqueue(new Callback<List<Compra>>() {
            @Override
            public void onResponse(@NonNull Call<List<Compra>> call, @NonNull Response<List<Compra>> response) {
                if (response.isSuccessful()) {
                    List<Compra> todasLasCompras = response.body();

                    if (todasLasCompras != null && !todasLasCompras.isEmpty()) {
                        // Filtra las compras por userId
                        List<Compra> comprasDelUsuario = new ArrayList<>();
                        for (Compra compra : todasLasCompras) {
                            if (compra.getUserId().equals(userId)) {
                                comprasDelUsuario.add(compra);
                            }
                        }

                        if (!comprasDelUsuario.isEmpty()) {
                            // Actualiza el adaptador con la lista de compras del usuario
                            comprasAdapter.setCompras(comprasDelUsuario);
                        } else {
                            // Maneja el caso en que el usuario no tenga compras
                            Toast.makeText(ListadoDeComprasActivity.this, "No se encontraron compras para este usuario", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Maneja el caso en que no haya compras
                        Toast.makeText(ListadoDeComprasActivity.this, "No se encontraron compras", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Maneja el caso en que la solicitud no sea exitosa
                    Toast.makeText(ListadoDeComprasActivity.this, "Error al obtener las compras", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Compra>> call, @NonNull Throwable t) {
                // Maneja el error de la solicitud de red aquí
                Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
                Toast.makeText(ListadoDeComprasActivity.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

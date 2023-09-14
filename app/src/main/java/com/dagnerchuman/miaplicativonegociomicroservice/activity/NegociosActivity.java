package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceNegocio;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ConfigApi;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Negocio;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class NegociosActivity extends AppCompatActivity {

    private Button btnVerUsuario;
    private Button btnVerNegocios;
    private ApiServiceNegocio apiServiceNegocio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocio);

        // Inicializa las vistas
        btnVerUsuario = findViewById(R.id.btnVerUsuario);
        btnVerNegocios = findViewById(R.id.btnVerNegocios);

        // Inicializa ApiServiceNegocio utilizando Retrofit

        // Configura los eventos click para los botones
        btnVerUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí debes implementar la lógica para ver la información del usuario
                // Puedes redirigir a MainActivity o cualquier otra actividad que desees
                // Por ejemplo:
                startActivity(new Intent(NegociosActivity.this, MainActivity.class));
            }
        });

        btnVerNegocios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener y mostrar el listado de negocios
                obtenerYMostrarNegocios();
            }
        });
    }

    // Método para obtener y mostrar el listado de negocios
    private void obtenerYMostrarNegocios() {
        Call<List<Negocio>> call = apiServiceNegocio.getAllNegocios();

        call.enqueue(new Callback<List<Negocio>>() {
            @Override
            public void onResponse(Call<List<Negocio>> call, Response<List<Negocio>> response) {
                if (response.isSuccessful()) {
                    List<Negocio> negocios = response.body();
                    // Puedes mostrar la lista de negocios en una nueva actividad o en un RecyclerView, según tus necesidades.
                } else {
                    Toast.makeText(NegociosActivity.this, "Error al obtener los negocios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Negocio>> call, Throwable t) {
                Toast.makeText(NegociosActivity.this, "Error de red al obtener los negocios", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
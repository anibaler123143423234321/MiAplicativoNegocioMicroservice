// NegociosActivity
package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.adapter.NegocioAdapter;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceNegocio;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Negocio;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ConfigApi;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NegociosActivity extends AppCompatActivity {

    private ApiServiceNegocio apiServiceNegocio;
    private RecyclerView recyclerViewNegocios;
    private NegocioAdapter negocioAdapter;
    private ImageView btnRegresar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocio);

        // Inicializa las vistas
        recyclerViewNegocios = findViewById(R.id.recyclerViewNegocios);
        ImageView btnRegresar = findViewById(R.id.btnregresar);

        // Configura el ApiServiceNegocio usando ConfigApi
        apiServiceNegocio = ConfigApi.getInstanceNegocio(this);

        // Configura el RecyclerView y su adaptador
        recyclerViewNegocios.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa el adaptador con una lista vacía
        negocioAdapter = new NegocioAdapter(new ArrayList<>());
        recyclerViewNegocios.setAdapter(negocioAdapter);

        // Obtén y muestra la lista de negocios
        obtenerYMostrarNegocios();

        // Agrega un OnClickListener al botón de retroceso
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Acción de retroceso aquí (por ejemplo, finish() para cerrar la actividad actual)
                finish();
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
                    // Actualiza el adaptador con la lista de negocios
                    negocioAdapter.updateData(negocios);
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

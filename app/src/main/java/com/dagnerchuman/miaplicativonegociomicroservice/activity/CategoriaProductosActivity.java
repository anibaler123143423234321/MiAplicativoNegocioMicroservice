package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.adapter.CategoriaAdapter;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceProductos;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ConfigApi;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriaProductosActivity extends AppCompatActivity {
    private List<Producto> productList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CategoriaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_productos);

        recyclerView = findViewById(R.id.recyclerViewProductos);

        adapter = new CategoriaAdapter(this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Obtén el ID de la categoría seleccionada de la actividad anterior
        long categoriaId = getIntent().getLongExtra("categoriaSeleccionada", -1);

        // Registra el ID de la categoría seleccionada en el log
        Log.d("Categoría recibida", "ID: " + categoriaId);

        // Obtén todos los productos y filtra los que pertenecen a la categoría seleccionada
        obtenerProductosYFiltrarPorCategoria(categoriaId);
    }

    private void obtenerProductosYFiltrarPorCategoria(final long categoriaId) {
        ApiServiceProductos apiServiceProductos = ConfigApi.getInstanceProducto(this);

        // Realiza una llamada a la API para obtener todos los productos
        Call<List<Producto>> call = apiServiceProductos.getAllProductos();

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()) {
                    List<Producto> productos = response.body();
                    Log.d("Productos cargados", "Cantidad: " + productos.size());

                    // Filtra los productos por categoría
                    List<Producto> productosFiltrados = new ArrayList<>();
                    for (Producto producto : productos) {
                        if (producto.getCategoriaId() == categoriaId) {
                            productosFiltrados.add(producto);
                        }
                    }

                    // Registra los productos filtrados en el log
                    for (Producto producto : productosFiltrados) {
                        Log.d("Producto cargado", "Nombre: " + producto.getNombre());
                        Log.d("Producto cargado", "Categoría ID: " + producto.getCategoriaId());
                        Log.d("Producto cargado", "Imagen: " + producto.getPicture());
                        Log.d("Producto cargado", "Precio: " + producto.getPrecio());
                        Log.d("Producto cargado", "Negocio ID: " + producto.getNegocioId());
                        Log.d("Producto cargado", "Stock: " + producto.getStock());
                    }

                    productList.addAll(productosFiltrados);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("API Response", "Respuesta no exitosa: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("API Failure", "Fallo en la solicitud a la API", t);
            }
        });
    }
}

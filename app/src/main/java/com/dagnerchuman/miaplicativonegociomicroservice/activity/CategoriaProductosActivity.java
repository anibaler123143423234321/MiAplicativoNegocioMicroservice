package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.adapter.CategoriaAdapter;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceProductos;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ConfigApi;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.CarritoItem;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class CategoriaProductosActivity extends AppCompatActivity {
    private List<Producto> productList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CategoriaAdapter adapter;
    private ImageButton btnBackToLogin, btnCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_productos);

        recyclerView = findViewById(R.id.recyclerViewProductos);
        btnCarrito = findViewById(R.id.btnCarrito);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);

        adapter = new CategoriaAdapter(this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Configuración del botón de retroceso
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(CategoriaProductosActivity.this, EntradaActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        // Configuración del botón del carrito
        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre la actividad del carrito cuando se hace clic en el ícono del carrito.
                Intent carritoIntent = new Intent(CategoriaProductosActivity.this, CarritoActivity.class);
                carritoIntent.putExtra("productosEnCarrito", new ArrayList<>(productList)); // Envia la lista de productos al carrito
                startActivity(carritoIntent);
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_categoria, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {
            // Abre la actividad del carrito cuando se hace clic en el ícono del carrito.
            Intent intent = new Intent(this, CarritoActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void abrirCarrito() {
        // Crea una lista de elementos de prueba para el carrito (sustituye por tus propios elementos).
        List<CarritoItem> carrito = new ArrayList<>();

        // Crea una Intent y agrega los datos del carrito como extras.
        Intent carritoIntent = new Intent(CategoriaProductosActivity.this, CarritoActivity.class);
        carritoIntent.putExtra("carritoItems", (Serializable) carrito);

        // Inicia la actividad CarritoActivity.
        startActivity(carritoIntent);
    }

}

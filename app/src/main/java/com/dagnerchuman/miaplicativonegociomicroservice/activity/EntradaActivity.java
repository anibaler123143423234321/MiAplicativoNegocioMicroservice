package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceNegocio;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Negocio;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;
import com.dagnerchuman.miaplicativonegociomicroservice.adapter.ProductoAdapter;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceProductos;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ConfigApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceNegocio;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Negocio;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;
import com.dagnerchuman.miaplicativonegociomicroservice.adapter.ProductoAdapter;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceProductos;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ConfigApi;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntradaActivity extends AppCompatActivity {

    private ImageButton btnBackToLogin;
    private ImageButton btnNavigation;

    private RecyclerView recyclerViewProductos;
    private SearchView searchView;

    private ProductoAdapter adapter;
    private List<Producto> productosList;

    private AlertDialog popupDialog;
    private List<Producto> carrito = new ArrayList<>(); // Lista de productos en el carrito

    private int productosEnCarrito = 0; // Variable para llevar un registro de la cantidad de productos en el carrito

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);

        btnBackToLogin = findViewById(R.id.btnBackToLogin);
        recyclerViewProductos = findViewById(R.id.recyclerViewProductos);
        btnNavigation = findViewById(R.id.btnNavigation);
        searchView = findViewById(R.id.searchView);

        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));
        productosList = new ArrayList<>();
        adapter = new ProductoAdapter(this, productosList); // Pasar "this" como el tercer argumento
        recyclerViewProductos.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        Long userNegocioId = sharedPreferences.getLong("userNegocioId", -1);

        // Llama a la API para obtener los productos del mismo negocio que el usuario
        obtenerProductosDelNegocio(userNegocioId);

        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarPopupMisDatos();
            }
        });

        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // Corregir "toonClick" a "onClick"
                Intent loginIntent = new Intent(EntradaActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // El texto de búsqueda está vacío, oculta el RecyclerView
                    recyclerViewProductos.setVisibility(View.GONE);
                } else {
                    // El texto de búsqueda no está vacío, muestra el RecyclerView
                    recyclerViewProductos.setVisibility(View.VISIBLE);
                    adapter.filterProductos(newText);
                }
                return true;
            }
        });

        // Obtener el nombre del negocio y establecerlo en el Toolbar
        obtenerNombreNegocio(userNegocioId);
    }

    // Método para obtener los productos del mismo negocio que el usuario
    private void obtenerProductosDelNegocio(Long userNegocioId) {
        ApiServiceProductos apiService = ConfigApi.getInstanceProducto(this);

        Call<List<Producto>> call = apiService.getAllProductos();

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()) {
                    List<Producto> productos = response.body();

                    // Filtra los productos que pertenecen al mismo negocio que el usuario
                    List<Producto> productosDelNegocio = new ArrayList<>();
                    for (Producto producto : productos) {
                        if (producto.getNegocioId().equals(userNegocioId)) {
                            productosDelNegocio.add(producto);
                        }
                    }

                    // Actualiza la lista de productos en el adaptador
                    productosList.clear();
                    productosList.addAll(productosDelNegocio);
                    adapter.notifyDataSetChanged();

                    Log.d("API Response", "Respuesta exitosa");
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

    private void mostrarPopupMisDatos() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setPositiveButton("Cerrar Ventana", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (popupDialog != null && popupDialog.isShowing()) {
                    popupDialog.dismiss();
                }
            }
        });

        alertDialogBuilder.setNeutralButton("Ver Mis Datos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                verMisDatos();
            }
        });

        alertDialogBuilder.setNegativeButton("Ver Compras", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                verCompras();
            }
        });

        popupDialog = alertDialogBuilder.create();
        popupDialog.show();
    }

    // Método para manejar el clic del botón "Ver Mis Datos"
    private void verMisDatos() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    // Método para manejar el clic del botón "Ver Negocios"
    private void verNegocios() {
        Intent mainIntentN = new Intent(this, NegociosActivity.class);
        startActivity(mainIntentN);
    }

    // Método para manejar el clic del botón "Ver Compras"
    private void verCompras() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("userId", -1);

        Intent mainIntentN = new Intent(this, ListadoDeComprasActivity.class);
        mainIntentN.putExtra("userId", userId);
        startActivity(mainIntentN);
    }

    // Método para obtener el nombre del negocio
    private void obtenerNombreNegocio(Long userNegocioId) {
        ApiServiceNegocio apiServiceNegocio = ConfigApi.getInstanceNegocio(this);

        Call<Negocio> call = apiServiceNegocio.getNegocioById(userNegocioId);

        call.enqueue(new Callback<Negocio>() {
            @Override
            public void onResponse(Call<Negocio> call, Response<Negocio> response) {
                if (response.isSuccessful()) {
                    Negocio negocio = response.body();
                    if (negocio != null) {
                        // Obtén el nombre del negocio
                        String nombreNegocio = negocio.getNombre();

                        Log.d("API Response", "Nombre del negocio obtenido: " + nombreNegocio); // Agregar este log

                        // Obtén la instancia del Toolbar
                        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

                        // Establece el nombre del negocio como título del Toolbar
                        toolbar.setTitle(nombreNegocio);
                        setSupportActionBar(toolbar);
                    }
                }
            }

            @Override
            public void onFailure(Call<Negocio> call, Throwable t) {
                // Maneja el error de la solicitud a la API aquí
                Log.e("API Failure", "Fallo en la solicitud a la API", t);
            }
        });
    }

    public void updateCarritoIcon(int carritoSize) {
        productosEnCarrito = carritoSize;

        // Actualizar el ícono del carrito en la barra de herramientas
        invalidateOptionsMenu();
    }

    public boolean addToCart(Producto producto) {
        // Comprobar si el producto ya está en el carrito
        if (!carrito.contains(producto)) {
            carrito.add(producto);
            productosEnCarrito++; // Aumenta la cantidad de productos en el carrito
            updateCarritoIcon(productosEnCarrito);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_entrada, menu);
        MenuItem carritoItem = menu.findItem(R.id.menu_cart);
        if (productosEnCarrito > 0) {
            carritoItem.setIcon(R.drawable.ic_add_shop);
        } else {
            carritoItem.setIcon(R.drawable.ic_add_shop);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_cart) {
            navigateToCarrito();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToCarrito() {
        Intent carritoIntent = new Intent(this, CarritoActivity.class);
        Gson gson = new Gson();
        String carritoJson = gson.toJson(carrito);
        carritoIntent.putExtra("carrito", carritoJson);
        startActivity(carritoIntent);
    }

    private void setupToolbar(String negocioName) {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(negocioName);
        setSupportActionBar(toolbar);
    }

    private void showErrorDialog(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Error");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Cerrar", null);
        alertDialogBuilder.show();
    }
}

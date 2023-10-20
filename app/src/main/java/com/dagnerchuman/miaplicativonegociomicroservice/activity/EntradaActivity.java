package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceCategorias;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceNegocio;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Categoria;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Negocio;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;
import com.dagnerchuman.miaplicativonegociomicroservice.adapter.ProductoAdapter;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceProductos;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ConfigApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class EntradaActivity extends AppCompatActivity implements ProductoAdapter.OnProductSelectedListener {

    private ImageButton btnBackToLogin;
    private ImageButton btnNavigation;

    private RecyclerView recyclerViewProductos;
    private SearchView searchView;

    private ProductoAdapter adapter;
    private List<Producto> productosList;

    private AlertDialog popupDialog;
    private List<Producto> carrito = new ArrayList<>(); // Lista de productos en el carrito
    private List<Producto> productosSeleccionados = new ArrayList<>();

    private int productosEnCarrito = 0; // Variable para llevar un registro de la cantidad de productos en el carrito
    // Variable para almacenar el nombre del negocio
    private int categoriaCount = 0;

    private String nombreNegocio;
    private View customTitle; // Declarar customTitle como una variable miembro
    private TextView toolbarTitle; // Declarar la variable para el título
    private LinearLayout categoryButtonContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);

        btnBackToLogin = findViewById(R.id.btnBackToLogin);
        btnNavigation = findViewById(R.id.btnNavigation);
        searchView = findViewById(R.id.searchView);
        recyclerViewProductos = findViewById(R.id.recyclerView);
        productosList = new ArrayList<>();

        // Encuentra el contenedor de botones de categoría en tu diseño
        categoryButtonContainer = findViewById(R.id.categoryButtonContainer);


        // Infla el diseño personalizado para el título centrado
        customTitle = getLayoutInflater().inflate(R.layout.custom_toolbar_title, null);

        // Encuentra el título dentro del diseño personalizado
        toolbarTitle = customTitle.findViewById(R.id.toolbar_title);

        // Configura el título del negocio (asegúrate de que tengas una función obtenerNombreNegocio definida)
        obtenerNombreNegocio();

        // Inicializa el adaptador para el RecyclerView de búsqueda
        adapter = new ProductoAdapter(this, productosList, this); // Pasa "this" como la referencia a EntradaActivity
        recyclerViewProductos.setAdapter(adapter);

        // Configura el RecyclerView con el adaptador
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        Long userNegocioId = sharedPreferences.getLong("userNegocioId", -1);

        TextView toolbarTitle = customTitle.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(nombreNegocio);


        // Realiza una llamada a la API para obtener las categorías
        Log.d("EntradaActivity", "Obteniendo categorías...");
        obtenerCategorias(userNegocioId);


        obtenerProductosDelNegocio(userNegocioId);

        // Configuración del botón de navegación
        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarPopupMisDatos();
            }
        });

        // Configuración del botón de retroceso
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(EntradaActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        // Configuración del SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    recyclerViewProductos.setVisibility(View.GONE);
                } else {
                    recyclerViewProductos.setVisibility(View.VISIBLE);
                    adapter.filterProductos(newText);
                }
                return true;
            }
        });
        // Ejemplo de cómo configurar una barra de herramientas personalizada
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            // Establece el diseño personalizado como vista de título en la barra de herramientas
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(customTitle);
        }
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
                    productosList.addAll(productosDelNegocio);

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

    private void obtenerNombreNegocio() {
        ApiServiceNegocio apiServiceNegocio = ConfigApi.getInstanceNegocio(this);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        Long userNegocioId = sharedPreferences.getLong("userNegocioId", -1);

        Call<Negocio> call = apiServiceNegocio.getNegocioById(userNegocioId);

        call.enqueue(new Callback<Negocio>() {
            @Override
            public void onResponse(Call<Negocio> call, Response<Negocio> response) {
                if (response.isSuccessful()) {
                    Negocio negocio = response.body();
                    if (negocio != null) {
                        // Obtén el nombre del negocio
                        String nombreNegocio = negocio.getNombre();
                        Log.d("API Response", "Nombre del negocio obtenido: " + nombreNegocio);

                        // Configura el nombre del negocio como título centrado en la barra de herramientas
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setDisplayShowTitleEnabled(false);
                            getSupportActionBar().setDisplayShowCustomEnabled(true);

                            // Infla el diseño personalizado para el título centrado
                            customTitle = getLayoutInflater().inflate(R.layout.custom_toolbar_title, null);
                            toolbarTitle = customTitle.findViewById(R.id.toolbar_title); // Asigna la referencia a la variable
                            toolbarTitle.setText(nombreNegocio);
                            getSupportActionBar().setCustomView(customTitle);
                        }
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
        invalidateOptionsMenu();
    }


    public boolean addToCart(Producto producto) {
        // Comprobar si el producto ya está en el carrito
        if (!carrito.contains(producto)) {
            carrito.add(producto);
            productosEnCarrito++; // Aumenta la cantidad de productos en el carrito
            updateCarritoIcon(productosEnCarrito);

            // Agrega un registro de depuración para verificar que se agregó un producto al carrito.
            Log.d("Carrito", "Producto agregado al carrito: " + producto.getNombre());

            return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_entrada, menu);
        MenuItem carritoItem = menu.findItem(R.id.menu_cart);
        carritoItem.setIcon(R.drawable.ic_add_shop);
        return true;
    }

    // Método para manejar clics en el ícono del carrito (menu_cart)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_cart) {
            Intent intent = new Intent(this, ComprarActivity.class);
            // Obtener el carrito del adaptador y pasarlo a ComprarActivity
            intent.putExtra("productosSeleccionados", (Serializable) adapter.getCarrito());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Implementación del método de la interfaz para recibir productos seleccionados
    @Override
    public void onProductSelected(Producto producto) {
        productosSeleccionados.add(producto);
    }

    private void obtenerCategorias(Long userNegocioId) {
        ApiServiceCategorias apiServiceCategorias = ConfigApi.getInstanceCategorias(this);
        Context context = this; // Si te encuentras en una actividad

        String userToken = ApiServiceCategorias.getUserToken(context); // Obtén el token de usuario desde las preferencias
        Call<List<Categoria>> call = apiServiceCategorias.getAllCategorias("Bearer " + userToken); // Agrega "Bearer " antes del token


        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()) {
                    List<Categoria> categorias = response.body();

                    for (Categoria categoria : categorias) {
                        Log.d("Categoría", "ID: " + categoria.getId() + ", Nombre: " + categoria.getNombre() + ", NegocioId: " + categoria.getNegocioId());
                        if (categoria.getNegocioId().equals(userNegocioId)) {
                            // Incrementa el contador
                            categoriaCount++;

                            // Crea un nuevo botón para la categoría
                            Button categoryButton = new Button(EntradaActivity.this);
                            categoryButton.setText(categoria.getNombre());

                            // Configura el clic del botón para manejar la selección de la categoría
                            categoryButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Maneja la selección de la categoría aquí
                                    Log.d("Categoría seleccionada", "ID: " + categoria.getId() + ", Nombre: " + categoria.getNombre());
                                }
                            });

                            // Agrega el botón al contenedor de botones de categoría
                            categoryButtonContainer.addView(categoryButton);
                        }
                    }

                    // Agrega un registro para mostrar la cantidad total de categorías
                    Log.d("Categorías totales", "Cantidad: " + categoriaCount);
                } else {
                    Log.e("API Response", "Respuesta no exitosa: " + response.code());
                    // Agregar un registro para mostrar el estado del servidor en caso de una respuesta no exitosa
                    Log.e("API Response", "Estado del servidor: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                // Maneja el error de la solicitud a la API aquí
                Log.e("API Failure", "Fallo en la solicitud a la API", t);
            }
        });
    }
}
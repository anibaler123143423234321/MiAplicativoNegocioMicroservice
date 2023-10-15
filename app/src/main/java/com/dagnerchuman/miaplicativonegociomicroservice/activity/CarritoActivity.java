package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.adapter.ProductoAdapter;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CarritoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCarrito;
    private Button checkoutButton;
    private ProductoAdapter carritoAdapter;
    private List<Producto> productosEnCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        recyclerViewCarrito = findViewById(R.id.recyclerViewCarrito);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Aquí debes obtener la lista de productos que el usuario agregó al carrito
        productosEnCarrito = getProductsInCart();
        Log.d("CarritoActivity", "Cantidad de productos en el carrito: " + productosEnCarrito.size());

        // Configura el RecyclerView para mostrar los productos en el carrito
        recyclerViewCarrito.setLayoutManager(new LinearLayoutManager(this));
        carritoAdapter = new ProductoAdapter(this, productosEnCarrito);
        recyclerViewCarrito.setAdapter(carritoAdapter);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Agrega aquí la lógica para procesar el pago y finalizar la compra
                confirmarCompra();
            }
        });
    }

    private List<Producto> getProductsInCart() {
        // Implementa esta función para obtener la lista de productos en el carrito
        SharedPreferences sharedPreferences = getSharedPreferences("CarritoData", MODE_PRIVATE);
        String carritoJson = sharedPreferences.getString("carrito", "");

        if (!carritoJson.isEmpty()) {
            // Si hay datos en el carrito, convierte el JSON de productos en una lista
            Gson gson = new Gson();
            Type productListType = new TypeToken<List<Producto>>() {}.getType();
            return gson.fromJson(carritoJson, productListType);
        }

        return new ArrayList<>(); // Devuelve una lista vacía si no hay productos en el carrito
    }

    private void confirmarCompra() {
        // Aquí puedes implementar la lógica para procesar la compra
        // Por ejemplo, enviar la orden al servidor y procesar el pago.
        // Luego, puedes mostrar un mensaje de confirmación y borrar los productos del carrito.
        // Luego de completar la compra, puedes redirigir al usuario a la actividad principal u otra pantalla.
        // Aquí, simplemente mostraremos un mensaje de confirmación.
        Toast.makeText(this, "Compra confirmada", Toast.LENGTH_SHORT).show();
        // Borra los productos del carrito
        productosEnCarrito.clear();
        carritoAdapter.notifyDataSetChanged();
    }
}

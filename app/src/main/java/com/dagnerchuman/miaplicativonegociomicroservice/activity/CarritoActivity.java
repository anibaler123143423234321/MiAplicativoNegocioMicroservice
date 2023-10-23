package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.adapter.CarritoAdapter;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;

import java.util.List;

public class CarritoActivity extends AppCompatActivity {
    private List<Producto> productosEnCarrito;
    private TextView txtTotal;
    private Button btnFinalizarCompra;
    private CarritoAdapter carritoAdapter; // Crea un adaptador personalizado
    private RecyclerView recyclerView; // Agrega esta línea

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        txtTotal = findViewById(R.id.txtTotal);
        btnFinalizarCompra = findViewById(R.id.btnFinalizarCompra);

        // Recibe los productos del carrito pasados desde CategoriaProductosActivity
        productosEnCarrito = (List<Producto>) getIntent().getSerializableExtra("productosEnCarrito");

// Configura el RecyclerView
        recyclerView = findViewById(R.id.recyclerViewCarrito);
        carritoAdapter = new CarritoAdapter(this, productosEnCarrito);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(carritoAdapter);


        // Configura el botón para finalizar la compra
        btnFinalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implementa la lógica para finalizar la compra aquí.
                // Puedes redirigir al usuario a una actividad de pago, por ejemplo.
            }
        });

        // Calcula y muestra el total de la compra
        calcularTotal();
    }

    // Método para calcular el total de la compra en el carrito
    private void calcularTotal() {
        double total = 0;
        for (Producto producto : productosEnCarrito) {
            total += producto.getPrecio();
        }
        txtTotal.setText("Total: $" + total);
    }

    public void confirmarCompra(Long userId, Long productoId, String titulo, Double precioCompra, Integer cantidad, String tipoEnvio, String tipoDePago) {
        // Aquí puedes implementar la lógica para confirmar la compra.
        // Utiliza los datos recibidos para realizar la confirmación de la compra.

        // Por ejemplo, puedes mostrar un diálogo de confirmación y procesar la compra.
    }


}

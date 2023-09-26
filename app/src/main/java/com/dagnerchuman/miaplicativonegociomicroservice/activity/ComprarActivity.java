package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceCompras;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ConfigApi;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Compra;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComprarActivity extends AppCompatActivity {

    private TextView txtUserId;
    private TextView txtProductoId;
    private TextView txtNombreProducto;
    private TextView txtPrecioProducto;
    private ImageView imgProducto;
    private EditText edtCantidadDeseada;
    private Button btnConfirmarCompra;
    private ApiServiceCompras apiServiceCompras;

    private Long userId;
    private Long productoId;
    private String titulo;
    private Double precioCompra;
    private Integer cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar);

        txtUserId = findViewById(R.id.txtUserId);
        txtProductoId = findViewById(R.id.txtProductoId);
        txtNombreProducto = findViewById(R.id.txtNombreProducto);
        txtPrecioProducto = findViewById(R.id.txtPrecioProducto);
        imgProducto = findViewById(R.id.imgProducto);
        edtCantidadDeseada = findViewById(R.id.edtCantidadDeseada);
        btnConfirmarCompra = findViewById(R.id.btnConfirmarCompra);

        apiServiceCompras = ConfigApi.getInstanceCompra(this);

        // Obtén los extras enviados desde EntradaActivity
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getLongExtra("userId", -1);
            productoId = intent.getLongExtra("productoId", -1);
            String nombreProducto = intent.getStringExtra("nombreProducto");
            double precioProducto = intent.getDoubleExtra("precioProducto", 0.0);
            String imagenProducto = intent.getStringExtra("imagenProducto");

            mostrarDetallesDelProducto(userId, productoId, nombreProducto, precioProducto, imagenProducto);
        }

        btnConfirmarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmarCompra();
            }
        });
    }

    private void mostrarDetallesDelProducto(Long userId, Long productoId, String nombreProducto, double precioProducto, String imagenProducto) {
        txtUserId.setText("ID del Usuario: " + userId);
        txtProductoId.setText("ID del Producto: " + productoId);
        txtNombreProducto.setText("Nombre del Producto: " + nombreProducto);
        txtPrecioProducto.setText("Precio: $" + precioProducto);
        titulo = nombreProducto;
        precioCompra = precioProducto;

        Glide.with(this)
                .load(imagenProducto)
                .into(imgProducto);
    }

    private void confirmarCompra() {
        String cantidadDeseadaStr = edtCantidadDeseada.getText().toString().trim();

        if (!cantidadDeseadaStr.isEmpty()) {
            cantidad = Integer.parseInt(cantidadDeseadaStr);

            Compra compra = new Compra();
            compra.setUserId(userId);
            compra.setProductoId(productoId);
            compra.setTitulo(titulo);
            compra.setPrecioCompra(precioCompra);
            compra.setCantidad(cantidad);

            Call<Compra> call = apiServiceCompras.saveCompra(compra);
            call.enqueue(new Callback<Compra>() {
                @Override
                public void onResponse(Call<Compra> call, Response<Compra> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Compra compraConfirmada = response.body();
                        mostrarMensajeCompraExitosa();
                    } else {
                        Toast.makeText(ComprarActivity.this, "No se pudo confirmar la compra", Toast.LENGTH_SHORT).show();
                        Log.e("Compra", "Error en la respuesta: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Compra> call, Throwable t) {
                    Toast.makeText(ComprarActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    Log.e("Compra", "Error de conexión", t);
                }
            });
        } else {
            Toast.makeText(this, "Ingresa la cantidad deseada", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarMensajeCompraExitosa() {
        Toast.makeText(this, "Compra realizada con éxito", Toast.LENGTH_SHORT).show();
    }
}

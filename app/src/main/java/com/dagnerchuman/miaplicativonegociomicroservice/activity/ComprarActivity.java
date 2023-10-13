package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceCompras;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceProductos;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ConfigApi;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Compra;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ComprarActivity extends AppCompatActivity {

    private TextView txtUserId;
    private TextView txtProductoId;
    private TextView txtNombreProducto;
    private TextView txtPrecioProducto;
    private TextView txtStockProducto;
    private ImageView imgProducto;
    private EditText edtCantidadDeseada;
    private Button btnConfirmarCompra;
    private RadioGroup radioGroupEnvio;
    private RadioGroup radioGroupPago;
    private ApiServiceCompras apiServiceCompras;
    private ApiServiceProductos apiServiceProductos;

    private Long userId;
    private Long productoId;
    private String titulo;
    private Double precioCompra;
    private int stockProducto;
    private Integer cantidad;
    private String tipoEnvio;
    private String tipoDePago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar);

        initView();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getLongExtra("userId", -1);
            productoId = intent.getLongExtra("productoId", -1);
            String nombreProducto = intent.getStringExtra("nombreProducto");
            double precioProducto = intent.getDoubleExtra("precioProducto", 0.0);
            stockProducto = intent.getIntExtra("stockProducto", 0);
            String imagenProducto = intent.getStringExtra("imagenProducto");

            mostrarDetallesDelProducto(userId, productoId, nombreProducto, precioProducto, stockProducto, imagenProducto);
        }

        btnConfirmarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmarCompra();
            }
        });
    }

    private void initView() {
        txtUserId = findViewById(R.id.txtUserId);
        txtProductoId = findViewById(R.id.txtProductoId);
        txtNombreProducto = findViewById(R.id.txtNombreProducto);
        txtPrecioProducto = findViewById(R.id.txtPrecioProducto);
        txtStockProducto = findViewById(R.id.txtStockProducto);
        imgProducto = findViewById(R.id.imgProducto);
        edtCantidadDeseada = findViewById(R.id.edtCantidadDeseada);
        btnConfirmarCompra = findViewById(R.id.btnConfirmarCompra);
        radioGroupEnvio = findViewById(R.id.radioGroupEnvio);
        radioGroupPago = findViewById(R.id.radioGroupPago);
        apiServiceCompras = ConfigApi.getInstanceCompra(this);
        apiServiceProductos = ConfigApi.getInstanceProducto(this);
    }

    private void mostrarDetallesDelProducto(Long userId, Long productoId, String nombreProducto, double precioProducto, int stockProducto, String imagenProducto) {
        txtUserId.setText("ID del Usuario: " + userId);
        txtProductoId.setText("ID del Producto: " + productoId);
        txtNombreProducto.setText("Nombre del Producto: " + nombreProducto);
        txtPrecioProducto.setText("Precio: $" + precioProducto);
        txtStockProducto.setText("Stock: " + stockProducto);
        titulo = nombreProducto;
        precioCompra = precioProducto;

        if (imagenProducto != null && !imagenProducto.isEmpty()) {
            Glide.with(this)
                    .load(imagenProducto)
                    .into(imgProducto);
        } else {
            imgProducto.setImageResource(R.drawable.image_not_found);
        }
    }

    private void confirmarCompra() {
        String cantidadDeseadaStr = edtCantidadDeseada.getText().toString().trim();

        if (!cantidadDeseadaStr.isEmpty()) {
            cantidad = Integer.parseInt(cantidadDeseadaStr);
            tipoEnvio = obtenerValorRadioSeleccionado(radioGroupEnvio);
            tipoDePago = obtenerValorRadioSeleccionado(radioGroupPago);

            Compra compra = new Compra();
            compra.setUserId(userId);
            compra.setProductoId(productoId);
            compra.setTitulo(titulo);
            compra.setPrecioCompra(precioCompra);
            compra.setCantidad(cantidad);
            compra.setTipoEnvio(tipoEnvio);
            compra.setTipoDePago(tipoDePago);

            Call<Compra> call = apiServiceCompras.saveCompra(compra);
            call.enqueue(new Callback<Compra>() {
                @Override
                public void onResponse(Call<Compra> call, Response<Compra> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Compra compraConfirmada = response.body();
                        Log.i("ComprarActivity", "Compra confirmada con éxito. ID: " + compraConfirmada.getId());

                        int nuevoStock = stockProducto - cantidad;
                        actualizarStockProducto(productoId, nuevoStock);
                    } else {
                        Toast.makeText(ComprarActivity.this, "No se pudo confirmar la compra", Toast.LENGTH_SHORT).show();
                        Log.e("ComprarActivity", "Error en la respuesta: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Compra> call, Throwable t) {
                    Toast.makeText(ComprarActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    Log.e("ComprarActivity", "Error de conexión", t);
                }
            });
        } else {
            Toast.makeText(this, "Ingresa la cantidad deseada", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarStockProducto(Long productoId, int nuevoStock) {
        Call<Producto> call = apiServiceProductos.getProductoById(productoId);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Producto producto = response.body();
                    producto.setStock(nuevoStock);

                    Call<Producto> updateCall = apiServiceProductos.actualizarProducto(productoId, producto);
                    updateCall.enqueue(new Callback<Producto>() {
                        @Override
                        public void onResponse(Call<Producto> call, Response<Producto> response) {
                            if (response.isSuccessful()) {
                                Log.i("ComprarActivity", "Stock del producto actualizado con éxito.");
                                mostrarMensajeCompraExitosa();
                            } else {
                                Toast.makeText(ComprarActivity.this, "No se pudo actualizar el stock del producto", Toast.LENGTH_SHORT).show();
                                Log.e("ComprarActivity", "Error en la respuesta al actualizar el stock del producto: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Producto> call, Throwable t) {
                            Toast.makeText(ComprarActivity.this, "Error de conexión al actualizar el stock del producto", Toast.LENGTH_SHORT).show();
                            Log.e("ComprarActivity", "Error de conexión al actualizar el stock del producto", t);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(ComprarActivity.this, "Error de conexión al obtener el detalle del producto", Toast.LENGTH_SHORT).show();
                Log.e("ComprarActivity", "Error de conexión al obtener el detalle del producto", t);
            }
        });
    }

    private void mostrarMensajeCompraExitosa() {
        Toast.makeText(this, "Compra realizada con éxito", Toast.LENGTH_SHORT).show();
        Intent entradaIntent = new Intent(ComprarActivity.this, EntradaActivity.class);
        startActivity(entradaIntent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private String obtenerValorRadioSeleccionado(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        return radioButton != null ? radioButton.getText().toString() : "";
    }
}

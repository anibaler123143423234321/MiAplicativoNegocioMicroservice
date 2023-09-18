package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import com.dagnerchuman.miaplicativonegociomicroservice.R;

public class ComprarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar);

        // Configura la barra de herramientas (Toolbar) con el botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Oculta el título del Toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Agrega un oyente al botón de retroceso
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Regresar a la actividad EntradaActivity
                Intent intent = new Intent(ComprarActivity.this, EntradaActivity.class);
                startActivity(intent);
                finish(); // Finaliza la actividad actual para que no pueda volver atrás desde EntradaActivity
            }
        });

        // Aquí puedes recibir los datos pasados desde la actividad de la lista
        Intent intent = getIntent();
        if (intent != null) {
            int productoId = intent.getIntExtra("producto_id", -1);
            // Realiza acciones con el producto (por ejemplo, muestra detalles, realiza la compra, etc.)
        }

        // Resto del código de ComprarActivity
    }
}

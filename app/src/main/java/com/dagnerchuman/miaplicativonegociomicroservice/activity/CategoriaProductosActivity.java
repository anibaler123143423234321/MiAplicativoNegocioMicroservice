package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.adapter.CategoriaAdapter;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;

import java.util.ArrayList;
import java.util.List;

public class CategoriaProductosActivity extends AppCompatActivity {
    private List<Producto> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_productos);

        // Obtener el ID de la categoría de la actividad anterior
        long categoriaId = getIntent().getLongExtra("categoriaId", -1);

    }
}

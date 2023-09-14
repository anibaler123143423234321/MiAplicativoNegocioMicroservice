// MainActivity
package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.dagnerchuman.miaplicativonegociomicroservice.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa la Toolbar como ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Habilita el botón de retroceso en la Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inicializa el ImageView del botón de retroceso
        ImageView imageViewBack = findViewById(R.id.imageViewBack2);

        // Agrega un OnClickListener al ImageView para volver a EntradaActivity
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Configura los datos del usuario en el Intent
                Intent mainIntent = new Intent(MainActivity.this, EntradaActivity.class);
                mainIntent.putExtra("userEmail", getIntent().getStringExtra("userEmail"));
                mainIntent.putExtra("userName", getIntent().getStringExtra("userName"));
                mainIntent.putExtra("userApellido", getIntent().getStringExtra("userApellido"));
                mainIntent.putExtra("userTelefono", getIntent().getStringExtra("userTelefono"));
                mainIntent.putExtra("userFechaCreacion", getIntent().getStringExtra("userFechaCreacion"));
                mainIntent.putExtra("userId", getIntent().getLongExtra("userId", -1));
                mainIntent.putExtra("userNegocioId", getIntent().getLongExtra("userNegocioId", -1));

                // Iniciar MainActivity con startActivityForResult
                startActivity(mainIntent);
            }
        });


        // Inicializa las vistas
        TextView textViewHello = findViewById(R.id.textViewHello);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewNombre = findViewById(R.id.textViewNombre);
        TextView textViewApellido = findViewById(R.id.textViewApellido);
        TextView textViewTelefono = findViewById(R.id.textViewTelefono);
        TextView textViewUserId = findViewById(R.id.textViewUserId);
        TextView textViewNegocioId = findViewById(R.id.textViewNegocioId);

        // Configura el texto del TextView
        textViewHello.setText("¡Bienvenido a Mi Aplicativo Negocio Microservice!");

        // Recupera los valores del Intent
        String userEmail = getIntent().getStringExtra("userEmail");
        String userName = getIntent().getStringExtra("userName");
        String userApellido = getIntent().getStringExtra("userApellido");
        String userTelefono = getIntent().getStringExtra("userTelefono");
        Long userId = getIntent().getLongExtra("userId", -1);
        Long negocioId = getIntent().getLongExtra("userNegocioId", -1);

        // Muestra la información en los TextViews
        if (userEmail != null) {
            textViewEmail.setText("Email del usuario: " + userEmail);
        } else {
            textViewEmail.setText("Email no disponible");
        }

        if (userName != null) {
            textViewNombre.setText("Nombre del usuario: " + userName);
        } else {
            textViewNombre.setText("Nombre no disponible");
        }

        if (userApellido != null) {
            textViewApellido.setText("Apellido del usuario: " + userApellido);
        } else {
            textViewApellido.setText("Apellido no disponible");
        }

        if (userTelefono != null) {
            textViewTelefono.setText("Teléfono del usuario: " + userTelefono);
        } else {
            textViewTelefono.setText("Teléfono no disponible");
        }

        if (userId != -1) {
            textViewUserId.setText("ID del usuario: " + userId);
        } else {
            textViewUserId.setText("ID no disponible");
        }

        if (negocioId != -1) {
            textViewNegocioId.setText("ID del negocio: " + negocioId);
        } else {
            textViewNegocioId.setText("ID de negocio no disponible");
        }
    }
}

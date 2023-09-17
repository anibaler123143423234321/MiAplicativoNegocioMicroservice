// EntradaActivity
package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.User;

public class EntradaActivity extends AppCompatActivity {

    private Button btnVerUsuario;
    private Button btnVerNegocios;
    private ImageButton btnBackToLogin; // Nuevo botón

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);

        // Inicializa las vistas
        btnVerUsuario = findViewById(R.id.btnVerUsuario);
        btnVerNegocios = findViewById(R.id.btnVerNegocios);
        btnBackToLogin = findViewById(R.id.btnBackToLogin); // Inicializa el nuevo botón

        // Recupera los datos del usuario desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", "");
        String userName = sharedPreferences.getString("userName", "");
        String userApellido = sharedPreferences.getString("userApellido", "");
        String userTelefono = sharedPreferences.getString("userTelefono", "");
        Long userId = sharedPreferences.getLong("userId", -1);
        Long userNegocioId = sharedPreferences.getLong("userNegocioId", -1);


        // Configura los eventos click para los botones
        btnVerUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Configura los datos del usuario en el Intent
                Intent mainIntent = new Intent(EntradaActivity.this, MainActivity.class);
                mainIntent.putExtra("userEmail", userEmail);
                mainIntent.putExtra("userName", userName);
                mainIntent.putExtra("userApellido", userApellido);
                mainIntent.putExtra("userTelefono", userTelefono);
                mainIntent.putExtra("userId", userId);
                mainIntent.putExtra("userNegocioId", userNegocioId);

                // Iniciar MainActivity con startActivityForResult
                startActivity(mainIntent);
            }
        });




        // Configura el evento click para el botón de regreso
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Regresa a LoginActivity
                Intent loginIntent = new Intent(EntradaActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish(); // Cierra EntradaActivity para que el usuario no pueda volver atrás
            }
        });



        btnVerNegocios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí debes implementar la lógica para ver la lista de negocios
                // Puedes redirigir a una actividad donde muestres la lista de negocios
                // Por ejemplo:
                startActivity(new Intent(EntradaActivity.this, NegociosActivity.class));
            }
        });
    }

    // Método para recibir resultados de MainActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Actualiza los datos del usuario según los resultados recibidos de MainActivity
            if (data != null) {
                String userEmail = data.getStringExtra("userEmail");
                String userName = data.getStringExtra("userName");
                String userApellido = data.getStringExtra("userApellido");
                String userTelefono = data.getStringExtra("userTelefono");
                Long userId = data.getLongExtra("userId", -1);
                Long userNegocioId = data.getLongExtra("userNegocioId", -1);

                // Puedes actualizar tus vistas con los nuevos datos aquí
            }
        }
    }

}

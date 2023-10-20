package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceNegocio;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ConfigApi;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.User;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiService;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private Button buttonSignIn;

    private static final int REQUEST_INTERNET_PERMISSION = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializa las vistas
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);

        // Verifica si tienes permiso de acceso a Internet
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET_PERMISSION);
        }

        // Configura el evento click para el botón "Iniciar Sesión"
        buttonSignIn.setOnClickListener(view -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            if (validateInput(email, password)) {
                performSignIn(email, password);
            }
        });
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void performSignIn(String email, String password) {
        ApiService apiService = ConfigApi.getInstance(this);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        Call<User> call = apiService.signIn(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    handleSuccessfulLogin(response.body());
                } else {
                    handleLoginFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                handleNetworkError(t);
            }
        });
    }

    private void handleSuccessfulLogin(User user) {
        if (user != null) {
            String token = user.getToken();

            // Guarda los datos del usuario en SharedPreferences
            saveUserData(user);

            // Configura Retrofit con el token Bearer y obtén una instancia de ApiServiceNegocio
            ApiServiceNegocio apiServiceNegocio = ConfigApi.getInstanceNegocio(this);

            // Realiza las solicitudes utilizando apiServiceNegocio
            // Ejemplo de cómo usar apiServiceNegocio:
            // Call<Negocio> negocioCall = apiServiceNegocio.getNegocioById(123L);

            // Muestra un mensaje y navega a la siguiente actividad
            showToastAndNavigate("Inicio de sesión exitoso", EntradaActivity.class);
        }
    }


    private void handleLoginFailure() {
        Log.d("MiApp", "Inicio de sesión fallido");
        showToast("Inicio de sesión fallido");
    }

    private void handleNetworkError(Throwable t) {
        Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
        showToast("Error en la solicitud: " + t.getMessage());
    }

    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    private void showToastAndNavigate(String message, Class<?> targetActivity) {
        showToast(message);
        Intent intent = new Intent(LoginActivity.this, targetActivity);
        startActivity(intent);
        finish();
    }

    private void saveUserData(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Guarda todos los atributos del usuario
        editor.putString("userToken", user.getToken());
        editor.putString("userEmail", user.getEmail());
        editor.putString("userName", user.getNombre());
        editor.putString("userApellido", user.getApellido());
        editor.putString("userTelefono", user.getTelefono());
        editor.putLong("userId", user.getId());
        editor.putLong("userNegocioId", user.getNegocioId());
        // Agrega más atributos según las propiedades de la clase User

        editor.apply();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_INTERNET_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                performSignIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            } else {
                showToast("Permiso de Internet denegado");
            }
        }
    }

    public void onClickSignUp(View view) {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }
}

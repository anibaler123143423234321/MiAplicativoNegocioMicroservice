package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ConfigApi;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.User;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiService;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout textInputLayoutEmail;
    private TextInputEditText editTextEmail;
    private TextInputLayout textInputLayoutPassword;
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
            // Obtiene los valores de los campos de entrada
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            // Realiza la solicitud de inicio de sesión
            performSignIn(email, password);
        });
    }

    // Método para realizar la solicitud de inicio de sesión
    private void performSignIn(String email, String password) {
        // Obtén la instancia de ApiService de ConfigApi
        ApiService apiService = ConfigApi.getInstance(this);

        // Crea un objeto Usuario para la solicitud
        User usuario = new User();
        usuario.setEmail(email);
        usuario.setPassword(password);

        // Realiza la solicitud de inicio de sesión
        Call<User> call = apiService.signIn(usuario);

        Log.d("MiApp", "Antes de la solicitud de inicio de sesión");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    // La autenticación fue exitosa
                    Log.d("MiApp", "Inicio de sesión exitoso");
                    User user = response.body();

                    if (user != null) {
                        // Guarda el token en tu entidad User local
                        String token = user.getToken();
                        String userEmail = user.getEmail();
                        String userName = user.getNombre();
                        String userApellido = user.getApellido();
                        String userTelefono = user.getTelefono();
                        Long userId = user.getId();
                        Long userNegocioId = user.getNegocioId();


                        // Aquí puedes realizar las acciones necesarias después del inicio de sesión exitoso
                        Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                        // Pasa la información del usuario de LoginActivity  a MainActivity y abre la nueva actividad
                        Intent entradaIntent = new Intent(LoginActivity.this, EntradaActivity.class);
                      //  entradaIntent.putExtra("userEmail", user.getEmail());
                       // entradaIntent.putExtra("userName", user.getNombre());
                        // entradaIntent.putExtra("userApellido", user.getApellido());
                        //   entradaIntent.putExtra("userTelefono", user.getTelefono());
                        //  entradaIntent.putExtra("userFechaCreacion", user.getFechaCreacion());
                        //   entradaIntent.putExtra("userId", user.getId());
                        //  entradaIntent.putExtra("userNegocioId", user.getNegocioId());

                        // Guarda los datos del usuario en SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("userToken", token);
                        editor.putString("userEmail", userEmail);
                        editor.putString("userName", userName);
                        editor.putString("userApellido", userApellido);
                        editor.putString("userTelefono", userTelefono);
                        editor.putLong("userId", userId);
                        editor.putLong("userNegocioId", userNegocioId);
                        editor.apply();

                        startActivity(entradaIntent);

                        finish(); // Cierra esta actividad para que el usuario no pueda volver atrás
                    } else {
                        // Maneja el caso en que el usuario sea nulo
                    }
                } else {
                    // La autenticación falló
                    Log.d("MiApp", "Inicio de sesión fallido");
                    Toast.makeText(LoginActivity.this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                // Maneja el error de la solicitud de red aquí
                Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("MiApp", "Después de la solicitud de inicio de sesión");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_INTERNET_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso otorgado, realiza la solicitud de inicio de sesión
                performSignIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            } else {
                // Permiso denegado, maneja esto según tus necesidades
                Toast.makeText(this, "Permiso de Internet denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onClickSignUp(View view) {
        // Crea un Intent para iniciar RegisterActivity
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

}

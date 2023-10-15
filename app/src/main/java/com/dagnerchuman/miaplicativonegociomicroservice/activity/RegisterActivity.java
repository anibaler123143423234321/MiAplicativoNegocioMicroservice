package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiService;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ApiServiceNegocio;
import com.dagnerchuman.miaplicativonegociomicroservice.api.ConfigApi;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Negocio;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    // Declaraciones de las vistas y elementos
    private TextInputLayout textInputLayoutNombre;
    private TextInputEditText editTextNombre;
    private TextInputLayout textInputLayoutApellido;
    private TextInputEditText editTextApellido;
    private TextInputLayout textInputLayoutTelefono;
    private TextInputLayout textInputLayoutDNI;
    private TextInputEditText editTextTelefono;
    private TextInputEditText editTextDNI;
    private TextInputLayout textInputLayoutEmail;
    private TextInputEditText editTextEmail;
    private TextInputLayout textInputLayoutUsername;
    private TextInputEditText editTextUsername;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText editTextPassword;
    private Spinner spinnerNegocios;
    private Spinner spinnerTipoDocumento;
    private Spinner spinnerDepartamento;
    private Spinner spinnerProvincia;
    private Spinner spinnerDistrito;

    private Button buttonSignUp;
    private List<Negocio> listaNegocios;
    private Long selectedNegocioId = null;
    private ImageButton btnBackToLogin;

    private static final int REQUEST_INTERNET_PERMISSION = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializa las vistas y otros elementos
        spinnerNegocios = findViewById(R.id.spinnerNegocios);
        textInputLayoutNombre = findViewById(R.id.textInputLayoutNombre);
        textInputLayoutApellido = findViewById(R.id.textInputLayoutApellido);
        textInputLayoutTelefono = findViewById(R.id.textInputLayoutTelefono);
        textInputLayoutDNI = findViewById(R.id.textInputLayoutDNI);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutUsername = findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextDNI = findViewById(R.id.editTextDNI);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        spinnerTipoDocumento = findViewById(R.id.spinnerTipoDocumento);
        spinnerDepartamento = findViewById(R.id.spinnerDepartamento);
        spinnerProvincia = findViewById(R.id.spinnerProvincia);
        spinnerDistrito = findViewById(R.id.spinnerDistrito);

        // Crea adaptadores personalizados para los Spinners
        ArrayAdapter<CharSequence> tipoDocumentoAdapter = ArrayAdapter.createFromResource(this, R.array.document_types, R.layout.custom_spinner_item);
        tipoDocumentoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoDocumento.setAdapter(tipoDocumentoAdapter);

        ArrayAdapter<CharSequence> departamentoAdapter = ArrayAdapter.createFromResource(this, R.array.departamentos, R.layout.custom_spinner_item);
        departamentoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartamento.setAdapter(departamentoAdapter);

        ArrayAdapter<CharSequence> provinciaAdapter = ArrayAdapter.createFromResource(this, R.array.provincias, R.layout.custom_spinner_item);
        provinciaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvincia.setAdapter(provinciaAdapter);

        ArrayAdapter<CharSequence> distritoAdapter = ArrayAdapter.createFromResource(this, R.array.distritos, R.layout.custom_spinner_item);
        distritoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrito.setAdapter(distritoAdapter);


        // Verifica si tienes permiso de acceso a Internet
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET_PERMISSION);
        }
        // Configura el evento click para el botón "Registrarse"
        buttonSignUp.setOnClickListener(view -> {
            // Obtiene los valores de los campos de entrada
            String nombre = editTextNombre.getText().toString();
            String apellido = editTextApellido.getText().toString();
            String telefono = editTextTelefono.getText().toString();
            String email = editTextEmail.getText().toString();
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            String dni = editTextDNI.getText().toString();
            String tipoDoc = spinnerTipoDocumento.getSelectedItem().toString();
            String departamento = spinnerDepartamento.getSelectedItem().toString();
            String provincia = spinnerProvincia.getSelectedItem().toString();
            String distrito = spinnerDistrito.getSelectedItem().toString();

            // Realiza la solicitud de registro
            performSignUp(nombre, apellido, telefono, dni, email, username, password, tipoDoc, departamento, provincia, distrito);
        });

        // Configura el evento de selección del Spinner de negocios
        spinnerNegocios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtén el negocio seleccionado y su ID
                selectedNegocioId = listaNegocios.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Maneja el caso en que no se haya seleccionado nada en el Spinner
                selectedNegocioId = null;
            }
        });

        // Configura el evento de selección del Spinner de tipo de documento
        spinnerTipoDocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtén el tipo de documento seleccionado
                String selectedDocumentType = spinnerTipoDocumento.getSelectedItem().toString();
                // Puedes hacer algo con la selección, como guardarla en una variable.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Maneja el caso en que no se haya seleccionado nada
            }
        });

        // Obtén la lista de negocios y configura el Spinner
        getNegociosAndSetupSpinner();

        // Obtén una referencia al botón "Regresar a Login"
        btnBackToLogin = findViewById(R.id.btnBackToLogin);

        // Configura el evento click para el botón "Regresar a Login"
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MiApp", "Clic en el botón 'Regresar a Login'");

                // Resto del código para iniciar LoginActivity y cerrar la actividad actual
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }


    // Método para realizar la solicitud de registro
    private void performSignUp(String nombre, String apellido, String telefono, String dni, String email, String username, String password, String tipoDoc, String departamento, String provincia, String distrito) {
        // Verifica que se haya seleccionado un negocio
        if (selectedNegocioId == null) {
            Toast.makeText(this, "Selecciona un negocio válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtén la instancia de ApiService de ConfigApi
        ApiService apiService = ConfigApi.getInstance(this);

        // Crea un objeto Usuario para la solicitud
        User user = new User();
        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setTelefono(telefono);
        user.setDni(dni);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setNegocioId(selectedNegocioId);
        user.setTipoDoc(tipoDoc);
        user.setDepartamento(departamento);
        user.setProvincia(provincia);
        user.setDistrito(distrito);

        // Usa el negocio seleccionado

        // Realiza la solicitud de registro
        Call<User> call = apiService.signUp(user);

        Log.d("MiApp", "Antes de la solicitud de registro");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    // El registro fue exitoso
                    Log.d("MiApp", "Registro exitoso");
                    User user = response.body();

                    if (user != null) {
                        // Aquí puedes realizar las acciones necesarias después del registro exitoso
                        Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                        // Redirige al usuario a la actividad de inicio de sesión
                        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(loginIntent);

                        finish(); // Cierra esta actividad para que el usuario no pueda volver atrás
                    } else {
                        // Maneja el caso en que el usuario sea nulo
                    }
                } else {
                    // El registro falló
                    Log.d("MiApp", "Registro fallido");
                    Toast.makeText(RegisterActivity.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                // Maneja el error de la solicitud de red aquí
                Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("MiApp", "Después de la solicitud de registro");
    }

    // Método para obtener la lista de negocios y configurar el Spinner
    private void getNegociosAndSetupSpinner() {
        ApiServiceNegocio apiServiceNegocio = ConfigApi.getInstanceNegocio(this);

        Call<List<Negocio>> call = apiServiceNegocio.getAllNegocios();

        call.enqueue(new Callback<List<Negocio>>() {
            @Override
            public void onResponse(Call<List<Negocio>> call, Response<List<Negocio>> response) {
                if (response.isSuccessful()) {
                    listaNegocios = response.body();

                    // Configura el adaptador para el Spinner
                    ArrayAdapter<Negocio> adapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_item, listaNegocios);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerNegocios.setAdapter(adapter);
                } else {
                    // Maneja el caso en que no se pudo obtener la lista de negocios
                    Toast.makeText(RegisterActivity.this, "Error al obtener la lista de negocios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Negocio>> call, Throwable t) {
                // Maneja el error de la solicitud de red aquí
                Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

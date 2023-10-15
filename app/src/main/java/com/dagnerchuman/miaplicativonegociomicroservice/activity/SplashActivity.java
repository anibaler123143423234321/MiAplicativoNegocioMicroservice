package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import com.dagnerchuman.miaplicativonegociomicroservice.R;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 3000; // 3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Oculta la barra de acción si está presente
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        VideoView splashVideoView = findViewById(R.id.splashVideoView);

        // Establece la ruta del video y comienza la reproducción
        try {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.videoentrada;
            splashVideoView.setVideoPath(videoPath);
            splashVideoView.start();
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de excepción: muestra un mensaje de error o realiza otra acción en caso de error de video.
        }

        new Handler().postDelayed(() -> {
            // Navega a la actividad LoginActivity o la que desees después de la pantalla de carga
            Intent entradaIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(entradaIntent);
            finish();
        }, SPLASH_DELAY);
    }
}

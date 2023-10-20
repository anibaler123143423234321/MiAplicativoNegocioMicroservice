package com.dagnerchuman.miaplicativonegociomicroservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.dagnerchuman.miaplicativonegociomicroservice.R;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 2000; // 2 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Oculta la barra de acción si está presente
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        LottieAnimationView splashAnimationView = findViewById(R.id.animation_view);

        // Configura la animación y comienza la reproducción
        splashAnimationView.setAnimation(R.raw.anikihamster);
        splashAnimationView.setRepeatCount(LottieDrawable.INFINITE); // Repetir la animación
        splashAnimationView.playAnimation();

        new Handler().postDelayed(() -> {
            // Navega a la actividad LoginActivity o la que desees después de la pantalla de carga
            Intent entradaIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(entradaIntent);
            finish();
        }, SPLASH_DELAY);
    }
}
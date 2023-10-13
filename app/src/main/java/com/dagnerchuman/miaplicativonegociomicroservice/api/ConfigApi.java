package com.dagnerchuman.miaplicativonegociomicroservice.api;

import android.content.Context;
import android.content.SharedPreferences;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigApi {
    // 10.0.2.2
    private static final String BASE_URL = "http://10.0.2.2:5555";
    private static final String SHARED_PREFERENCES_NAME = "UserData";
    private static final String KEY_AUTH_TOKEN = "userToken";

    private static ApiService apiService;
    private static ApiServiceNegocio apiServiceNegocio;
    private static ApiServiceProductos apiServiceProducto;
    private static ApiServiceCompras apiServiceCompra;

    public static ApiService getInstance(Context context) {
        // Obtain the token from SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(KEY_AUTH_TOKEN, "");

        if (apiService == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", authToken); // Add your authorization header here
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build()) // Set the custom OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    public static ApiServiceNegocio getInstanceNegocio(Context context) {
        // Obtain the token from SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(KEY_AUTH_TOKEN, "");

        if (apiServiceNegocio == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", authToken); // Add your authorization header here
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build()) // Set the custom OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiServiceNegocio = retrofit.create(ApiServiceNegocio.class);
        }
        return apiServiceNegocio;
    }

    public static ApiServiceProductos getInstanceProducto(Context context) {
        // Obtain the token from SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(KEY_AUTH_TOKEN, "");

        if (apiServiceProducto == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", authToken); // Add your authorization header here
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build()) // Set the custom OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiServiceProducto = retrofit.create(ApiServiceProductos.class);
        }
        return apiServiceProducto;
    }

    public static ApiServiceCompras getInstanceCompra(Context context) {
        // Obtain the token from SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(KEY_AUTH_TOKEN, "");

        if (apiServiceCompra == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", authToken); // Add your authorization header here
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build()) // Set the custom OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiServiceCompra = retrofit.create(ApiServiceCompras.class);
        }
        return apiServiceCompra;
    }
}

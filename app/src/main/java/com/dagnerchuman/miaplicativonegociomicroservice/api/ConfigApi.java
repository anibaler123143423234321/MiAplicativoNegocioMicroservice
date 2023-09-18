package com.dagnerchuman.miaplicativonegociomicroservice.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigApi {
    private static final String BASE_URL = "http://10.0.2.2:5555";

    private static ApiService apiService;
    private static ApiServiceNegocio apiServiceNegocio;

    private static ApiServiceProductos apiServiceProducto;

    public static ApiService getInstance() {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    public static ApiServiceNegocio getInstanceNegocio() {
        if (apiServiceNegocio == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiServiceNegocio = retrofit.create(ApiServiceNegocio.class);
        }
        return apiServiceNegocio;
    }

    public static ApiServiceProductos getInstanceProducto() {
        if (apiServiceProducto == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiServiceProducto = retrofit.create(ApiServiceProductos.class);
        }
        return apiServiceProducto;
    }

}

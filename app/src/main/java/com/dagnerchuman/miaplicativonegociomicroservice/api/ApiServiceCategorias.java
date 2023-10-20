package com.dagnerchuman.miaplicativonegociomicroservice.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.dagnerchuman.miaplicativonegociomicroservice.entity.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiServiceCategorias {
    String PREFERENCES_NAME = "UserPreferences";
    public static String getUserToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("userToken", null); // "userToken" es la clave bajo la cual se almacena el token
    }
    String baseUser = "api/categoria";

    @POST(baseUser)
    Call<Categoria> saveCategoria(@Body Categoria categoria);

    @GET(baseUser)
    Call<List<Categoria>> getAllCategorias(@Header("Authorization") String token);

    @GET(baseUser+"/{categoriaId}")
    Call<Categoria> getCategoriaById(@Path("categoriaId") Long categoriaId);

    @PUT(baseUser + "/{categoriaId}")
    Call<Categoria> updateCategoria(@Path("categoriaId") Long categoriaId, @Body Categoria categoria);

    @DELETE(baseUser + "/{categoriaId}")
    Call<Void> deleteCategoria(@Path("categoriaId") Long categoriaId);
}

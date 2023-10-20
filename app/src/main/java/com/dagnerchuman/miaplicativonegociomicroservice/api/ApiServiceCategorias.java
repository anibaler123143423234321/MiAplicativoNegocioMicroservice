package com.dagnerchuman.miaplicativonegociomicroservice.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.dagnerchuman.miaplicativonegociomicroservice.entity.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiServiceCategorias {
    String baseUser = "gateway/categoria";

    @Headers("Authorization: Bearer")
    @POST(baseUser)
    Call<Categoria> saveCategoria(@Body Categoria categoria);

    @Headers("Authorization: Bearer")
    @GET(baseUser)
    Call<List<Categoria>> getAllCategorias();

    @Headers("Authorization: Bearer")
    @GET(baseUser+"/{categoriaId}")
    Call<Categoria> getCategoriaById(@Path("categoriaId") Long categoriaId);

    @Headers("Authorization: Bearer")
    @PUT(baseUser + "/{categoriaId}")
    Call<Categoria> updateCategoria(@Path("categoriaId") Long categoriaId, @Body Categoria categoria);

    @Headers("Authorization: Bearer")
    @DELETE(baseUser + "/{categoriaId}")
    Call<Void> deleteCategoria(@Path("categoriaId") Long categoriaId);
}

package com.dagnerchuman.miaplicativonegociomicroservice.api;

import com.dagnerchuman.miaplicativonegociomicroservice.entity.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiServiceCategorias {

    String baseUser = "api/categoria";

    @POST(baseUser)
    Call<Categoria> saveCategoria(@Body Categoria categoria);

    @GET(baseUser)
    Call<List<Categoria>> getAllCategorias();

    @GET(baseUser+"/{categoriaId}")
    Call<Categoria> getCategoriaById(@Path("categoriaId") Long categoriaId);

    @PUT(baseUser + "/{categoriaId}")
    Call<Categoria> updateCategoria(@Path("categoriaId") Long categoriaId, @Body Categoria categoria);

    @DELETE(baseUser + "/{categoriaId}")
    Call<Void> deleteCategoria(@Path("categoriaId") Long categoriaId);
}

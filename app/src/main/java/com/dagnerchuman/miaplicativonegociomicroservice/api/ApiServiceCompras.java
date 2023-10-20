package com.dagnerchuman.miaplicativonegociomicroservice.api;

import com.dagnerchuman.miaplicativonegociomicroservice.entity.Compra;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiServiceCompras {

    String baseCompra = "gateway/compra";

    @Headers("Authorization: Bearer")
    @POST(baseCompra)
    Call<Compra> saveCompra(@Body Compra compra);

    // En la interfaz ApiServiceCompras
    @Headers("Authorization: Bearer")
    @GET(baseCompra + "/{userId}") // Cambia la ruta para incluir el userId
    Call<List<Compra>> getAllComprasOfUser(@Path("userId") Long userId);

    @Headers("Authorization: Bearer")
    @PUT(baseCompra + "/{compraId}")
    Call<Compra> updateCompra(@Path("compraId") Long compraId, @Body Compra compra);

    @Headers("Authorization: Bearer")
    @GET(baseCompra + "/all")
    Call<List<Compra>> getAllCompras(@Header("Authorization") String token);
}

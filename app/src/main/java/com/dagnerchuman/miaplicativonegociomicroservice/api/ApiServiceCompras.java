package com.dagnerchuman.miaplicativonegociomicroservice.api;

import com.dagnerchuman.miaplicativonegociomicroservice.entity.Compra;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiServiceCompras {

    String baseCompra = "gateway/compra";

    @POST(baseCompra)
    Call<Compra> saveCompra(@Body Compra compra);

    @GET(baseCompra)
    Call<List<Compra>> getAllComprasOfUser();

    @PUT(baseCompra + "/{compraId}")
    Call<Compra> updateCompra(@Path("compraId") Long compraId, @Body Compra compra);

    @GET(baseCompra + "/all")
    Call<List<Compra>> getAllCompras();
}

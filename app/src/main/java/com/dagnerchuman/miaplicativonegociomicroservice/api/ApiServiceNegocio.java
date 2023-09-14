package com.dagnerchuman.miaplicativonegociomicroservice.api;

import com.dagnerchuman.miaplicativonegociomicroservice.entity.Negocio;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiServiceNegocio {
    String baseUser = "gateway/negocios";

    @POST(baseUser + "/")
    Call<Negocio> saveNegocio(@Body Negocio negocio);

    @GET(baseUser + "/{id}")
    Call<Negocio> getNegocioById(@Path("id") Long id);

    @GET(baseUser + "/")
    Call<List<Negocio>> getAllNegocios();

    @PUT(baseUser + "/{id}")
    Call<Negocio> updateNegocio(@Path("id") Long id, @Body Negocio negocio);

    @DELETE(baseUser + "/{id}")
    Call<Void> deleteNegocio(@Path("id") Long id);
}

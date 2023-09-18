package com.dagnerchuman.miaplicativonegociomicroservice.api;

import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServiceProductos {

    String baseUser = "gateway/producto";

    @POST(baseUser)
    Call<Producto> saveProducto(@Body Producto producto);

    @DELETE(baseUser + "/{productoId}")
    Call<Void> deleteProducto(@Path("productoId") Long productoId);

    @GET(baseUser)
    Call<List<Producto>> getAllProductos();

    @GET(baseUser + "{productoId}")
    Call<Producto> getProductoById(@Path("productoId") Long productoId);

    @DELETE(baseUser + "/eliminar-todos")
    Call<Void> deleteAllProductos();

    // Nuevo endpoint para obtener productos siguientes
    @GET(baseUser + "/siguientes")
    Call<List<Producto>> getSiguientesProductos(@Query("posicion") int posicion, @Query("cantidad") int cantidad);
}

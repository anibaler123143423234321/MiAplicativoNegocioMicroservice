package com.dagnerchuman.miaplicativonegociomicroservice.api;

import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServiceProductos {

    String baseUser = "gateway/producto";

    @Headers("Authorization: Bearer")

    @POST(baseUser)
    Call<Producto> saveProducto(@Body Producto producto);

    @Headers("Authorization: Bearer")
    @DELETE(baseUser + "/{productoId}")
    Call<Void> deleteProducto(@Path("productoId") Long productoId);

    @GET(baseUser)
    Call<List<Producto>> getAllProductos();

    @Headers("Authorization: Bearer")
    @GET(baseUser + "/{productoId}") // Corrección aqu// í
    Call<Producto> getProductoById(@Path("productoId") Long productoId);

    @Headers("Authorization: Bearer")
    @DELETE(baseUser + "/eliminar-todos")
    Call<Void> deleteAllProductos();

    // Nuevo endpoint para obtener productos siguientes
    @GET(baseUser + "/siguientes")
    Call<List<Producto>> getSiguientesProductos(@Query("posicion") int posicion, @Query("cantidad") int cantidad);

    @Headers("Authorization: Bearer")
    @PUT(baseUser + "/{productoId}")
    Call<Producto> actualizarProducto(@Path("productoId") Long productoId, @Body Producto nuevoProducto);

}

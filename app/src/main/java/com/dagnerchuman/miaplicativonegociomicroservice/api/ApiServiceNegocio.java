        package com.dagnerchuman.miaplicativonegociomicroservice.api;

        import com.dagnerchuman.miaplicativonegociomicroservice.entity.Negocio;

        import retrofit2.Call;
        import retrofit2.http.*;

        import java.util.List;

        public interface ApiServiceNegocio {
            String baseUser = "gateway/negocios";

            // Agrega un encabezado de autorización Bearer a todas las solicitudes
            @Headers("Authorization: Bearer")
            @POST(baseUser + "/")
            Call<Negocio> saveNegocio(@Body Negocio negocio);

            @Headers("Authorization: Bearer")
            @GET(baseUser + "/{id}")
            Call<Negocio> getNegocioById(@Path("id") Long id);


            @GET(baseUser + "/")
            Call<List<Negocio>> getAllNegocios();

            @Headers("Authorization: Bearer")
            @PUT(baseUser + "/{id}")
            Call<Negocio> updateNegocio(@Path("id") Long id, @Body Negocio negocio);

            @Headers("Authorization: Bearer")
            @DELETE(baseUser + "/{id}")
            Call<Void> deleteNegocio(@Path("id") Long id);
        }

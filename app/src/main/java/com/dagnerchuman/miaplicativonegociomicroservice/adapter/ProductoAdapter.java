package com.dagnerchuman.miaplicativonegociomicroservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.activity.ComprarActivity;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private Context context;
    private List<Producto> productosList;

    public ProductoAdapter(Context context, List<Producto> productosList) {
        this.context = context;
        this.productosList = productosList;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productosList.get(position);

        // Configura los datos del producto en las vistas del ViewHolder
        holder.txtNombre.setText(producto.getNombre());
        holder.txtPrecio.setText("$" + producto.getPrecio());

        // Carga la imagen desde Firebase Storage utilizando Glide
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Almacenar en caché
                .placeholder(R.drawable.ic_documento) // Imagen por defecto
                .error(R.drawable.ic_documento); // Imagen por defecto en caso de error

        Glide.with(context)
                .load(producto.getPicture()) // URL de la imagen en Firebase Storage
                .apply(requestOptions)
                .into(holder.imgProducto);

        // Configura el clic en el botón Comprar
        holder.btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Producto productoSeleccionado = productosList.get(adapterPosition);
                    long productoId = productoSeleccionado.getId();
                    String nombreProducto = productoSeleccionado.getNombre();
                    double precioProducto = productoSeleccionado.getPrecio();
                    String imagenProducto = productoSeleccionado.getPicture();

                    // Obtiene el ID del usuario desde SharedPreferences
                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    long userId = sharedPreferences.getLong("userId", -1);

                    if (userId != -1) {
                        // Crea un Intent para iniciar ComprarActivity y pasa los datos del producto y el userId como extras
                        Intent intent = new Intent(context, ComprarActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("productoId", productoId);
                        intent.putExtra("nombreProducto", nombreProducto);
                        intent.putExtra("precioProducto", precioProducto);
                        intent.putExtra("imagenProducto", imagenProducto);
                        context.startActivity(intent);
                    } else {
                        // El ID del usuario no está disponible en SharedPreferences, maneja esto según tus necesidades
                        // Puede ser que el usuario no haya iniciado sesión correctamente
                        Toast.makeText(context, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productosList.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProducto;
        TextView txtNombre, txtPrecio;
        Button btnComprar;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProducto = itemView.findViewById(R.id.imgProducto);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            btnComprar = itemView.findViewById(R.id.btnComprar);
            // Incluye aquí otras vistas de los elementos del producto si es necesario
        }
    }
}

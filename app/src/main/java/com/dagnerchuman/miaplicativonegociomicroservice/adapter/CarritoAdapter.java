package com.dagnerchuman.miaplicativonegociomicroservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {
    private Context context;
    private List<Producto> productosEnCarrito;

    public CarritoAdapter(Context context, List<Producto> productosEnCarrito) {
        this.context = context;
        this.productosEnCarrito = productosEnCarrito;
    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carrito, parent, false);
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        Producto producto = productosEnCarrito.get(position);
        holder.txtNombreProducto.setText(producto.getNombre());
        holder.txtPrecioProducto.setText("$" + producto.getPrecio());
        Picasso.get().load(producto.getPicture()).into(holder.imgProducto);

        // Puedes cargar la imagen del producto en holder.imgProducto si lo deseas
    }

    @Override
    public int getItemCount() {
        return productosEnCarrito.size();
    }

    public class CarritoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgProducto;
        public TextView txtNombreProducto;
        public TextView txtPrecioProducto;

        public CarritoViewHolder(View itemView) {
            super(itemView);
            imgProducto = itemView.findViewById(R.id.imgProducto); // Asegúrate de agregar la ImageView correspondiente en tu diseño XML.
            txtNombreProducto = itemView.findViewById(R.id.txtNombreProducto);
            txtPrecioProducto = itemView.findViewById(R.id.txtPrecioProducto);
        }
    }
}

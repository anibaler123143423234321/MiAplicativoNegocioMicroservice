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
import com.dagnerchuman.miaplicativonegociomicroservice.activity.EntradaActivity;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {
    private Context context;
    private List<Producto> productList;
    private List<Producto> filteredList;
    private List<Producto> carrito;  // Lista de productos en el carrito
    private EntradaActivity entradaActivity; // Añade esta variable



    public ProductoAdapter(Context context, List<Producto> productList) {
        this.context = context;
        this.productList = productList;
        this.filteredList = new ArrayList<>(productList);
        this.carrito = new ArrayList<>();
        this.entradaActivity = entradaActivity; // Inicializa la referencia al EntradaActivity

    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = filteredList.get(position); // Cambiar productList a filteredList

        holder.txtNombre.setText(producto.getNombre());
        holder.txtCategoria.setText(String.valueOf(producto.getCategoriaId()));
        holder.txtPrecio.setText("$" + producto.getPrecio());
        holder.txtStock.setText(String.valueOf(producto.getStock()));


        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_documento)
                .error(R.drawable.ic_documento);

        Glide.with(context)
                .load(producto.getPicture())
                .apply(requestOptions)
                .into(holder.imgProducto);

        holder.btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCompra(producto);
            }
        });

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToCart(producto);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size(); // Cambiar a filteredList
    }

    public void filterProductos(String query) {
        query = query.toLowerCase();
        filteredList.clear();

        if (query.isEmpty()) {
            filteredList.addAll(productList); // Restablecer la lista completa cuando la consulta está vacía
        } else {
            for (Producto producto : productList) {
                String nombre = producto.getNombre().toLowerCase();
                if (nombre.contains(query)) {
                    filteredList.add(producto);
                }
            }
        }

        notifyDataSetChanged();
    }



    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProducto;
        TextView txtNombre, txtCategoria, txtPrecio, txtStock;
        Button btnComprar, btnAddToCart;


        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProducto = itemView.findViewById(R.id.imgProducto);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            txtStock = itemView.findViewById(R.id.txtStock);
            btnComprar = itemView.findViewById(R.id.btnComprar);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }

    private void handleCompra(Producto producto) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", -1);

        if (userId != -1) {
            Intent intent = new Intent(context, ComprarActivity.class);
            intent.putExtra("userId", userId);
            intent.putExtra("productoId", producto.getId());
            intent.putExtra("nombreProducto", producto.getNombre());
            intent.putExtra("categoriaProducto", producto.getCategoriaId());
            intent.putExtra("precioProducto", producto.getPrecio());
            intent.putExtra("imagenProducto", producto.getPicture());
            intent.putExtra("stockProducto", producto.getStock());
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
        }
    }

    private void addProductToCart(Producto producto) {
        // Comprobar si el producto ya está en el carrito
        if (entradaActivity.addToCart(producto)) {
            Toast.makeText(context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Este producto ya está en el carrito", Toast.LENGTH_SHORT).show();
        }

        // Actualizar el ícono del carrito en la actividad de EntradaActivity
        entradaActivity.updateCarritoIcon(productList.size()); // Usar el tamaño actual del carrito
    }


}

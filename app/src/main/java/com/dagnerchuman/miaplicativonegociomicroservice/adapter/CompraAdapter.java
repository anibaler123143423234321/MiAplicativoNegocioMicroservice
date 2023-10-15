package com.dagnerchuman.miaplicativonegociomicroservice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Compra;
import java.util.ArrayList;
import java.util.List;

public class CompraAdapter extends RecyclerView.Adapter<CompraAdapter.CompraViewHolder> {

    public interface BoletaDownloadListener {
        void onBoletaDownload(Compra compra);
    }

    private List<Compra> comprasList;
    private BoletaDownloadListener boletaDownloadListener;

    public CompraAdapter(BoletaDownloadListener listener) {
        this.boletaDownloadListener = listener;
        this.comprasList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CompraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compra, parent, false);
        return new CompraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraViewHolder holder, int position) {
        Compra compra = comprasList.get(position);

        holder.tvTituloCompra.setText(compra.getTitulo());
        holder.tvFechaCompra.setText(compra.getFechaCompra());
        holder.tvPrecioCompra.setText(String.valueOf(compra.getPrecioCompra()));
        holder.tvCodigoCompra.setText(compra.getCodigo());
        holder.tvEstadoCompra.setText(compra.getEstadoCompra());
        holder.tvTipoEnvio.setText(compra.getTipoEnvio());
        holder.tvTipoDePago.setText(compra.getTipoDePago());
        holder.btnDescargarBoleta.setTag(compra);

        // Agrega más configuraciones para otros atributos de Compra aquí

        // Configura el OnClickListener para el botón de descarga
        holder.btnDescargarBoleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (boletaDownloadListener != null) {
                    boletaDownloadListener.onBoletaDownload(compra);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return comprasList.size();
    }

    // Método personalizado para actualizar los datos del adaptador
    public void setCompras(List<Compra> newCompras) {
        comprasList.clear();
        comprasList.addAll(newCompras);
        notifyDataSetChanged();
    }

    static class CompraViewHolder extends RecyclerView.ViewHolder {
        TextView tvTituloCompra;
        TextView tvFechaCompra;
        TextView tvPrecioCompra;
        TextView tvCodigoCompra;
        TextView tvEstadoCompra;
        TextView tvTipoEnvio;
        TextView tvTipoDePago;
        Button btnDescargarBoleta; // Agregar el botón

        CompraViewHolder(View itemView) {
            super(itemView);
            tvTituloCompra = itemView.findViewById(R.id.tvTituloCompra);
            tvFechaCompra = itemView.findViewById(R.id.tvFechaCompra);
            tvPrecioCompra = itemView.findViewById(R.id.tvPrecioCompra);
            tvCodigoCompra = itemView.findViewById(R.id.tvCodigoCompra);
            tvEstadoCompra = itemView.findViewById(R.id.tvEstadoCompra);
            tvTipoEnvio = itemView.findViewById(R.id.tvTipoEnvio);
            tvTipoDePago = itemView.findViewById(R.id.tvTipoDePago);
            btnDescargarBoleta = itemView.findViewById(R.id.btnDescargarBoleta); // Inicializa el botón
        }
    }
}

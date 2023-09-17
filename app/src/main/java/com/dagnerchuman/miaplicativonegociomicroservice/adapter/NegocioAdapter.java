package com.dagnerchuman.miaplicativonegociomicroservice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dagnerchuman.miaplicativonegociomicroservice.R;
import com.dagnerchuman.miaplicativonegociomicroservice.entity.Negocio;

import java.util.List;

public class NegocioAdapter extends RecyclerView.Adapter<NegocioAdapter.ViewHolder> {

    private List<Negocio> negocios;

    public NegocioAdapter(List<Negocio> negocios) {
        this.negocios = negocios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_negocio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Negocio negocio = negocios.get(position);
        holder.txtNombreNegocio.setText(negocio.getNombre());
        holder.txtDireccionNegocio.setText(negocio.getDireccion());

        // Puedes agregar más campos aquí para mostrar en el elemento de la lista
    }


    @Override
    public int getItemCount() {
        return negocios.size();
    }

    public void updateData(List<Negocio> newNegocios) {
        negocios.clear();
        negocios.addAll(newNegocios);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreNegocio;
        TextView txtDireccionNegocio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreNegocio = itemView.findViewById(R.id.txtNombreNegocio);
            txtDireccionNegocio = itemView.findViewById(R.id.txtDireccionNegocio);

        }
    }
}

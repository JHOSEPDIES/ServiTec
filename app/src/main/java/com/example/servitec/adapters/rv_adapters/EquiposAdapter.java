package com.example.servitec.adapters.rv_adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servitec.R;
import com.example.servitec.clases.Modelos.POJOEquipos;

import java.util.ArrayList;
import java.util.List;

public class EquiposAdapter extends RecyclerView.Adapter<EquiposAdapter.EquiposViewHolder>{

    public List<POJOEquipos> equipos;

    public EquiposAdapter (List<POJOEquipos> equipo)
    {
        this.equipos = equipo;
    }

    @NonNull
    @Override
    public EquiposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvl_equipos,parent,false);
        return new EquiposViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EquiposViewHolder holder, int position) {

        final POJOEquipos equipo = equipos.get(position);

        holder.nombre.setText(equipo.getNombre());
        holder.dependencia.setText(equipo.getDependencia());
        holder.modelo.setText(equipo.getModelo());
        holder.marca.setText(equipo.getMarca());
        holder.ns.setText(equipo.getSn());
        holder.color.setText(equipo.getColor());
        holder.estado.setText(equipo.getEstado());
        holder.notas.setText(equipo.getNotas());
    }

    @Override
    public int getItemCount() {
        return equipos.size();
    }

    public static class EquiposViewHolder extends RecyclerView.ViewHolder
    {
        private TextView nombre,dependencia,modelo,marca,ns,color,estado,notas;

        public EquiposViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.tv_nombre);
            dependencia = itemView.findViewById(R.id.tv_dependencia);
            modelo = itemView.findViewById(R.id.tv_modelo);
            marca = itemView.findViewById(R.id.tv_marca);
            ns = itemView.findViewById(R.id.tv_ns);
            color = itemView.findViewById(R.id.tv_color);
            estado = itemView.findViewById(R.id.tv_estado);
            notas = itemView.findViewById(R.id.tv_notas);

        }
    }

    public void actualizar(List<POJOEquipos> ListaEquipo)
    {
        if (ListaEquipo == null) return;

        this.equipos = ListaEquipo;

        notifyDataSetChanged();
    }

}

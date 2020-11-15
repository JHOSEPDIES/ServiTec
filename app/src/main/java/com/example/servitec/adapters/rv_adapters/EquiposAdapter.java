package com.example.servitec.adapters.rv_adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servitec.R;
import com.example.servitec.clases.Modelos.POJOEquipos;

import java.util.List;
import java.util.Random;

public class EquiposAdapter extends RecyclerView.Adapter<EquiposAdapter.EquiposViewHolder>{

    public List<POJOEquipos> equipos;

    public EquiposAdapter (List<POJOEquipos> equipo)
    {
        this.equipos = equipo;
    }

    @NonNull
    @Override
    public EquiposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_equipos_edit,parent,false);
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

        boolean isExpanded = equipos.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POJOEquipos equipo = equipos.get(position);
                equipo.setExpanded(!equipo.isExpanded());
                notifyDataSetChanged();
            }
        });

        holder.card.setBackgroundColor(getRandomColorCode());
    }
    public int getRandomColorCode(){

        Random random = new Random();

        return Color.argb(255, random.nextInt(256), random.nextInt(256),random.nextInt(256));

    }

    @Override
    public int getItemCount() {
        return equipos.size();
    }

    public static class EquiposViewHolder extends RecyclerView.ViewHolder
    {
        private TextView nombre,dependencia,modelo,marca,ns,color,estado,notas;
        ConstraintLayout expandableLayout;
        CardView card;
        public EquiposViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.tv_nombre);
            dependencia = itemView.findViewById(R.id.tv_dependencia_serv);
            modelo = itemView.findViewById(R.id.tv_modelo_serv);
            marca = itemView.findViewById(R.id.tv_marca_serv);
            ns = itemView.findViewById(R.id.tv_ns_serv);
            color = itemView.findViewById(R.id.tv_color_serv);
            estado = itemView.findViewById(R.id.tv_edo_serv);
            notas = itemView.findViewById(R.id.tv_servicio_ser);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
             card = itemView.findViewById(R.id.card_equipos);


        }
    }
}

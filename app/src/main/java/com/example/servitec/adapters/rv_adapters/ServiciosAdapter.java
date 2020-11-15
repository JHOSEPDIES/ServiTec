package com.example.servitec.adapters.rv_adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servitec.R;
import com.example.servitec.clases.Modelos.POJOServiciosGet;

import java.util.List;
import java.util.Random;

public class ServiciosAdapter extends RecyclerView.Adapter<ServiciosAdapter.ServiciosViewHolder> {

    public List<POJOServiciosGet> servicios;

    public ServiciosAdapter (List<POJOServiciosGet> servicio)
    {
        this.servicios = servicio;
    }

    @NonNull
    @Override
    public ServiciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_servicios_edit,parent,false);
        return new ServiciosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiciosViewHolder holder, int position) {

        final POJOServiciosGet servicio = servicios.get(position);

        holder.nombre.setText(servicio.getNombre());
        holder.serv.setText(servicio.getServicio());
        holder.fecha.setText(servicio.getFecha_registro());
        holder.card.setBackgroundColor(getRandomColorCode());

        boolean isExpanded = servicios.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicio.setExpanded(!servicio.isExpanded());
                notifyDataSetChanged();
            }
        });

    }

    public int getRandomColorCode(){

        Random random = new Random();

        return Color.argb(255, random.nextInt(256), random.nextInt(256),random.nextInt(256));

    }

    @Override
    public int getItemCount() {
        return servicios.size();
    }

    public static class ServiciosViewHolder extends RecyclerView.ViewHolder{

        private TextView nombre,serv,fecha;
        LinearLayout expandableLayout;
        CardView card;
        public ServiciosViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.tv_nom_serv);
            serv = itemView.findViewById(R.id.tv_serv_ser);
            fecha = itemView.findViewById(R.id.tv_fecha_serv);
            expandableLayout = itemView.findViewById(R.id.expandableLayout_servicios);
            card = itemView.findViewById(R.id.card_servicios);
        }
    }
}

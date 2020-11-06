package com.example.servitec.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servitec.R;
import com.example.servitec.clases.POJOServiciosGet;

import java.util.ArrayList;

public class ServiciosAdapter extends RecyclerView.Adapter<ServiciosAdapter.ServiciosViewHolder> {

    public ArrayList<POJOServiciosGet> servicios;

    public ServiciosAdapter (ArrayList<POJOServiciosGet> servicio)
    {
        this.servicios = servicio;
    }

    @NonNull
    @Override
    public ServiciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvl_servicios,parent,false);
        return new ServiciosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiciosViewHolder holder, int position) {

        final POJOServiciosGet servicio = servicios.get(position);

        holder.nombre.setText(servicio.getNombre());
        holder.dependencia.setText(servicio.getDependencia());
        holder.modelo.setText(servicio.getModelo());
        holder.marca.setText(servicio.getMarca());
        holder.ns.setText(servicio.getSn());
        holder.color.setText(servicio.getColor());
        holder.serv.setText(servicio.getServicio());
    }

    @Override
    public int getItemCount() {
        return servicios.size();
    }

    public static class ServiciosViewHolder extends RecyclerView.ViewHolder{

        private TextView nombre,dependencia,modelo,marca,ns,color,estado,serv;

        public ServiciosViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.tv_nombre_serv);
            dependencia = itemView.findViewById(R.id.tv_dependencia_serv);
            modelo = itemView.findViewById(R.id.tv_modelo_serv);
            marca = itemView.findViewById(R.id.tv_marca_serv);
            ns = itemView.findViewById(R.id.tv_ns_serv);
            color = itemView.findViewById(R.id.tv_color_serv);
            estado = itemView.findViewById(R.id.tv_estado_serv);
            serv = itemView.findViewById(R.id.tv_servicio_serv);
        }
    }

    public void actualizar(ArrayList<POJOServiciosGet> ListaServicio)
    {
        if (ListaServicio == null) return;

        this.servicios = ListaServicio;

        notifyDataSetChanged();
    }
}

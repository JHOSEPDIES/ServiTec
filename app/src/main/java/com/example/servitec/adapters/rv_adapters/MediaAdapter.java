package com.example.servitec.adapters.rv_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servitec.R;
import com.example.servitec.clases.Modelos.POJOMedia;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {

    List<POJOMedia> medias ;
    private Context context;

    public MediaAdapter (List<POJOMedia> media)
    {
        this.medias = media;
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_media,parent,false);
        context = parent.getContext();
        return new MediaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {

        if (medias.get(position).getLocation() == null)
        {

            Picasso
                    .get()
                    .load("https://tecdies.com.mx/TECDIES_ANDROID/images/tpojbxmh74.jpg")
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .fit()
                    .into(holder.link);

        }
        else
        {
            Picasso
                    .get()
                    .load("https://tecdies.com.mx/TECDIES_ANDROID/"+medias.get(position).getLocation())
                    .error(R.drawable.ic_launcher_foreground)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .fit()
                    .into(holder.link);
        }

    }

    @Override
    public int getItemCount() {
        return medias.size();
    }
    

    public static class MediaViewHolder extends RecyclerView.ViewHolder {

        private ImageView link;
        private TextView tvlink;

        public MediaViewHolder(@NonNull View itemView) {
            super(itemView);
            link = itemView.findViewById(R.id.img_media);
        }
    }

    public void actualizar(ArrayList<POJOMedia> ListaMedia)
    {
        if (ListaMedia == null) return;

        this.medias = ListaMedia;

        notifyDataSetChanged();
    }
}

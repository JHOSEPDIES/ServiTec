package com.example.servitec.ui.media.List;

import android.icu.lang.UCharacter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servitec.R;
import com.example.servitec.adapters.rv_adapters.MediaAdapter;
import com.example.servitec.clases.Modelos.POJOMedia;

import java.util.List;

public class View_Galery_Fragment extends Fragment implements List_Galery_View{

    List<POJOMedia> media;

    private RecyclerView listamedia;

    private MediaAdapter adapter;

    private SwipeRefreshLayout swp;

    private ProgressBar pb;

    List_Galery_Presentador presentador;

    public View_Galery_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_view__galery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listamedia = requireActivity().findViewById(R.id.rv_media);

        pb = requireActivity().findViewById(R.id.pb_media);

        swp = requireActivity().findViewById(R.id.swp_media);

        StaggeredGridLayoutManager layoutManager=new  StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        listamedia.setLayoutManager(layoutManager);

        presentador = new List_Galery_Presentador(this);

        presentador.callMedia();

        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presentador.callMedia();
            }
        });

    }

    private void showToast(String mensaje)
    {
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast, requireActivity().findViewById(R.id.layout_toast));

        TextView txt_mensaje = layout.findViewById(R.id.txt_mensaje);

        txt_mensaje.setText(mensaje);

        Toast toast = new Toast(requireActivity());

        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,0,0);

        toast.setDuration(Toast.LENGTH_SHORT);

        toast.setView(layout);

        toast.show();
    }

    @Override
    public void showBar()
    {
        swp.setRefreshing(true);
    }

    @Override
    public void hideBar()
    {
        swp.setRefreshing(false);
    }

    @Override
    public void onErrorLoad(String Message)
    {
        showToast(Message);
    }

    @Override
    public void onGetResult(List<POJOMedia> medias)
    {
        adapter = new MediaAdapter(medias);
        adapter.notifyDataSetChanged();
        listamedia.setAdapter(adapter);
        media = medias;
    }
}
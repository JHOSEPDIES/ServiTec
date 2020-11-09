package com.example.servitec.ui.media;

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
import com.example.servitec.clases.POJO.POJOMedia;
import com.example.servitec.clases.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class View_Galery_Fragment extends Fragment {

    ArrayList<POJOMedia> media = new ArrayList<>();

    private RecyclerView listamedia;

    private MediaAdapter adapter;

    private SwipeRefreshLayout swp;

    private ProgressBar pb;

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


        StaggeredGridLayoutManager sgl = new StaggeredGridLayoutManager (2,StaggeredGridLayoutManager.VERTICAL);
        listamedia.setLayoutManager(sgl);

        inicializarAdaptador();

        callMedia();

        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callMedia();
                swp.setRefreshing(false);
            }
        });

    }

    private void callMedia() {

        pb.setVisibility(View.VISIBLE);

        Call<List<POJOMedia>> response = RetroClient.getInstance().getApi().getmedia();

        response.enqueue(new Callback<List<POJOMedia>>() {
            @Override
            public void onResponse(Call<List<POJOMedia>> call, Response<List<POJOMedia>> response) {

                if (response.isSuccessful() && response.code() == 200)
                {

                    media = new ArrayList<>();

                    for (POJOMedia elemento : response.body())
                    {
                        media.add(new POJOMedia(""+elemento.getLocation()));
                    }
                    showList(media);
                    pb.setVisibility(View.INVISIBLE);
                }
                else
                {
                    showToast("Error en el EndPoint");
                    pb.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<POJOMedia>> call, Throwable t) {
                    showToast(t.toString());
                    pb.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void inicializarAdaptador()
    {
        adapter = new MediaAdapter(media);
        listamedia.setAdapter(adapter);
    }

    public void showList(ArrayList<POJOMedia> lista)
    {
        adapter.actualizar(lista);
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

}
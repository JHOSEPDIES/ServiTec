package com.example.servitec.ui.equipos.List;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.servitec.adapters.rv_adapters.EquiposAdapter;
import com.example.servitec.clases.Modelos.POJOEquipos;
import com.example.servitec.API.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Lista_Equipos_Fragment extends Fragment implements List_Equipos_View{

    List<POJOEquipos> equipo;

    private RecyclerView listaequipos;

    private EquiposAdapter adapter;

    private SwipeRefreshLayout swp;

    List_Equipos_Presenter presenter;

    public Lista_Equipos_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) 
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista__equipos_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //swipe layout
        swp = requireActivity().findViewById(R.id.swp_datos);

        //asignado el widget
        listaequipos = requireActivity().findViewById(R.id.rv_equipos);

        //asignando el tipo de layout
        listaequipos.setLayoutManager(new LinearLayoutManager(requireActivity()));

        //asignamos la vista, en este caso esta
        presenter = new List_Equipos_Presenter(this);

        //obteniendo los datos
        presenter.getData();


        //buscando más datos en la opción de refrescar
        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() 
        {
            @Override
            public void onRefresh() 
            {
                //obtenemos los datos
                presenter.getData();
            }
        });
    }

    //se muestra el icono de load en el swipe down
    @Override
    public void showBar()
    {
        swp.setRefreshing(true);
    }

    //oculta el icono de load
    @Override
    public void hideBar()
    {
        swp.setRefreshing(false);
    }

    //muestra el toast personalizado
    @Override
    public void onErrorLoad(String Message)
    {
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast, requireActivity().findViewById(R.id.layout_toast));

        TextView txt_mensaje = layout.findViewById(R.id.txt_mensaje);

        txt_mensaje.setText(Message);

        Toast toast = new Toast(requireActivity());

        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,0,0);

        toast.setDuration(Toast.LENGTH_SHORT);

        toast.setView(layout);

        toast.show();
    }

    //obtiene los resultados de la BD y los asigna al RV
    @Override
    public void onGetResult(List<POJOEquipos> equipos)
    {
        //le asigna el objeto equipos al adaptador y realiza toda la asignación
        adapter = new EquiposAdapter(equipos);

        //Notifica a los observadores adjuntos que los datos subyacentes han sido cambiados 
        //y que cualquier vista que refleja el conjunto de datos debe actualizarse.
        adapter.notifyDataSetChanged();
        

        //asigna al RV el adaptador que contiene la lista de equipos
        listaequipos.setAdapter(adapter);
        
        //asigna los resultados de la BD a la lista del POJO
        equipo = equipos;
    }
}


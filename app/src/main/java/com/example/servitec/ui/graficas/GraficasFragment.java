package com.example.servitec.ui.graficas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.servitec.R;
import com.example.servitec.clases.Modelos.POJOGraficas;
import com.example.servitec.API.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GraficasFragment extends Fragment {

    private ProgressBar pb_graficas;

    public GraficasFragment() {
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
        return inflater.inflate(R.layout.fragment_graficas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pb_graficas = requireActivity().findViewById(R.id.pb_graficas);

        getChart();
    }

    private void getChart()
    {
        Call<List<POJOGraficas>> call = RetroClient.getInstance().getApi().getData_Services_Chart();

        call.enqueue(new Callback<List<POJOGraficas>>() {
            @Override
            public void onResponse(Call<List<POJOGraficas>> call, Response<List<POJOGraficas>> response) {
                if (response.isSuccessful() && response.code() == 200)
                {
                    iniciarGraficas(response);
                }
                else
                {
                    Toast.makeText(requireActivity(), "No Existen Datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<POJOGraficas>> call, Throwable t) {

            }
        });
    }

    private void iniciarGraficas(Response<List<POJOGraficas>> response)
    {
        pb_graficas.setVisibility(View.VISIBLE);

        AnyChartView anyChartView = requireActivity().findViewById(R.id.graficas_generales);
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();

        for (POJOGraficas elemento : response.body())
        {
            data.add(new ValueDataEntry("Servicios", elemento.getServicios()));
        }

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        cartesian.title("Gráficos de Actividades");
        cartesian.yScale().minimum(0d);
        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.xAxis(0).title("Actividades");
        cartesian.yAxis(0).title("Cantidad");
        anyChartView.setChart(cartesian);

        pb_graficas.setVisibility(View.INVISIBLE);

    }
}
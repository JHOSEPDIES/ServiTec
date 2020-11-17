package com.example.servitec.ui.graficas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
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


public class GraficasFragment extends Fragment implements Graficas_View{

    private ProgressBar pb_graficas;

    Graficas_Presenter presenter;

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

        presenter = new Graficas_Presenter(this);

        presenter.getChart();
    }


    private void iniciarGraficas(int valores)
    {

        AnyChartView anyChartView = requireActivity().findViewById(R.id.graficas_generales);
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();

        data.add(new ValueDataEntry("Servicios", valores));

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


    }

    @Override
    public void showBar()
    {
        pb_graficas.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBar()
    {
        pb_graficas.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onErrorLoad(String message)
    {
        showToast(message);
    }

    @Override
    public void onFail(String message)
    {
        showToast(message);
    }

    @Override
    public void getValues(int total)
    {
        iniciarGraficas(total);
    }

    private void showToast(String message)
    {
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast, requireActivity().findViewById(R.id.layout_toast));

        TextView txt_mensaje = layout.findViewById(R.id.txt_mensaje);

        txt_mensaje.setText(message);

        Toast toast = new Toast(requireActivity());

        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,0,0);

        toast.setDuration(Toast.LENGTH_SHORT);

        toast.setView(layout);

        toast.show();
    }
}
package sv.ues.fia.eisi.agendaestudiantil.ui.grafico;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.Event;
import sv.ues.fia.eisi.agendaestudiantil.clases.PrefCofig;
import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;


public class GraficoFragment extends Fragment {

    private LineChart grafica;
    private ArrayList<Entry> entradas = new ArrayList<>();
    public GraficoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_grafico, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Event.eventsList = (ArrayList<Event>) PrefCofig.readListFromPref(view.getContext());
        if (Event.eventsList == null)
            Event.eventsList = new ArrayList<>();

        grafica = view.findViewById(R.id.line_chart);


        LocalDate dia = CalendarUtils.selectedDate.now();
        String [] xAxisLabels = new String[] {CalendarUtils.formattedDia(dia), CalendarUtils.formattedDia(dia.plusDays(1)), CalendarUtils.formattedDia(dia.plusDays(2)), CalendarUtils.formattedDia(dia.plusDays(3)), CalendarUtils.formattedDia(dia.plusDays(4)), CalendarUtils.formattedDia(dia.plusDays(5)), CalendarUtils.formattedDia(dia.plusDays(6))};
        grafica.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));

        entradas.add(new Entry(0f,contarEventos(dia)));
        entradas.add(new Entry(1f,contarEventos(dia.plusDays(1))));
        entradas.add(new Entry(2f,contarEventos(dia.plusDays(2))));
        entradas.add(new Entry(3f,contarEventos(dia.plusDays(3))));
        entradas.add(new Entry(4f,contarEventos(dia.plusDays(4))));
        entradas.add(new Entry(5f,contarEventos(dia.plusDays(5))));
        entradas.add(new Entry(6f,contarEventos(dia.plusDays(6))));

        LineDataSet datos = new LineDataSet(entradas, "GRAFICA DE EVENTOS DIARIOS");
        LineData data = new LineData(datos);
        datos.setLineWidth(4);
        datos.setDrawCircleHole(false);
        datos.setCircleColor(R.color.purple_700);
        datos.setCircleRadius(5);
        datos.setValueTextSize(10);
        datos.setLineWidth(5f);
        datos.setColor(R.color.teal_700);
        datos.setHighLightColor(Color.RED);
        datos.setDrawValues(true);
        //to make the smooth line as the graph is adrapt change so smooth curve
        datos.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //to enable the cubic density : if 1 then it will be sharp curve
        datos.setCubicIntensity(0.1f);

        //to fill the below of smooth line in graph
        datos.setDrawFilled(true);
        datos.setFillColor(R.color.teal_200);
        //set the transparency
        datos.setFillAlpha(80);

        grafica.setData(data);
        grafica.setTouchEnabled(false);
        grafica.setDragEnabled(true);
        grafica.setScaleEnabled(true);
        grafica.setPinchZoom(false);
        grafica.setDrawGridBackground(false);
        grafica.setMaxHighlightDistance(200);
        grafica.setViewPortOffsets(0, 0, 0, 0);
        grafica.getDescription().setEnabled(false);

        grafica.invalidate();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int contarEventos(LocalDate date){
        ArrayList<Event> eventos = new ArrayList<>();
        int contador = 0;
        if (Event.eventsList != null){
            for (Event event : Event.eventsList){
                LocalDate fecha = LocalDate.parse(event.getFecha());
                if (fecha.equals(date))
                    contador++;
            }
        }
        return contador;
    }
}
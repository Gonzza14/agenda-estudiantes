package sv.ues.fia.eisi.agendaestudiantil.ui.grafico;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
/*import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;*/

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.Event;
import sv.ues.fia.eisi.agendaestudiantil.clases.PrefCofig;
import sv.ues.fia.eisi.agendaestudiantil.clases.TemplatePDF;
import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;


@RequiresApi(api = Build.VERSION_CODES.O)
public class GraficoFragment extends Fragment {

    private LineChart grafica;
    private ArrayList<Entry> entradas = new ArrayList<>();
    private FloatingActionButton btnGenerar;
    private LocalDate dia;

    private String[] header;
    String NOMBRE_DIRECTORIO = "AgendaEstudiantilPDF";
    String NOMBRE_DOCUMENTO = "EventosDiarios"+CalendarUtils.formattedDia(CalendarUtils.selectedDate.now())+".pdf";

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


        dia = CalendarUtils.selectedDate.now();
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


        // Permisos
        /*if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                    1000);
        }*/

        TemplatePDF templatePDF = new TemplatePDF(getContext());
        templatePDF.openDocument();
        templatePDF.addMetaData("Eventos diarios", "Eventos diario en los proximos 7 dias", "AgendaEstudiantil");
        templatePDF.addTitles("Agenda estudiantil", "Eventos diarios en los proximos 7 dias", CalendarUtils.formattedDate(CalendarUtils.selectedDate.now()));
        templatePDF.createTable(xAxisLabels, getEventos());
        templatePDF.closeDocument();
        btnGenerar = view.findViewById(R.id.btnGenerarPDF);
        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //crearPDF();}
                Bundle bundle = new Bundle();
                bundle.putString("PATH",templatePDF.pdfFile.getAbsolutePath());
                Navigation.findNavController(view).navigate(R.id.nav_ver_pdf, bundle);
            }
        });
    }

    private ArrayList<String[]>getEventos(){
        ArrayList<String[]>rows = new ArrayList<>();
        String [][] filas;
        filas = new String[10][7];
        for (int c = 0; c<7;c++){
            ArrayList<Event> listaEventosPorDias = Event.eventos(dia.plusDays(c));
            for (int f= 0; f<listaEventosPorDias.size();f++){
                filas[f][c] = listaEventosPorDias.get(f).getNombre()+" " + listaEventosPorDias.get(f).getDescripcion();
            }
        }
        for (int i = 0; i < filas.length; i++ ){
            rows.add(filas[i]);
        }
        return rows;
    }

    /*private void crearPDF() {
        Document documento = new Document();

        try {
            File file = crearFichero(NOMBRE_DOCUMENTO);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());

            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            documento.open();
            documento.add(new Paragraph("TABLA DE EVENTOS DIARIOS"));
            documento.add(new Paragraph("Desde" + CalendarUtils.formattedDate(dia) + "Hasta" + CalendarUtils.formattedDate(dia.plusDays(6))));

            PdfPTable tabla = new PdfPTable(7);
            for (int i = 0; i < tabla.getNumberOfColumns(); i++){
                tabla.addCell(CalendarUtils.formattedDia(dia.plusDays(i)));
                for (int j = 0; j < Event.eventos(dia.plusDays(j)).size(); j++){
                    for (Event event : Event.eventos(dia.plusDays(j))){
                        tabla.addCell(event.getNombre() + event.getHora());
                    }
                }
            }
            documento.add(tabla);
        }catch (DocumentException e) {
        }catch (IOException e) {
        }finally {
            documento.close();
        }
    }

    public File crearFichero(String nombreFichero){
        File ruta = getRuta();

        File fichero = null;
        if (ruta != null){
            fichero = new File(ruta, nombreFichero);
        }

        return fichero;
    }

    public File getRuta(){
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), NOMBRE_DIRECTORIO);
            if (ruta != null){
                if (!ruta.mkdirs()){
                    if (!ruta.exists()){
                        return null;
                    }
                }
            }
        }
        return ruta;
    }*/

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
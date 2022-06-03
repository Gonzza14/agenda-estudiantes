package sv.ues.fia.eisi.agendaestudiantil.ui.calendario;

import static sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils.daysInMonthArray;
import static sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils.daysInWeekArray;
import static sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils.monthYearFromDate;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.list.DialogListExtKt;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.CalendarAdapter;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.EventAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.clases.Event;
import sv.ues.fia.eisi.agendaestudiantil.clases.PrefCofig;
import sv.ues.fia.eisi.agendaestudiantil.ui.examen.ExamenViewModel;


public class WeekViewFragment extends Fragment implements CalendarAdapter.OnItemListener{

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();
        Event.eventsList = (ArrayList<Event>) PrefCofig.readListFromPref(getContext());
        if (Event.eventsList == null)
            Event.eventsList = new ArrayList<>();
        if (Event.eventsList != null){
            for (Event event : Event.eventsList){
                LocalDate fecha = LocalDate.parse(event.getFecha());
                if (fecha.equals(date))
                    events.add(event);
            }
        }
        return events;
    }

    private BD helper;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    Button btnSemanaAtras, btnSemanaAdelante;
    FloatingActionButton btnNuevoEvento;
    /*@RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<ExamenViewModel> examenesPorFecha(LocalDate date){
        ArrayList<ExamenViewModel> examenes = new ArrayList<>();

        for (ExamenViewModel examen : helper.mostrarExamenes()) {
            LocalDate fecha = LocalDate.parse(examen.getFechaExamen());
            if (fecha.equals(date))
                examenes.add(examen);
        }
        return examenes;
    }*/

    public WeekViewFragment() {
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
        return inflater.inflate(R.layout.fragment_week_view, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidgets();
        setWeekView();

        helper = new BD(view.getContext());


        btnSemanaAtras = view.findViewById(R.id.btnAtras);
        btnSemanaAdelante = view.findViewById(R.id.btnAdelante);
        btnNuevoEvento = view.findViewById(R.id.btnNuevoEvento);

        btnSemanaAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
                setWeekView();
            }
        });

        btnSemanaAdelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
                setWeekView();
            }
        });

        btnNuevoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPlainListDialog();
            }
        });


        /*String tiempo, hora = "", amPm, minuto;
        String [] parts, parts2;
        int convertirHora;
        ArrayList<ExamenViewModel> examenes = helper.mostrarExamenes();

        for (int i = 0; i<examenes.size(); i++){
            Event eventoExamen = new Event();
            eventoExamen.setIdEvento(examenes.get(i).getIdExamen());
            eventoExamen.setNombre(examenes.get(i).getNombreExamen());
            eventoExamen.setFecha(LocalDate.parse(examenes.get(i).getFechaExamen()));

            tiempo = examenes.get(i).getHoraExamen();
            parts = tiempo.split(" ");
            parts2 = tiempo.split(":");
            minuto = parts2[1];
            amPm = parts[1];

            convertirHora = Integer.parseInt(parts2[0]);

            if (amPm.equals("PM") && convertirHora != 12){
                convertirHora += 12;
                hora=convertirHora+":"+minuto.substring(0,2)+":00";
            }
            if (amPm.equals("AM") && convertirHora == 12) {
                convertirHora += 12;
                hora="0"+convertirHora+":"+minuto.substring(0,2)+":00";
            }
            if (amPm.equals("AM"))
                if (convertirHora < 10)
                    hora="0"+parts[0]+":00";
                else
                    hora=parts[0]+":00";

            eventoExamen.setHora(LocalTime.parse(hora));
            eventoExamen.setDescripcion(examenes.get(i).getDescripcionExamen());
            Event.eventsList.add(eventoExamen);
        }*/
    }

    private void showPlainListDialog() {
        String[] args = {"Agregar tarea", "Agregar examen", "Agregar recordatorio"};
        List<String> list = Arrays.asList(args);

        MaterialDialog dialog = new MaterialDialog(getContext(), MaterialDialog.getDEFAULT_BEHAVIOR());
        dialog.title(null, "Seleccione el evento");
        DialogListExtKt.listItems(dialog, null, list, null, false, (materialDialog, integer, s) -> {
            if (integer == 0)
                Navigation.findNavController(getView()).navigate(R.id.nav_agregar_tarea);
            if (integer == 1)
                Navigation.findNavController(getView()).navigate(R.id.nav_agregar_examen);
            if (integer == 2)
                Navigation.findNavController(getView()).navigate(R.id.nav_agregar_recordatorio);
            dialog.dismiss();
            return null;
        });
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdapter();
    }


    private void initWidgets() {
        calendarRecyclerView = getView().findViewById(R.id.calendarRecyclerView);
        monthYearText = getView().findViewById(R.id.mesAñoCalendario);
        eventListView = getView().findViewById(R.id.eventListView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        setEventAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setEventAdapter() {
        ArrayList<Event> dailyEvents = eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getActivity().getApplicationContext(),dailyEvents);
        eventListView.setAdapter(eventAdapter);

        eventAdapter.ordenarPorHora();
    }
}
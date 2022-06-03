package sv.ues.fia.eisi.agendaestudiantil.ui.calendario;

import static sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils.daysInMonthArray;
import static sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils.monthYearFromDate;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.list.DialogListExtKt;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.CalendarAdapter;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.EventAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.Event;
import sv.ues.fia.eisi.agendaestudiantil.clases.PrefCofig;

public class CalendarioFragment extends Fragment implements CalendarAdapter.OnItemListener {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();
        if (Event.eventsList == null)
            Event.eventsList = new ArrayList<>();
        Event.eventsList = (ArrayList<Event>) PrefCofig.readListFromPref(getContext());
        if (Event.eventsList != null) {
            for (Event event : Event.eventsList) {
                LocalDate fecha = LocalDate.parse(event.getFecha());
                if (fecha.equals(date))
                    events.add(event);
            }
        }
        return events;
    }

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private Button btnAtras, btnAdelante, btnSemanal;
    private FloatingActionButton btnNuevoEvento;

    public static CalendarioFragment newInstance() {
        return new CalendarioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendario, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidgets();
        if (CalendarUtils.selectedDate == null)
            CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        btnAtras = view.findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        btnAdelante = view.findViewById(R.id.btnAdelante);
        btnAdelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
                setMonthView();
            }
        });

        btnSemanal = view.findViewById(R.id.btnSemanal);
        btnSemanal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_calendario_semanal);
            }
        });

        btnNuevoEvento = view.findViewById(R.id.btnNuevoEvento);
        btnNuevoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPlainListDialog();
            }
        });

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
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
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
        if (date != null) {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
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
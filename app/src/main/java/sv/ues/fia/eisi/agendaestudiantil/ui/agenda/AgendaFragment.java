package sv.ues.fia.eisi.agendaestudiantil.ui.agenda;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.list.DialogListExtKt;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.EventAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.clases.Event;
import sv.ues.fia.eisi.agendaestudiantil.clases.PrefCofig;
import sv.ues.fia.eisi.agendaestudiantil.databinding.FragmentAgendaBinding;
import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;

public class AgendaFragment extends Fragment {

    private ListView eventListView;
    private FloatingActionButton btnAgregar;
    private BD helper;
    private ArrayList<Event> listaArrayEvento;
    private EventAdapter eventAdapter;
    private EventAdapter adapter;
    private Spinner spOrdenar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_agenda, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Event.eventsList != null)
            Event.eventsList = (ArrayList<Event>) PrefCofig.readListFromPref(getContext());
        eventListView = view.findViewById(R.id.eventListView);

        listaArrayEvento = new ArrayList<>();

        spOrdenar = view.findViewById(R.id.spOrdenar);
        spOrdenar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spOrdenar.getSelectedItemPosition() == 0){
                    listaArrayEvento = Event.eventos(CalendarUtils.selectedDate.now().minusDays(1));
                    eventAdapter = new EventAdapter(getActivity().getApplicationContext(),listaArrayEvento);
                    eventListView.setAdapter(eventAdapter);
                    eventAdapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 1){
                    listaArrayEvento = Event.eventos(CalendarUtils.selectedDate.now());
                    eventAdapter = new EventAdapter(getActivity().getApplicationContext(),listaArrayEvento);
                    eventListView.setAdapter(eventAdapter);
                    eventAdapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 2){
                    listaArrayEvento = Event.eventos(CalendarUtils.selectedDate.now().plusDays(1));
                    eventAdapter = new EventAdapter(getActivity().getApplicationContext(),listaArrayEvento);
                    eventListView.setAdapter(eventAdapter);
                    eventAdapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 3){
                    listaArrayEvento = Event.eventosSieteDias(CalendarUtils.selectedDate.now());
                    eventAdapter = new EventAdapter(getActivity().getApplicationContext(),listaArrayEvento);
                    eventListView.setAdapter(eventAdapter);
                    eventAdapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 4){
                    listaArrayEvento = Event.eventosPorMes(CalendarUtils.selectedDate.now());
                    eventAdapter = new EventAdapter(getActivity().getApplicationContext(),listaArrayEvento);
                    eventListView.setAdapter(eventAdapter);
                    eventAdapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 5){
                    listaArrayEvento = Event.eventosSiguienteMes(CalendarUtils.selectedDate.now());
                    eventAdapter = new EventAdapter(getActivity().getApplicationContext(),listaArrayEvento);
                    eventListView.setAdapter(eventAdapter);
                    eventAdapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 6){
                    listaArrayEvento = Event.eventsList;
                    if (listaArrayEvento != null){
                        eventAdapter = new EventAdapter(getActivity().getApplicationContext(),listaArrayEvento);
                        eventListView.setAdapter(eventAdapter);
                        eventAdapter.ordenarPorFecha();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spOrdenar.setSelection(1);
        btnAgregar = view.findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
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
}
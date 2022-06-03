package sv.ues.fia.eisi.agendaestudiantil.ui.examen;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaExamenAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.clases.Event;
import sv.ues.fia.eisi.agendaestudiantil.clases.PrefCofig;
import sv.ues.fia.eisi.agendaestudiantil.databinding.FragmentExamenBinding;
import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;

public class ExamenFragment extends Fragment {

    private RecyclerView listaExamenes;
    private FloatingActionButton btnAgregarExamen;
    private BD helper;
    private ArrayList<ExamenViewModel> listaArrayExamenes;
    private ListaExamenAdapter adapter;
    private Spinner spOrdenar;

    public static ExamenFragment newInstance() { return new ExamenFragment();}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_examen, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Event.eventsList = (ArrayList<Event>) PrefCofig.readListFromPref(view.getContext());
        if (Event.eventsList == null)
            Event.eventsList = new ArrayList<>();
        listaExamenes = view.findViewById(R.id.listaExamenes);
        listaExamenes.setLayoutManager(new LinearLayoutManager(view.getContext()));

        helper = new BD(view.getContext());

        listaArrayExamenes = new ArrayList<>();

        spOrdenar = view.findViewById(R.id.spOrdenar);
        spOrdenar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spOrdenar.getSelectedItemPosition() == 0) {
                    listaArrayExamenes = helper.mostrarExamenesAyer();
                    adapter = new ListaExamenAdapter(listaArrayExamenes);
                    listaExamenes.setAdapter(adapter);
                    adapter.ordenarPorFecha();

                }
                else if (spOrdenar.getSelectedItemPosition() == 1){
                    listaArrayExamenes = helper.mostrarExamenesHoy();
                    adapter = new ListaExamenAdapter(listaArrayExamenes);
                    listaExamenes.setAdapter(adapter);
                    adapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 2){
                    listaArrayExamenes = helper.mostrarExamenesMañana();
                    adapter = new ListaExamenAdapter(listaArrayExamenes);
                    listaExamenes.setAdapter(adapter);
                    adapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 3){
                    listaArrayExamenes = helper.mostrarExamenesSieteDias();
                    adapter = new ListaExamenAdapter(listaArrayExamenes);
                    listaExamenes.setAdapter(adapter);
                    adapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 4){
                    String mesCadena = "";
                    int mes =  CalendarUtils.selectedDate.now().getMonthValue();
                    if (mes < 10) {
                        mesCadena = "0" + mes;
                    }else{
                        mesCadena = String.valueOf(mes);
                    }
                    listaArrayExamenes = helper.mostrarExamenesEsteMes(mesCadena);
                    adapter = new ListaExamenAdapter(listaArrayExamenes);
                    listaExamenes.setAdapter(adapter);
                    adapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 5){
                    String mesCadena = "";
                    int mes = CalendarUtils.selectedDate.now().getMonthValue();
                    mes++;
                    if (mes > 12){
                        mes = 1;
                    }else if (mes < 10){
                        mesCadena = "0" + mes;
                    }else{
                        mesCadena = String.valueOf(mes);
                    }
                    listaArrayExamenes = helper.mostrarExamenesSiguienteMes(mesCadena);
                    adapter = new ListaExamenAdapter(listaArrayExamenes);
                    listaExamenes.setAdapter(adapter);
                    adapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 6){
                    listaArrayExamenes = helper.mostrarExamenes();
                    adapter = new ListaExamenAdapter(listaArrayExamenes);
                    listaExamenes.setAdapter(adapter);
                    adapter.ordenarPorFecha();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spOrdenar.setSelection(1);
        btnAgregarExamen = view.findViewById(R.id.btnAgregarExamen);

        btnAgregarExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_agregar_examen);
            }
        });

    }

}
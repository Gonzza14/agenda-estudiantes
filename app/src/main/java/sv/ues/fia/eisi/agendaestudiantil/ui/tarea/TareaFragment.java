package sv.ues.fia.eisi.agendaestudiantil.ui.tarea;

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
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaTareaAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.databinding.FragmentTareaBinding;
import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;

public class TareaFragment extends Fragment {

    private RecyclerView listaTareas;
    private FloatingActionButton btnAgregarTarea;
    private BD helper;
    private ListaTareaAdapter adapter;
    private ArrayList<TareaViewModel> listaArrayTarea;
    private Spinner spOrdenar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tarea, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaTareas = view.findViewById(R.id.listaTareas);
        listaTareas.setLayoutManager(new LinearLayoutManager(view.getContext()));

        helper = new BD(view.getContext());

        listaArrayTarea = new ArrayList<>();

        spOrdenar = view.findViewById(R.id.spOrdenar);
        spOrdenar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spOrdenar.getSelectedItemPosition() == 0) {
                    listaArrayTarea = helper.mostrarTareasAyer();
                    adapter = new ListaTareaAdapter(listaArrayTarea);
                    listaTareas.setAdapter(adapter);
                    adapter.ordenarPorFecha();

                }
                else if (spOrdenar.getSelectedItemPosition() == 1){
                    listaArrayTarea = helper.mostrarTareasHoy();
                    adapter = new ListaTareaAdapter(listaArrayTarea);
                    listaTareas.setAdapter(adapter);
                    adapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 2){
                    listaArrayTarea = helper.mostrarTareasMañana();
                    adapter = new ListaTareaAdapter(listaArrayTarea);
                    listaTareas.setAdapter(adapter);
                    adapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 3){
                    listaArrayTarea = helper.mostrarTareasSieteDias();
                    adapter = new ListaTareaAdapter(listaArrayTarea);
                    listaTareas.setAdapter(adapter);
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
                    listaArrayTarea = helper.mostrarTareasEsteMes(mesCadena);
                    adapter = new ListaTareaAdapter(listaArrayTarea);
                    listaTareas.setAdapter(adapter);
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
                    listaArrayTarea = helper.mostrarTareasSiguienteMes(mesCadena);
                    adapter = new ListaTareaAdapter(listaArrayTarea);
                    listaTareas.setAdapter(adapter);
                    adapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 6){
                    listaArrayTarea = helper.mostrarTareas();
                    adapter = new ListaTareaAdapter(listaArrayTarea);
                    listaTareas.setAdapter(adapter);
                    adapter.ordenarPorFecha();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spOrdenar.setSelection(1);
        btnAgregarTarea = view.findViewById(R.id.btnAgregarTarea);

        btnAgregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_agregar_tarea);
            }
        });

    }
}
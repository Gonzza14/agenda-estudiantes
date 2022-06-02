package sv.ues.fia.eisi.agendaestudiantil.ui.recordatorio;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaExamenAdapter;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaRecordatorioAdapter;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaTareaAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;

public class RecordatorioFragment extends Fragment {

    private RecyclerView listaRecordatorio;
    private FloatingActionButton btnAgregarRecordatorio;
    private ListaRecordatorioAdapter adapter;
    private BD helper;
    private ArrayList<RecordatorioViewModel> listaArrayRecordatorio;
    private Spinner spOrdenar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recordatorio, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaRecordatorio = view.findViewById(R.id.listaRecordatorios);
        listaRecordatorio.setLayoutManager(new LinearLayoutManager(view.getContext()));

        helper = new BD(view.getContext());

        listaArrayRecordatorio = new ArrayList<>();

        spOrdenar = view.findViewById(R.id.spOrdenar);
        spOrdenar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spOrdenar.getSelectedItemPosition() == 0) {
                    listaArrayRecordatorio = helper.mostrarRecordatoriosAyer();
                    adapter = new ListaRecordatorioAdapter(listaArrayRecordatorio);
                    listaRecordatorio.setAdapter(adapter);
                    adapter.ordenarPorFecha();

                }
                else if (spOrdenar.getSelectedItemPosition() == 1){
                    listaArrayRecordatorio = helper.mostrarRecordatoriosHoy();
                    adapter = new ListaRecordatorioAdapter(listaArrayRecordatorio);
                    listaRecordatorio.setAdapter(adapter);
                    adapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 2){
                    listaArrayRecordatorio = helper.mostrarRecordatoriosMañana();
                    adapter = new ListaRecordatorioAdapter(listaArrayRecordatorio);
                    listaRecordatorio.setAdapter(adapter);
                    adapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 3){
                    listaArrayRecordatorio = helper.mostrarRecordatoriosSieteDias();
                    adapter = new ListaRecordatorioAdapter(listaArrayRecordatorio);
                    listaRecordatorio.setAdapter(adapter);
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
                    listaArrayRecordatorio = helper.mostrarRecordatoriosEsteMes(mesCadena);
                    adapter = new ListaRecordatorioAdapter(listaArrayRecordatorio);
                    listaRecordatorio.setAdapter(adapter);
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
                    listaArrayRecordatorio = helper.mostrarRecordatoriosSiguienteMes(mesCadena);
                    adapter = new ListaRecordatorioAdapter(listaArrayRecordatorio);
                    listaRecordatorio.setAdapter(adapter);
                    adapter.ordenarPorFecha();
                }
                else if (spOrdenar.getSelectedItemPosition() == 6){
                    listaArrayRecordatorio = helper.mostrarRecordatorios();
                    adapter = new ListaRecordatorioAdapter(listaArrayRecordatorio);
                    listaRecordatorio.setAdapter(adapter);
                    adapter.ordenarPorFecha();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spOrdenar.setSelection(1);
        btnAgregarRecordatorio = view.findViewById(R.id.btnAgregarRecordatorio);
        btnAgregarRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_agregar_recordatorio);
            }
        });


    }
}
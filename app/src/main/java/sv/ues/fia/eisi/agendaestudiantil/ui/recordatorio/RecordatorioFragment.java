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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaExamenAdapter;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaRecordatorioAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;

public class RecordatorioFragment extends Fragment {

    private RecyclerView listaRecordatorio;
    private FloatingActionButton btnAgregarRecordatorio;
    private BD helper;
    private ArrayList<RecordatorioViewModel> listaArrayRecordatorio;

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
        listaArrayRecordatorio = helper.mostrarRecordatorios();

        ListaRecordatorioAdapter adapter = new ListaRecordatorioAdapter(listaArrayRecordatorio);
        listaRecordatorio.setAdapter(adapter);

        btnAgregarRecordatorio = view.findViewById(R.id.btnAgregarRecordatorio);

        adapter.ordenarPorFecha();
        btnAgregarRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_agregar_recordatorio);
            }
        });


    }
}
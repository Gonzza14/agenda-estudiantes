package sv.ues.fia.eisi.agendaestudiantil.ui.examen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaMateriaAdapter;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaMateriasNotasAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.ui.materia.MateriaViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.periodo.PeriodoViewModel;

public class VerNotasFragment extends Fragment {

    private RecyclerView listaNotas, listaMateriasNotas;
    private BD helper;
    private ArrayList<MateriaViewModel> listaArrayMaterias;
    private ListaMateriasNotasAdapter adapter;
    private ArrayAdapter<PeriodoViewModel> adaptadorPeriodos;
    private Spinner spOrdenar;
    private int idPeriodoMateria = 0;
    public VerNotasFragment() {
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
        return inflater.inflate(R.layout.fragment_ver_notas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaNotas = view.findViewById(R.id.listaNotas);
        listaNotas.setLayoutManager(new LinearLayoutManager(view.getContext()));

        helper = new BD(view.getContext());

        listaArrayMaterias = new ArrayList<>();

        spOrdenar = view.findViewById(R.id.spOrdenar);

        ArrayList<PeriodoViewModel> periodos = helper.mostrarPeriodos();
        adaptadorPeriodos = new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, periodos);
        spOrdenar.setAdapter(adaptadorPeriodos);
        spOrdenar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idPeriodoMateria = ((PeriodoViewModel) adapterView.getSelectedItem()).getIdPeriodo();
                listaArrayMaterias = helper.mostrarMateriasPorPeriodo(idPeriodoMateria);
                adapter = new ListaMateriasNotasAdapter(listaArrayMaterias);
                listaNotas.setAdapter(adapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
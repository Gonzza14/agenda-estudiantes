package sv.ues.fia.eisi.agendaestudiantil.ui.periodo;

import androidx.lifecycle.ViewModelProvider;

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

import java.text.ParseException;
import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaPeriodoAdapter;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaProfesoresAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;

public class PeriodoFragment extends Fragment {

    private PeriodoViewModel mViewModel;
    private RecyclerView listaPeriodos;
    private FloatingActionButton btnAgregarPeriodo;
    private BD helper;
    ArrayList<PeriodoViewModel> listaArrayPeriodos;


    public static PeriodoFragment newInstance() {
        return new PeriodoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_periodo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaPeriodos = view.findViewById(R.id.listaPeriodos);
        listaPeriodos.setLayoutManager(new LinearLayoutManager(view.getContext()));

        helper = new BD(view.getContext());

        listaArrayPeriodos = new ArrayList<>();

        ListaPeriodoAdapter adapter = new ListaPeriodoAdapter(helper.mostrarPeriodos());
        listaPeriodos.setAdapter(adapter);

        btnAgregarPeriodo = view.findViewById(R.id.btnAgregarPeriodo);
        btnAgregarPeriodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_agregar_periodo);
            }
        });
    }
}
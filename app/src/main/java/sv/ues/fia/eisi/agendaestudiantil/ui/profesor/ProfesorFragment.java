package sv.ues.fia.eisi.agendaestudiantil.ui.profesor;

import androidx.fragment.app.FragmentTransaction;
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

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaProfesoresAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.databinding.FragmentProfesorBinding;

public class ProfesorFragment extends Fragment {

    private RecyclerView listaProfesores;
    private FloatingActionButton btnAgregarProfesor;
    private BD helper;
    ArrayList<ProfesorViewModel> listaArrayProfesores;
    private ProfesorViewModel mViewModel;

    public static ProfesorFragment newInstance() {
        return new ProfesorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profesor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaProfesores = view.findViewById(R.id.listaProfesores);
        listaProfesores.setLayoutManager(new LinearLayoutManager(view.getContext()));

        helper = new BD(view.getContext());

        listaArrayProfesores = new ArrayList<>();

        ListaProfesoresAdapter adapter = new ListaProfesoresAdapter(helper.mostrarProfesores());
        listaProfesores.setAdapter(adapter);

        btnAgregarProfesor = view.findViewById(R.id.btnAgregarProfesor);

        btnAgregarProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_agregar_profesor);
            }
        });
    }
}
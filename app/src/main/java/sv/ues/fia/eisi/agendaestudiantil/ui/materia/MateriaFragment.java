package sv.ues.fia.eisi.agendaestudiantil.ui.materia;

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
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaMateriaAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;

public class MateriaFragment extends Fragment {

    private RecyclerView listaMaterias;
    private FloatingActionButton btnAgregarMateria;
    private BD helper;
    private ArrayList<MateriaViewModel> listaArrayMaterias;
    private ListaMateriaAdapter adapter;
    private int id = 0, position = 0;

    public static MateriaFragment newInstance() {
        return new MateriaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_materia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaMaterias = view.findViewById(R.id.listaMaterias);
        listaMaterias.setLayoutManager(new LinearLayoutManager(view.getContext()));

        helper = new BD(view.getContext());

        listaArrayMaterias = new ArrayList<>();
        listaArrayMaterias = helper.mostrarMaterias();

        adapter = new ListaMateriaAdapter(listaArrayMaterias);
        listaMaterias.setAdapter(adapter);

        btnAgregarMateria = view.findViewById(R.id.btnAgregarMateria);
        btnAgregarMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_agregar_materia);
            }
        });

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = helper.mostrarMaterias().get(listaMaterias.getChildAdapterPosition(view)).getIdMateria();
                position = listaMaterias.getChildAdapterPosition(view);
                showCustomViewDialog(view,id,position);
            }
        });
    }

    private void showCustomViewDialog(View view, int id, int position){

    }
}
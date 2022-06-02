package sv.ues.fia.eisi.agendaestudiantil.ui.tarea;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaTareaAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.databinding.FragmentTareaBinding;

public class TareaFragment extends Fragment {

    private RecyclerView listaTareas;
    private FloatingActionButton btnAgregarTarea;
    private BD helper;
    private ArrayList<TareaViewModel> listaArrayTarea;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tarea, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaTareas = view.findViewById(R.id.listaTareas);
        listaTareas.setLayoutManager(new LinearLayoutManager(view.getContext()));

        helper = new BD(view.getContext());

        listaArrayTarea = new ArrayList<>();
        listaArrayTarea = helper.mostrarTareas();

        ListaTareaAdapter adapter = new ListaTareaAdapter(listaArrayTarea);
        listaTareas.setAdapter(adapter);

        btnAgregarTarea = view.findViewById(R.id.btnAgregarTarea);

        btnAgregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_agregar_tarea);
            }
        });

    }
}
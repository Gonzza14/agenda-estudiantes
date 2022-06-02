package sv.ues.fia.eisi.agendaestudiantil.ui.examen;

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
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaExamenAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.databinding.FragmentExamenBinding;

public class ExamenFragment extends Fragment {

    private RecyclerView listaExamenes;
    private FloatingActionButton btnAgregarExamen;
    private BD helper;
    private ArrayList<ExamenViewModel> listaArrayExamenes;

    public static ExamenFragment newInstance() { return new ExamenFragment();}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_examen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaExamenes = view.findViewById(R.id.listaExamenes);
        listaExamenes.setLayoutManager(new LinearLayoutManager(view.getContext()));

        helper = new BD(view.getContext());

        listaArrayExamenes = new ArrayList<>();
        listaArrayExamenes = helper.mostrarExamenes();

        ListaExamenAdapter adapter = new ListaExamenAdapter(listaArrayExamenes);
        listaExamenes.setAdapter(adapter);

        btnAgregarExamen = view.findViewById(R.id.btnAgregarExamen);

        btnAgregarExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_agregar_examen);
            }
        });

    }

}
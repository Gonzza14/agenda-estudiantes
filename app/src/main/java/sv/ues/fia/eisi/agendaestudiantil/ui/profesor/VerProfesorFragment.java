package sv.ues.fia.eisi.agendaestudiantil.ui.profesor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.databinding.FragmentAgendaBinding;
import sv.ues.fia.eisi.agendaestudiantil.databinding.FragmentVerProfesorBinding;

public class VerProfesorFragment extends Fragment {


    private FragmentVerProfesorBinding binding;
    private TextView lblNombreView, lblTelefonoView, lblCorreoView;
    private BD helper;
    private ProfesorViewModel profesor;
    private int id = 0;

    public VerProfesorFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentVerProfesorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        helper = new BD(view.getContext());
        lblNombreView = view.findViewById(R.id.lblNombreView);
        lblTelefonoView = view.findViewById(R.id.lblTelefonoView);
        lblCorreoView = view.findViewById(R.id.lblCorreoView);
        FloatingActionButton fab = binding.btnEditarProfesor;
        FloatingActionButton btnEliminarProfesor = binding.btnEliminarProfesor;
        id = (int) getArguments().getInt("ID");
        profesor = helper.verProfesor(id);

        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;

        if (profesor != null){
            toolBarLayout.setTitle(profesor.getNombreProfesor() + " " + profesor.getApellidoProfesor());
            lblTelefonoView.setText(profesor.getTelefonoProfesor());
            lblCorreoView.setText(profesor.getCorreoProfesor());
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("ID", id);
                Navigation.findNavController(view).navigate(R.id.nav_editar_profesor, bundle);
            }
        });

        btnEliminarProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje;
                ProfesorViewModel profesor = new ProfesorViewModel();
                profesor.setIdProfesor(id);

                helper.abrir();
                mensaje = helper.eliminar(profesor);
                helper.cerrar();

                Toast.makeText(view.getContext(),mensaje, Toast.LENGTH_SHORT).show();

                Navigation.findNavController(view).popBackStack();
            }
        });
    }
}
package sv.ues.fia.eisi.agendaestudiantil.ui.profesor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
            lblTelefonoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phoneNo = lblTelefonoView.getText().toString();
                    if(!TextUtils.isEmpty(phoneNo)) {
                        String dial = "tel:" + phoneNo;
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                    }else
                        Toast.makeText(getContext(), "No hay numero de telefono", Toast.LENGTH_SHORT).show();

                }
            });
            lblCorreoView.setText(profesor.getCorreoProfesor());
            lblCorreoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String correo = lblCorreoView.getText().toString();
                    if(!TextUtils.isEmpty(correo)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("CORREO", profesor.getCorreoProfesor());
                        Navigation.findNavController(view).navigate(R.id.nav_enviar_correo, bundle);
                    }else
                        Toast.makeText(getContext(), "No hay correo de usuario", Toast.LENGTH_SHORT).show();
                }
            });
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
package sv.ues.fia.eisi.agendaestudiantil.ui.examen;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;

public class NotaExamenFragment extends Fragment {

    private EditText txtCalificacion, txtPorcentaje, txtDescripcion;
    private FloatingActionButton btnGuardarNota;
    private NotaExamenViewModel notaExamen;
    private BD helper;
    private int idExamen = 0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        notaExamen = new ViewModelProvider(this).get(NotaExamenViewModel.class);
        return inflater.inflate(R.layout.fragment_nota_examen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        helper = new BD(view.getContext());
        txtCalificacion = view.findViewById(R.id.txtCalificacion);
        txtPorcentaje = view.findViewById(R.id.txtPorcentaje);
        txtDescripcion = view.findViewById(R.id.txtDescripcion);

        idExamen = (int) getArguments().getInt("ID");

        notaExamen = helper.verNotaExamen(idExamen);

        if (notaExamen != null){
            txtCalificacion.setText(String.valueOf(notaExamen.getCalificacion()));
            txtPorcentaje.setText(String.valueOf(notaExamen.getPorcentaje()));
            txtDescripcion.setText(notaExamen.getDescripcionExamen());
        }

        btnGuardarNota = view.findViewById(R.id.btnGuardarNota);
        btnGuardarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notaExamen.setIdExamen(idExamen);
                notaExamen.setCalificacion(Integer.parseInt(txtCalificacion.getText().toString()));
                notaExamen.setPorcentaje(Integer.parseInt(txtPorcentaje.getText().toString()));
                notaExamen.setDescripcionExamen(txtDescripcion.getText().toString());

                helper.abrir();
                String estado = helper.actualizar(notaExamen);
                helper.cerrar();

                Toast.makeText(view.getContext(), estado, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
            }
        });


    }
}
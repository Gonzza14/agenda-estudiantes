package sv.ues.fia.eisi.agendaestudiantil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.agendaestudiantil.clases.Agenda;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;

public class CrearAgendaFragment extends Fragment {

    BD helper;
    View vista;
    EditText txtNombre;
    Button btnCrear;

    public CrearAgendaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_crear_agenda, container, false);
        helper = new BD(vista.getContext());
        txtNombre = (EditText) vista.findViewById(R.id.lblNombre);
        btnCrear = (Button) vista.findViewById(R.id.btnCrear);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreAgenda = txtNombre.getText().toString();
                String mensaje;
                Agenda agenda = new Agenda();
                agenda.setNombreAgenda(nombreAgenda);
                helper.abrir();
                mensaje = helper.insertar(agenda);
                helper.cerrar();
                Toast.makeText(getActivity().getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
            }
        });
        return vista;
    }
}
package sv.ues.fia.eisi.agendaestudiantil.ui.materia;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.ui.periodo.PeriodoViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.profesor.ProfesorViewModel;


public class AgregarMateriaFragment extends Fragment {

    private EditText txtNombreMateria, txtAulaMateria;
    private Spinner spPeriodoMateria, spProfesorMateria;
    private FloatingActionButton btnGuardarMateria;
    ArrayList<PeriodoViewModel> listaArrayMateria;
    private BD helper;
    private MateriaViewModel materia;
    private int idProfesor, idPeriodo;


    public AgregarMateriaFragment() {
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
        materia = new ViewModelProvider(this).get(MateriaViewModel.class);
        return inflater.inflate(R.layout.fragment_agregar_materia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helper = new BD(view.getContext());
        txtNombreMateria = view.findViewById(R.id.txtNombreMateria);
        txtAulaMateria = view.findViewById(R.id.txtAulaMateria);
        spProfesorMateria = view.findViewById(R.id.spProfesorMateria);
        spPeriodoMateria = view.findViewById(R.id.spPeriodoMateria);
        btnGuardarMateria = view.findViewById(R.id.btnGuardarMateria);

        ArrayList<ProfesorViewModel> profesores = helper.mostrarProfesores();
        ArrayAdapter<ProfesorViewModel> adaptadorProfesores = new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, profesores);
        spProfesorMateria.setAdapter(adaptadorProfesores);

        spProfesorMateria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idProfesor = ((ProfesorViewModel) adapterView.getSelectedItem()).getIdProfesor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<PeriodoViewModel> periodos = helper.mostrarPeriodos();
        ArrayAdapter<PeriodoViewModel> adaptadorPeriodos = new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, periodos);
        spPeriodoMateria.setAdapter(adaptadorPeriodos);

        spPeriodoMateria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idPeriodo = ((PeriodoViewModel) adapterView.getSelectedItem()).getIdPeriodo();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnGuardarMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje;
                String nombreMateria = txtNombreMateria.getText().toString();
                String aulaMateria = txtAulaMateria.getText().toString();

                materia.setNombreMateria(nombreMateria);
                materia.setAulaMateria(aulaMateria);
                materia.setIdProfesor(idProfesor);
                materia.setIdPeriodo(idPeriodo);

                helper.abrir();
                mensaje = helper.insertar(materia);
                helper.cerrar();

                Toast.makeText(view.getContext(), mensaje, Toast.LENGTH_SHORT).show();

                txtNombreMateria.setText("");
                txtAulaMateria.setText("");
            }
        });

    }
}
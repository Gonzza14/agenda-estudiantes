package sv.ues.fia.eisi.agendaestudiantil.ui.materia;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaMateriaAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.custom.EditarMateriaView;
import sv.ues.fia.eisi.agendaestudiantil.ui.periodo.PeriodoViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.profesor.ProfesorViewModel;

public class MateriaFragment extends Fragment {

    private String registroProfesor, registroPeriodo;
    private MateriaViewModel materia;
    private EditText txtNombreMateria, txtAulaMateria;
    private Spinner spProfesorMateria, spPeriodoMateria;
    private Button btnEditar, btnEliminar, btnCancelar;
    private RecyclerView listaMaterias;
    private FloatingActionButton btnAgregarMateria;
    private ArrayAdapter<ProfesorViewModel> adaptadorProfesores;
    private ArrayAdapter<PeriodoViewModel> adaptadorPeriodos;
    private BD helper;
    private ArrayList<MateriaViewModel> listaArrayMaterias;
    private ListaMateriaAdapter adapter;
    private int id = 0, position = 0, idProfesor = 0, idPeriodo = 0;;

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
        EditarMateriaView customView = new EditarMateriaView(view.getContext());

        txtNombreMateria = customView.findViewById(R.id.txtNombreMateriaView);
        txtAulaMateria = customView.findViewById(R.id.txtAulaMateriaView);
        spProfesorMateria = customView.findViewById(R.id.spProfesorMateriaView);

        ArrayList<ProfesorViewModel> profesores = helper.mostrarProfesores();
        adaptadorProfesores = new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, profesores);
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

        spPeriodoMateria = customView.findViewById(R.id.spPeriodoMateriaView);
        ArrayList<PeriodoViewModel> periodos = helper.mostrarPeriodos();
        adaptadorPeriodos = new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, periodos);
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

        btnEditar = customView.findViewById(R.id.btnEditarMateria);
        btnEliminar = customView.findViewById(R.id.btnEliminarMateria);
        btnCancelar = customView.findViewById(R.id.btnCancelar);

        materia = new ViewModelProvider(this).get(MateriaViewModel.class);
        materia = helper.verMateria(id);

        registroProfesor = helper.obtenerProfesorDeMateria(id);
        registroPeriodo = helper.obtenerPeriodoDeMateria(id);

        if (materia != null){
            txtNombreMateria.setText(materia.getNombreMateria());
            txtAulaMateria.setText(materia.getAulaMateria());
            spProfesorMateria.setSelection(obtenerPosicionProfesor(spProfesorMateria,registroProfesor));
            spPeriodoMateria.setSelection(obtenerPosicionPeriodo(spPeriodoMateria, registroPeriodo));
        }

        final AlertDialog alertDialog;
        final  AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setView(customView);
        alertDialog = builder.create();
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materia.setIdProfesor(idProfesor);
                materia.setIdPeriodo(idPeriodo);
                materia.setNombreMateria(txtNombreMateria.getText().toString());
                materia.setAulaMateria(txtAulaMateria.getText().toString());

                helper.abrir();
                String estado = helper.actualizar(materia);
                helper.cerrar();

                adapter.updateItem(materia, position);
                Toast.makeText(view.getContext(),estado,Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje;
                materia.setIdMateria(id);

                helper.abrir();
                mensaje = helper.eliminar(materia);
                helper.cerrar();

                adapter.deleteItem(position);
                Toast.makeText(view.getContext(), mensaje, Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    public int obtenerPosicionProfesor(Spinner spinner, String valor){
        int position = 0;
        for (int i = 0; i <adaptadorProfesores.getCount(); i++){
            if (valor.equals(adaptadorProfesores.getItem(i).toString())){
                position = i;
            }
        }
        return position;
    }

    public int obtenerPosicionPeriodo(Spinner spinner, String valor){
        int position = 0;
        for (int i = 0; i <adaptadorPeriodos.getCount(); i++){
            if (valor.equals(adaptadorPeriodos.getItem(i).toString())){
                position = i;
            }
        }
        return position;
    }
}
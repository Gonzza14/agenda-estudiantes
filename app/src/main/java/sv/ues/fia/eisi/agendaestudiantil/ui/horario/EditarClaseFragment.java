package sv.ues.fia.eisi.agendaestudiantil.ui.horario;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.list.DialogListExtKt;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.clases.TimePickerFragment;
import sv.ues.fia.eisi.agendaestudiantil.ui.materia.MateriaViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.profesor.ProfesorViewModel;

public class EditarClaseFragment extends Fragment {

    private String registroMateria, registroProfesor;
    private Spinner spMateriaClase, spProfesorClase;
    private EditText txtAulaClase, txtDiaClase, txtInicioClase, txtFinClase, txtDescripcionClase;
    private FloatingActionButton btnEditarClase, btnEliminarClase;
    private ArrayAdapter<ProfesorViewModel> adaptadorProfesores;
    private ArrayAdapter<MateriaViewModel> adaptadorMaterias;
    private BD helper;
    private int idMateria, idProfesor;
    private ClaseViewModel clase;
    private String diaClase;
    private int id = 0;

    public EditarClaseFragment() {
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
        clase = new ViewModelProvider(this).get(ClaseViewModel.class);
        return inflater.inflate(R.layout.fragment_editar_clase, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helper = new BD(view.getContext());
        txtAulaClase = view.findViewById(R.id.txtAulaClase);
        txtDescripcionClase = view.findViewById(R.id.txtDescripcionClase);

        txtDiaClase = view.findViewById(R.id.txtDiaClase);
        txtDiaClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPlainListDialog();
            }
        });

        txtInicioClase = view.findViewById(R.id.txtHoraInicioClase);
        txtInicioClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(txtInicioClase);
            }
        });

        txtFinClase = view.findViewById(R.id.txtHoraFinClase);
        txtFinClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(txtFinClase);
            }
        });

        spMateriaClase = view.findViewById(R.id.spMateriaClase);
        spProfesorClase = view.findViewById(R.id.spProfesorClase);

        ArrayList<MateriaViewModel> materias = helper.mostrarMaterias();
        adaptadorMaterias = new ArrayAdapter<>(view.getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, materias);
        spMateriaClase.setAdapter(adaptadorMaterias);

        spMateriaClase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idMateria = ((MateriaViewModel) adapterView.getSelectedItem()).getIdMateria();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<ProfesorViewModel> profesores = helper.mostrarProfesores();
        adaptadorProfesores = new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, profesores);
        spProfesorClase.setAdapter(adaptadorProfesores);

        spProfesorClase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idProfesor = ((ProfesorViewModel) adapterView.getSelectedItem()).getIdProfesor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        id = (int) getArguments().getInt("ID");

        clase = helper.verClase(id);

        registroMateria = helper.obtenerMateriaDeClase(id);
        registroProfesor = helper.obtenerProfesorDeClase(id);

        if (clase !=null){
            txtAulaClase.setText(clase.getAulaClase());
            txtDescripcionClase.setText(clase.getDescripcionClase());
            txtDiaClase.setText(clase.getDiaClase());
            txtInicioClase.setText(clase.getInicioClase());
            txtFinClase.setText(clase.getFinClase());
            spMateriaClase.setSelection(obtenerPosicionMateria(spMateriaClase, registroMateria));
            spProfesorClase.setSelection(obtenerPosicionProfesor(spProfesorClase, registroProfesor));
        }

        btnEditarClase = view.findViewById(R.id.btnEditarClase);
        btnEditarClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clase.setIdHorario(1);
                clase.setIdMateria(idMateria);
                clase.setIdProfesor(idProfesor);
                clase.setAulaClase(txtAulaClase.getText().toString());
                clase.setDiaClase(txtDiaClase.getText().toString());
                clase.setInicioClase(txtInicioClase.getText().toString());
                clase.setFinClase(txtFinClase.getText().toString());
                clase.setDescripcionClase(txtDescripcionClase.getText().toString());

                helper.abrir();
                String estado = helper.actualizar(clase);
                helper.cerrar();

                Toast.makeText(view.getContext(), estado, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
            }
        });

        btnEliminarClase = view.findViewById(R.id.btnEliminarClase);
        btnEliminarClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje;
                clase.setIdClase(id);

                helper.abrir();;
                mensaje = helper.eliminar(clase);
                helper.cerrar();

                Toast.makeText(view.getContext(), mensaje, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
            }
        });

    }

    @SuppressLint("CheckResult")
    private void showPlainListDialog() {

        String[] args = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
        List<String> list = Arrays.asList(args);

        MaterialDialog dialog = new MaterialDialog(getContext(), MaterialDialog.getDEFAULT_BEHAVIOR());
        dialog.title(null, "Seleccione el dia");
        DialogListExtKt.listItems(dialog, null, list, null, false, (materialDialog, integer, s) -> {
            diaClase = args[integer];
            txtDiaClase.setText(diaClase);
            dialog.dismiss();
            return null;
        });
        dialog.show();
    }


    private void showTimePickerDialog(final EditText editText){
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hora, int minuto) {
                String AM_PM = "AM";
                if (hora >=12){
                    AM_PM = "PM";
                    if (hora >= 13 && hora < 24)
                        hora -=12;
                    else
                        hora = 12;
                }else if (hora == 0){
                    hora = 12;
                }
                final String selectedTime = hora + ":" + twoDigits(minuto) + " " + AM_PM;

                editText.setText(selectedTime);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(),"timePicker");
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    public int obtenerPosicionMateria(Spinner spinner, String valor){
        int position = 0;
        for (int i = 0; i <adaptadorMaterias.getCount(); i++){
            if (valor.equals(adaptadorMaterias.getItem(i).toString())){
                position = i;
            }
        }
        return position;
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

}
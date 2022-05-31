package sv.ues.fia.eisi.agendaestudiantil.ui.horario;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.github.tlaabs.timetableview.TimetableView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.clases.TimePickerFragment;
import sv.ues.fia.eisi.agendaestudiantil.ui.materia.MateriaViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.profesor.ProfesorViewModel;

public class ClaseFragment extends Fragment{

    private Spinner spMateriaClase, spProfesorClase;
    private EditText txtAulaClase, txtDiaClase, txtInicioClase, txtFinClase, txtDescripcionClase;
    private FloatingActionButton btnGuardarClase;
    private BD helper;
    private int idMateria, idProfesor;
    private ClaseViewModel clase;
    private String diaClase;

    public static ClaseFragment newInstance() {
        return new ClaseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        clase = new ViewModelProvider(this).get(ClaseViewModel.class);
        return inflater.inflate(R.layout.fragment_clase, container, false);
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
        ArrayAdapter<MateriaViewModel> adaptadorMaterias = new ArrayAdapter<>(view.getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, materias);
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
        ArrayAdapter<ProfesorViewModel> adaptadorProfesores = new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, profesores);
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

        btnGuardarClase = view.findViewById(R.id.btnGuardarClase);
        btnGuardarClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje;
                String aulaClase = txtAulaClase.getText().toString();
                String diaClase = txtDiaClase.getText().toString();
                String inicioClase = txtInicioClase.getText().toString();
                String finClase = txtFinClase.getText().toString();
                String descripcionClase = txtDescripcionClase.getText().toString();

                clase.setIdHorario(1);
                clase.setIdMateria(idMateria);
                clase.setIdProfesor(idProfesor);
                clase.setAulaClase(aulaClase);
                clase.setDiaClase(diaClase);
                clase.setInicioClase(inicioClase);
                clase.setFinClase(finClase);
                clase.setDescripcionClase(descripcionClase);

                helper.abrir();
                mensaje = helper.insertar(clase);
                helper.cerrar();

                Toast.makeText(view.getContext(), mensaje, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();

                txtAulaClase.setText("");
                txtDiaClase.setText("");
                txtInicioClase.setText("");
                txtFinClase.setText("");
                txtDescripcionClase.setText("");
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
}
package sv.ues.fia.eisi.agendaestudiantil.ui.examen;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.clases.DatePickerFragment;
import sv.ues.fia.eisi.agendaestudiantil.clases.Event;
import sv.ues.fia.eisi.agendaestudiantil.clases.PrefCofig;
import sv.ues.fia.eisi.agendaestudiantil.clases.TimePickerFragment;
import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;
import sv.ues.fia.eisi.agendaestudiantil.ui.materia.MateriaViewModel;

public class AgregarExamenFragment extends Fragment {

    private Spinner spMateriaExamen, spTipoExamen;
    private EditText txtFechaExamen, txtHoraExamen, txtDescripcionExamen, txtAulaExamen;
    private FloatingActionButton btnGuardarExamen;
    private BD helper;
    private int idMateria, idTipoExamen;
    private ExamenViewModel examen;
    private int id;
    public AgregarExamenFragment() {
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
        examen = new ViewModelProvider(this).get(ExamenViewModel.class);
        return inflater.inflate(R.layout.fragment_agregar_examen, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helper = new BD(view.getContext());
        txtDescripcionExamen = view.findViewById(R.id.txtDescripcionExamen);
        txtAulaExamen = view.findViewById(R.id.txtAulaExamen);

        txtFechaExamen = view.findViewById(R.id.txtFechaExamen);
        if (CalendarUtils.selectedDate != null)
            txtFechaExamen.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        else if (CalendarUtils.selectedDate == null)
            txtFechaExamen.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate.now()));
        txtFechaExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(txtFechaExamen);
            }
        });

        txtHoraExamen = view.findViewById(R.id.txtHoraExamen);
        txtHoraExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(txtHoraExamen);
            }
        });

        spMateriaExamen = view.findViewById(R.id.spMateriaExamen);
        ArrayList<MateriaViewModel> materias = helper.mostrarMaterias();
        ArrayAdapter<MateriaViewModel> adaptadorMaterias = new ArrayAdapter<>(view.getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, materias);
        spMateriaExamen.setAdapter(adaptadorMaterias);

        spMateriaExamen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idMateria = ((MateriaViewModel) adapterView.getSelectedItem()).getIdMateria();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spTipoExamen = view.findViewById(R.id.spTipoExamen);
        ArrayList<TipoExamenViewModel> tipoExamenes = helper.mostrarTipoExamen();
        ArrayAdapter<TipoExamenViewModel> adaptadorTiposExamenes = new ArrayAdapter<>(view.getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tipoExamenes);
        spTipoExamen.setAdapter(adaptadorTiposExamenes);

        spTipoExamen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idTipoExamen = ((TipoExamenViewModel) adapterView.getSelectedItem()).getIdTipoExamen();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnGuardarExamen = view.findViewById(R.id.btnGuardarExamen);
        btnGuardarExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje;
                String aulaExamen = txtAulaExamen.getText().toString();
                String descripcionExamen = txtDescripcionExamen.getText().toString();
                String fechaExamen = txtFechaExamen.getText().toString();
                String horaExamen = txtHoraExamen.getText().toString();

                examen.setNombreExamen("Examen");
                examen.setIdAgenda(1);
                examen.setIdMateria(idMateria);
                examen.setIdTipoExamen(idTipoExamen);
                examen.setFechaExamen(fechaExamen);
                examen.setHoraExamen(horaExamen);
                examen.setDescripcionExamen(descripcionExamen);
                examen.setAulaExamen(aulaExamen);

                helper.abrir();
                mensaje = helper.insertar(examen);
                helper.cerrar();

                id = helper.obtenerUltimoIdFilaInsertadaExamen();

                examen = helper.verExamen(id);

                Event eventoExamen = new Event();
                eventoExamen.setIdEvento(examen.getIdExamen());
                eventoExamen.setNombre(examen.getNombreExamen());
                eventoExamen.setFecha(examen.getFechaExamen());
                eventoExamen.setHora(examen.getHoraExamen());
                eventoExamen.setDescripcion(examen.getDescripcionExamen());
                Event.eventsList.add(eventoExamen);
                PrefCofig.writeListInPref(getActivity().getApplicationContext(), Event.eventsList);

                Toast.makeText(view.getContext(), mensaje, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();

            }
        });
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = year + "-" + twoDigits(month+1) + "-" + twoDigits(day);
                editText.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
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
}
package sv.ues.fia.eisi.agendaestudiantil.ui.tarea;

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

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.clases.DatePickerFragment;
import sv.ues.fia.eisi.agendaestudiantil.clases.Event;
import sv.ues.fia.eisi.agendaestudiantil.clases.PrefCofig;
import sv.ues.fia.eisi.agendaestudiantil.clases.TimePickerFragment;
import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;
import sv.ues.fia.eisi.agendaestudiantil.ui.materia.MateriaViewModel;

public class AgregarTareaFragment extends Fragment {

    private Spinner spMateriaTarea;
    private EditText txtTituloTarea, txtDescripcionTarea, txtFechaTarea, txtHoraEntrega;
    private FloatingActionButton btnGuardarTarea;
    private BD helper;
    private int idMateria;
    private TareaViewModel tarea;
    private int id;


    public AgregarTareaFragment() {
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
        tarea = new ViewModelProvider(this).get(TareaViewModel.class);
        return inflater.inflate(R.layout.fragment_agregar_tarea, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helper = new BD(view.getContext());
        txtTituloTarea = view.findViewById(R.id.txtTituloTarea);
        txtDescripcionTarea = view.findViewById(R.id.txtDescripcionTarea);

        txtFechaTarea = view.findViewById(R.id.txtFechaTarea);
        if (CalendarUtils.selectedDate != null)
            txtFechaTarea.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        else if (CalendarUtils.selectedDate == null)
            txtFechaTarea.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate.now()));
        txtFechaTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(txtFechaTarea);
            }
        });

        txtHoraEntrega = view.findViewById(R.id.txtHoraTarea);
        txtHoraEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(txtHoraEntrega);
            }
        });

        spMateriaTarea = view.findViewById(R.id.spMateriaTarea);
        ArrayList<MateriaViewModel> materias = helper.mostrarMaterias();
        ArrayAdapter<MateriaViewModel> adaptadorMaterias = new ArrayAdapter<>(view.getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, materias);
        spMateriaTarea.setAdapter(adaptadorMaterias);

        spMateriaTarea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idMateria = ((MateriaViewModel) adapterView.getSelectedItem()).getIdMateria();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnGuardarTarea = view.findViewById(R.id.btnGuardarTarea);
        btnGuardarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje;
                String tituloTarea = txtTituloTarea.getText().toString();
                String descripcionTarea = txtDescripcionTarea.getText().toString();
                String fechaTarea = txtFechaTarea.getText().toString();
                String horaEntrega = txtHoraEntrega.getText().toString();

                tarea.setNombre("Tarea");
                tarea.setIdMateria(idMateria);
                tarea.setIdAgenda(1);
                tarea.setTituloTarea(tituloTarea);
                tarea.setDescripcionTarea(descripcionTarea);
                tarea.setFechaTarea(fechaTarea);
                tarea.setHoraTarea(horaEntrega);
                tarea.setFinalizadaTarea(0);
                tarea.setArchivadaTarea(0);

                helper.abrir();
                mensaje = helper.insertar(tarea);
                helper.cerrar();

                id = helper.obtenerUltimoIdFilaInsertadaTarea();

                tarea = helper.verTarea(id);

                Event eventoTarea = new Event();
                eventoTarea.setIdEvento(tarea.getIdTarea());
                eventoTarea.setNombre(tarea.getNombre());
                eventoTarea.setFecha(tarea.getFechaTarea());
                eventoTarea.setHora(tarea.getHoraTarea());
                eventoTarea.setDescripcion(tarea.getDescripcionTarea());
                Event.eventsList.add(eventoTarea);
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
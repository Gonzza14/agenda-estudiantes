package sv.ues.fia.eisi.agendaestudiantil.ui.recordatorio;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.clases.DatePickerFragment;
import sv.ues.fia.eisi.agendaestudiantil.clases.Event;
import sv.ues.fia.eisi.agendaestudiantil.clases.PrefCofig;
import sv.ues.fia.eisi.agendaestudiantil.clases.TimePickerFragment;
import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;


public class AgregarRecordatorioFragment extends Fragment {

    EditText txtFechaRecordatorio, txtHoraRecordatorio, txtDescripcionRecordatorio;
    private FloatingActionButton btnGuardarRecordatorio;
    private BD helper;
    private RecordatorioViewModel recordatorio;
    private int id;

    public AgregarRecordatorioFragment() {
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
        recordatorio = new ViewModelProvider(this).get(RecordatorioViewModel.class);
        return inflater.inflate(R.layout.fragment_agregar_recordatorio, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helper = new BD(view.getContext());
        txtDescripcionRecordatorio = view.findViewById(R.id.txtDescripcionRecordatorio);

        txtFechaRecordatorio = view.findViewById(R.id.txtFechaRecordatorio);
        txtFechaRecordatorio.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        txtFechaRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(txtFechaRecordatorio);
            }
        });

        txtHoraRecordatorio = view.findViewById(R.id.txtHoraRecordatorio);
        txtHoraRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(txtHoraRecordatorio);
            }
        });

        btnGuardarRecordatorio = view.findViewById(R.id.btnGuardarRecordatorio);
        btnGuardarRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje;
                String fechaRecordatorio = txtFechaRecordatorio.getText().toString();
                String horaRecordatorio = txtHoraRecordatorio.getText().toString();
                String descripcionRecordatorio = txtDescripcionRecordatorio.getText().toString();

                recordatorio.setNombreRecordatorio("Recordatorio");
                recordatorio.setIdAgenda(1);
                recordatorio.setFecha(fechaRecordatorio);
                recordatorio.setHora(horaRecordatorio);
                recordatorio.setDescripcionRecordatorio(descripcionRecordatorio);

                helper.abrir();
                mensaje = helper.insertar(recordatorio);
                helper.cerrar();

                id = helper.obtenerUltimoIdFilaInsertadaRecordatorio();

                recordatorio = helper.verRecordatorio(id);

                Event eventoRecordatorio = new Event();
                eventoRecordatorio.setIdEvento(recordatorio.getIdRecordatorio());
                eventoRecordatorio.setNombre(recordatorio.getNombreRecordatorio());
                eventoRecordatorio.setFecha(recordatorio.getFecha());
                eventoRecordatorio.setHora(recordatorio.getHora());
                eventoRecordatorio.setDescripcion(recordatorio.getDescripcionRecordatorio());
                Event.eventsList.add(eventoRecordatorio);
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
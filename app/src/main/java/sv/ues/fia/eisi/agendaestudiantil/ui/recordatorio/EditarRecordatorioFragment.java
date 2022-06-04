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
import androidx.work.Data;
import androidx.work.WorkManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.clases.DatePickerFragment;
import sv.ues.fia.eisi.agendaestudiantil.clases.Event;
import sv.ues.fia.eisi.agendaestudiantil.clases.Notificacion;
import sv.ues.fia.eisi.agendaestudiantil.clases.PrefCofig;
import sv.ues.fia.eisi.agendaestudiantil.clases.TimePickerFragment;
import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;

public class EditarRecordatorioFragment extends Fragment {

    EditText txtFechaRecordatorio, txtHoraRecordatorio, txtDescripcionRecordatorio;
    private FloatingActionButton btnEditarRecordatorio, btnEliminarRecordatorio;
    private BD helper;
    private RecordatorioViewModel recordatorio;
    private int id;
    Calendar actual = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    public EditarRecordatorioFragment() {
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
        return inflater.inflate(R.layout.fragment_editar_recordatorio, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        helper = new BD(view.getContext());
        txtDescripcionRecordatorio = view.findViewById(R.id.txtDescripcionRecordatorio);

        txtFechaRecordatorio = view.findViewById(R.id.txtFechaRecordatorio);
        if (CalendarUtils.selectedDate != null)
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

        id = (int) getArguments().getInt("ID");

        recordatorio = helper.verRecordatorio(id);


        if (recordatorio != null){
            txtFechaRecordatorio.setText(recordatorio.getFecha());
            txtHoraRecordatorio.setText(recordatorio.getHora());
            txtDescripcionRecordatorio.setText(recordatorio.getDescripcionRecordatorio());

            String parts [], parts2[], parts3[], fecha, tiempo, horaPura, amPm;
            int dia, mes, año, hora, minuto;

            fecha = recordatorio.getFecha();

            parts = fecha.split("-");
            año = Integer.parseInt(parts[0]);
            mes = Integer.parseInt(parts[1])-1;
            dia = Integer.parseInt(parts[2]);

            calendar.set(Calendar.DAY_OF_MONTH, dia);
            calendar.set(Calendar.MONTH, mes);
            calendar.set(Calendar.YEAR, año);

            tiempo = recordatorio.getHora();

            parts2 = tiempo.split(" ");
            horaPura = parts2[0];
            amPm = parts2[1];

            parts3 = horaPura.split(":");
            hora = Integer.parseInt(parts3[0]);
            minuto = Integer.parseInt(parts3[1]);

            if (amPm.equals("PM") && hora != 12)
                hora += 12;
            if (amPm.equals("AM") && hora == 12)
                hora +=12;

            calendar.set(Calendar.HOUR_OF_DAY, hora);
            calendar.set(Calendar.MINUTE, minuto);
        }

        btnEditarRecordatorio = view.findViewById(R.id.btnEditarRecordatorio);
        btnEditarRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarNotificacion(recordatorio.getNombreRecordatorio()+recordatorio.getIdRecordatorio());
                recordatorio.setNombreRecordatorio("Recordatorio");
                recordatorio.setIdAgenda(1);
                recordatorio.setFecha(txtFechaRecordatorio.getText().toString());
                recordatorio.setHora(txtHoraRecordatorio.getText().toString());
                recordatorio.setDescripcionRecordatorio(txtDescripcionRecordatorio.getText().toString());

                helper.abrir();
                String estado = helper.actualizar(recordatorio);
                helper.cerrar();

                Event.eventsList = (ArrayList<Event>) PrefCofig.readListFromPref(getContext());
                if (Event.eventsList == null)
                    Event.eventsList = new ArrayList<>();

                for (Event event : Event.eventsList) {
                    if (event.getIdEvento() == recordatorio.getIdRecordatorio() && event.getNombre().equals(recordatorio.getNombreRecordatorio())){
                        event.setIdEvento(recordatorio.getIdRecordatorio());
                        event.setNombre(recordatorio.getNombreRecordatorio());
                        event.setFecha(recordatorio.getFecha());
                        event.setHora(recordatorio.getHora());
                        event.setDescripcion(recordatorio.getDescripcionRecordatorio());
                    }
                }
                PrefCofig.writeListInPref(getActivity().getApplicationContext(), Event.eventsList);

                String tag = recordatorio.getNombreRecordatorio()+recordatorio.getIdRecordatorio();
                long time = calendar.getTimeInMillis();
                long timeNow = System.currentTimeMillis();
                Long alertTime = time - timeNow;

                Data data = guardarData(recordatorio.getNombreRecordatorio(), recordatorio.getDescripcionRecordatorio(), "Notficacion de "+ recordatorio.getNombreRecordatorio());
                Notificacion.guardarNotificacion(alertTime,data,tag);

                Toast.makeText(view.getContext(), estado, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
            }
        });

        btnEliminarRecordatorio = view.findViewById(R.id.btnEliminarRecordatorio);
        btnEliminarRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje;
                eliminarNotificacion(recordatorio.getNombreRecordatorio()+recordatorio.getIdRecordatorio());
                ArrayList<Event> eventoEliminar = new ArrayList<>();
                Event.eventsList = (ArrayList<Event>) PrefCofig.readListFromPref(getContext());
                if (Event.eventsList == null)
                    Event.eventsList = new ArrayList<>();
                for (Event event : Event.eventsList){
                    if (event.getIdEvento() == recordatorio.getIdRecordatorio() && event.getNombre().equals(recordatorio.getNombreRecordatorio())) {
                        eventoEliminar.add(event);
                    }
                }
                Event.eventsList.removeAll(eventoEliminar);
                PrefCofig.writeListInPref(getActivity().getApplicationContext(), Event.eventsList);

                recordatorio.setIdRecordatorio(id);
                helper.abrir();
                mensaje = helper.eliminar(recordatorio);
                helper.cerrar();

                Toast.makeText(view.getContext(), mensaje, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
            }
        });
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
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
                calendar.set(Calendar.HOUR_OF_DAY,hora);
                calendar.set(Calendar.MINUTE,minuto);
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

     private void eliminarNotificacion(String tag){
        WorkManager.getInstance(getContext()).cancelAllWorkByTag(tag);
        Toast.makeText(getContext(), "Notificacion eliminada", Toast.LENGTH_SHORT).show();
    }

    private Data guardarData(String titulo, String detalle, String ticker){
        return new Data.Builder()
                .putString("TITULO", titulo)
                .putString("DETALLE", detalle)
                .putString("TICKER", ticker).build();
    }
}
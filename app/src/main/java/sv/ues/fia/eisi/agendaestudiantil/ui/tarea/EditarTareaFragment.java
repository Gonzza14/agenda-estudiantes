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
import androidx.work.Data;
import androidx.work.WorkManager;

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
import java.util.Calendar;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.clases.DatePickerFragment;
import sv.ues.fia.eisi.agendaestudiantil.clases.Event;
import sv.ues.fia.eisi.agendaestudiantil.clases.Notificacion;
import sv.ues.fia.eisi.agendaestudiantil.clases.PrefCofig;
import sv.ues.fia.eisi.agendaestudiantil.clases.TimePickerFragment;
import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;
import sv.ues.fia.eisi.agendaestudiantil.ui.materia.MateriaViewModel;

public class EditarTareaFragment extends Fragment {

    private String registroMateria;
    private Spinner spMateriaTarea;
    private EditText txtTituloTarea, txtDescripcionTarea, txtFechaTarea, txtHoraEntrega;
    private FloatingActionButton btnEditarTarea, btnEliminarTarea;
    private ArrayAdapter<MateriaViewModel> adaptadorMaterias;
    private BD helper;
    private int idMateria;
    private TareaViewModel tarea;
    private int id;
    Calendar calendar = Calendar.getInstance();
    public EditarTareaFragment() {
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
        return inflater.inflate(R.layout.fragment_editar_tarea, container, false);
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
        adaptadorMaterias = new ArrayAdapter<>(view.getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, materias);
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

        id = (int) getArguments().getInt("ID");

        tarea = helper.verTarea(id);

        registroMateria = helper.obtenerMateriaDeTarea(id);

        if (tarea !=null){
            txtTituloTarea.setText(tarea.getTituloTarea());
            txtDescripcionTarea.setText(tarea.getDescripcionTarea());
            txtFechaTarea.setText(tarea.getFechaTarea());
            txtHoraEntrega.setText(tarea.getHoraTarea());
            spMateriaTarea.setSelection(obtenerPosicionMateria(spMateriaTarea, registroMateria));

            String parts [], parts2[], parts3[], fecha, tiempo, horaPura, amPm;
            int dia, mes, año, hora, minuto;

            fecha = tarea.getFechaTarea();

            parts = fecha.split("-");
            año = Integer.parseInt(parts[0]);
            mes = Integer.parseInt(parts[1])-1;
            dia = Integer.parseInt(parts[2]);

            calendar.set(Calendar.DAY_OF_MONTH, dia);
            calendar.set(Calendar.MONTH, mes);
            calendar.set(Calendar.YEAR, año);

            tiempo = tarea.getHoraTarea();

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

        btnEditarTarea = view.findViewById(R.id.btnEditarTarea);
        btnEditarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarNotificacion( tarea.getNombre()+tarea.getIdTarea());
                tarea.setNombre("Tarea");
                tarea.setIdMateria(idMateria);
                tarea.setIdAgenda(1);
                tarea.setTituloTarea(txtTituloTarea.getText().toString());
                tarea.setDescripcionTarea(txtDescripcionTarea.getText().toString());
                tarea.setFechaTarea(txtFechaTarea.getText().toString());
                tarea.setHoraTarea(txtHoraEntrega.getText().toString());
                tarea.setFinalizadaTarea(0);
                tarea.setArchivadaTarea(0);

                helper.abrir();
                String estado = helper.actualizar(tarea);
                helper.cerrar();

                Event.eventsList = (ArrayList<Event>) PrefCofig.readListFromPref(getContext());
                if (Event.eventsList == null)
                    Event.eventsList = new ArrayList<>();
                for (Event event: Event.eventsList){
                    if (event.getIdEvento() == tarea.getIdTarea() && event.getNombre().equals(tarea.getNombre())){
                        event.setIdEvento(tarea.getIdTarea());
                        event.setNombre(tarea.getNombre());
                        event.setFecha(tarea.getFechaTarea());
                        event.setHora(tarea.getHoraTarea());
                        event.setDescripcion(tarea.getDescripcionTarea());
                    }
                }
                PrefCofig.writeListInPref(getActivity().getApplicationContext(), Event.eventsList);

                String tag = tarea.getNombre()+tarea.getIdTarea();
                long time = calendar.getTimeInMillis();
                long timeNow = System.currentTimeMillis();
                Long alertTime = time - timeNow;

                Data data = guardarData(tarea.getNombre()+": " + tarea.getTituloTarea(), tarea.getDescripcionTarea(), "Notificacion de " + tarea.getTituloTarea());
                Notificacion.guardarNotificacion(alertTime,data,tag);

                Toast.makeText(view.getContext(), estado, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
            }
        });

        btnEliminarTarea = view.findViewById(R.id.btnEliminarTarea);
        btnEliminarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje;
                eliminarNotificacion( tarea.getNombre()+tarea.getIdTarea());
                ArrayList<Event> eventoEliminar = new ArrayList<>();
                Event.eventsList = (ArrayList<Event>) PrefCofig.readListFromPref(getContext());
                if (Event.eventsList == null)
                    Event.eventsList = new ArrayList<>();
                for (Event event : Event.eventsList){
                    if (event.getIdEvento() == tarea.getIdTarea() && event.getNombre().equals(tarea.getNombre())) {
                        eventoEliminar.add(event);
                    }
                }
                Event.eventsList.removeAll(eventoEliminar);
                PrefCofig.writeListInPref(getActivity().getApplicationContext(), Event.eventsList);

                tarea.setIdTarea(id);
                helper.abrir();
                mensaje = helper.eliminar(tarea);
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

    public int obtenerPosicionMateria(Spinner spinner, String valor){
        int position = 0;
        for (int i = 0; i <adaptadorMaterias.getCount(); i++){
            if (valor.equals(adaptadorMaterias.getItem(i).toString())){
                position = i;
            }
        }
        return position;
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
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

public class EditarExamenFragment extends Fragment {
    private String registroMateria, registroTipoExamen;
    private Spinner spMateriaExamen, spTipoExamen;
    private EditText txtFechaExamen, txtHoraExamen, txtDescripcionExamen, txtAulaExamen;
    private FloatingActionButton btnEditarExamen, btnEliminarExamen;
    private ArrayAdapter<MateriaViewModel> adaptadorMaterias;
    private ArrayAdapter<TipoExamenViewModel> adaptadorTiposExamenes;
    private BD helper;
    private int idMateria, idTipoExamen;
    private ExamenViewModel examen;
    private NotaExamenViewModel notaExamen;
    private int id = 0;
    Calendar calendar = Calendar.getInstance();


    public EditarExamenFragment() {
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
        notaExamen = new ViewModelProvider(this).get(NotaExamenViewModel.class);
        examen = new ViewModelProvider(this).get(ExamenViewModel.class);
        return inflater.inflate(R.layout.fragment_editar_examen, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        helper = new BD(view.getContext());
        txtDescripcionExamen = view.findViewById(R.id.txtDescripcionExamen);
        txtAulaExamen = view.findViewById(R.id.txtAulaExamen);

        txtFechaExamen = view.findViewById(R.id.txtFechaExamen);
        if(CalendarUtils.selectedDate !=null)
            txtFechaExamen.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
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
        adaptadorMaterias = new ArrayAdapter<>(view.getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, materias);
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
        adaptadorTiposExamenes = new ArrayAdapter<>(view.getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tipoExamenes);
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

        id = (int) getArguments().getInt("ID");

        examen = helper.verExamen(id);

        registroMateria = helper.obtenerMateriaDeExamen(id);
        registroTipoExamen = helper.obtenerTipoExamenDeExamen(id);

        if (examen !=null){
            txtAulaExamen.setText(examen.getAulaExamen());
            txtDescripcionExamen.setText(examen.getDescripcionExamen());
            txtFechaExamen.setText(examen.getFechaExamen());
            txtHoraExamen.setText(examen.getHoraExamen());
            spMateriaExamen.setSelection(obtenerPosicionMateria(spMateriaExamen, registroMateria));
            spTipoExamen.setSelection(obtenerPosicionTipoExamen(spTipoExamen, registroTipoExamen));

            String parts [], parts2[], parts3[], fecha, tiempo, horaPura, amPm;
            int dia, mes, año, hora, minuto;

            fecha = examen.getFechaExamen();

            parts = fecha.split("-");
            año = Integer.parseInt(parts[0]);
            mes = Integer.parseInt(parts[1])-1;
            dia = Integer.parseInt(parts[2]);

            calendar.set(Calendar.DAY_OF_MONTH, dia);
            calendar.set(Calendar.MONTH, mes);
            calendar.set(Calendar.YEAR, año);

            tiempo = examen.getHoraExamen();

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

        btnEditarExamen = view.findViewById(R.id.btnEditarExamen);
        btnEditarExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarNotificacion(examen.getNombreExamen()+examen.getIdExamen());
                examen.setNombreExamen("Examen");
                examen.setIdAgenda(1);
                examen.setIdMateria(idMateria);
                examen.setIdTipoExamen(idTipoExamen);
                examen.setFechaExamen(txtFechaExamen.getText().toString());
                examen.setHoraExamen(txtHoraExamen.getText().toString());
                examen.setDescripcionExamen(txtDescripcionExamen.getText().toString());
                examen.setAulaExamen(txtAulaExamen.getText().toString());

                helper.abrir();
                String estado = helper.actualizar(examen);
                helper.cerrar();


                notaExamen = helper.verNotaExamen(id);
                notaExamen.setIdMateria(idMateria);

                helper.abrir();
                helper.actualizar(notaExamen);
                helper.cerrar();

                Event.eventsList = (ArrayList<Event>) PrefCofig.readListFromPref(getContext());
                if (Event.eventsList == null)
                    Event.eventsList = new ArrayList<>();
                for (Event event : Event.eventsList){
                    if (event.getIdEvento() == examen.getIdExamen() && event.getNombre().equals(examen.getNombreExamen())){
                        event.setIdEvento(examen.getIdExamen());
                        event.setNombre(examen.getNombreExamen());
                        event.setFecha(examen.getFechaExamen());
                        event.setHora(examen.getHoraExamen());
                        event.setDescripcion(examen.getDescripcionExamen());
                    }
                }
                PrefCofig.writeListInPref(getActivity().getApplicationContext(), Event.eventsList);

                String tag = examen.getNombreExamen()+examen.getIdExamen();
                long time = calendar.getTimeInMillis();
                long timeNow = System.currentTimeMillis();
                Long alertTime = time - timeNow;

                Data data = guardarData(examen.getNombreExamen(),helper.obtenerTipoExamenDeExamen(examen.getIdExamen())+" "+ examen.getDescripcionExamen(),"Notifiacion de " + examen.getNombreExamen());
                Notificacion.guardarNotificacion(alertTime,data,tag);

                Toast.makeText(view.getContext(), estado, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
            }
        });

        btnEliminarExamen = view.findViewById(R.id.btnEliminarExamen);
        btnEliminarExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje;
                eliminarNotificacion(examen.getNombreExamen()+examen.getIdExamen());

                ArrayList<Event> eventoEliminar = new ArrayList<>();
                Event.eventsList = (ArrayList<Event>) PrefCofig.readListFromPref(getContext());
                if (Event.eventsList == null)
                    Event.eventsList = new ArrayList<>();
                for (Event event : Event.eventsList){
                    if (event.getIdEvento() == examen.getIdExamen() && event.getNombre().equals(examen.getNombreExamen())) {
                        eventoEliminar.add(event);
                    }
                }
                Event.eventsList.removeAll(eventoEliminar);
                PrefCofig.writeListInPref(getActivity().getApplicationContext(), Event.eventsList);

                examen.setIdExamen(id);
                helper.abrir();
                mensaje = helper.eliminar(examen);
                helper.cerrar();

                notaExamen.setIdExamen(id);
                helper.abrir();
                helper.eliminar(notaExamen);
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

    public int obtenerPosicionTipoExamen(Spinner spinner, String valor){
        int position = 0;
        for (int i = 0; i < adaptadorTiposExamenes.getCount(); i++){
            if (valor.equals(adaptadorTiposExamenes.getItem(i).toString())){
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
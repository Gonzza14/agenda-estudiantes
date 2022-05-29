package sv.ues.fia.eisi.agendaestudiantil.ui.horario;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.ui.profesor.ProfesorViewModel;

public class HorarioFragment extends Fragment {

    private HorarioViewModel mViewModel;
    private ClaseViewModel mClase;
    private FloatingActionButton btnAgregarClase;
    private TimetableView timetable;
    private BD helper;
    private int id = 0;

    public static HorarioFragment newInstance() {
        return new HorarioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_horario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*Navigation.findNavController(view).getCurrentBackStackEntry().getSavedStateHandle().getLiveData("ID").observe(this.getViewLifecycleOwner(),(Observer)null);
        id = (int) getArguments().getInt("ID");
        String mensaje = ""+id;
        Toast.makeText(view.getContext(), mensaje, Toast.LENGTH_SHORT).show();*/

        helper = new BD(view.getContext());
        timetable = view.findViewById(R.id.timetable);
        btnAgregarClase = view.findViewById(R.id.btnAgregarClase);

        String tiempo, hora, minuto, parteMinuto, amPm;
        int horaParseada, minutoParseado, dia;
        String [] parts;

        ArrayList<ClaseViewModel> clases = helper.mostrarClases();

        for (int i = 0; i<clases.size(); i++){
            ArrayList<Schedule> schedules = new ArrayList<Schedule>();
            Schedule schedule = new Schedule();
            schedule.setClassTitle(helper.obtenerMateriaDeClase(clases.get(i).getIdClase()));
            schedule.setClassPlace(clases.get(i).getAulaClase());
            schedule.setProfessorName(helper.obtenerProfesorDeClase(clases.get(i).getIdClase()));

            dia = obtenerIdDia(clases.get(i).getDiaClase());
            schedule.setDay(dia);

            tiempo= clases.get(i).getInicioClase();
            parts = tiempo.split(":");
            hora = parts[0];
            horaParseada = Integer.parseInt(hora);
            parteMinuto = parts[1];
            minuto = parteMinuto.substring(0,2);
            minutoParseado = Integer.parseInt(minuto);
            amPm = parteMinuto.substring(3,5);
            if (amPm.equals("PM") && horaParseada != 12)
                horaParseada += 12;
            if (amPm.equals("AM") && horaParseada == 12)
                horaParseada +=12;
            schedule.setStartTime(new Time(horaParseada,minutoParseado));

            tiempo = clases.get(i).getFinClase();
            parts = tiempo.split(":");
            hora = parts[0];
            horaParseada = Integer.parseInt(hora);
            parteMinuto = parts[1];
            minuto = parteMinuto.substring(0,2);
            minutoParseado = Integer.parseInt(minuto);
            amPm = parteMinuto.substring(3,5);
            if (amPm.equals("PM") && horaParseada != 12)
                horaParseada += 12;
            if (amPm.equals("AM") && horaParseada == 12)
                horaParseada +=12;
            schedule.setEndTime(new Time(horaParseada,minutoParseado));
            schedules.add(schedule);
            timetable.add(schedules);
            timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
                @Override
                public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", clases.get(idx).getIdClase());
                    Navigation.findNavController(view).navigate(R.id.nav_editar_clase, bundle);
                }
            });
        }

        btnAgregarClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_agregar_clase);
            }
        });
    }

    private int obtenerIdDia(String dia){
        int id = 0;
        if (dia.equals("Lunes"))
            id = 0;
        else if (dia.equals("Martes"))
            id = 1;
        else if (dia.equals("Miercoles"))
            id = 2;
        else if (dia.equals("Jueves"))
            id = 3;
        else if (dia.equals("Viernes"))
            id = 4;
        else if (dia.equals("Sabado"))
            id = 5;
        else if (dia.equals("Domingo"))
            id = 6;
        return id;
    }
}
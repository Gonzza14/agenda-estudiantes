package sv.ues.fia.eisi.agendaestudiantil.adaptadores;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.Event;
import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;
import sv.ues.fia.eisi.agendaestudiantil.ui.examen.ExamenViewModel;

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(@NonNull Context context, List<Event> events) {
        super(context, 0, events);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       Event event = getItem(position);
       if (convertView == null)
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);
        TextView lblEventCell = convertView.findViewById(R.id.lblEventCell);
        String [] parts, parts2;
        String tiempo, hora = "", amPm, minuto;
        int convertirHora;
        tiempo = event.getHora();
        parts = tiempo.split(" ");
        parts2 = tiempo.split(":");
        minuto = parts2[1];
        amPm = parts[1];

        convertirHora = Integer.parseInt(parts2[0]);

        if (amPm.equals("PM") && convertirHora != 12){
            convertirHora += 12;
            hora=convertirHora+":"+minuto.substring(0,2)+":00";
        }
        else if(amPm.equals("PM") && convertirHora == 12)
            hora=convertirHora+":"+minuto.substring(0,2)+":00";

        if (amPm.equals("AM"))
            if (amPm.equals("AM") && convertirHora == 12) {
                convertirHora -= 12;
                hora=convertirHora+":"+minuto.substring(0,2)+":00";
            }
            if (convertirHora < 10)
                hora="0"+convertirHora+":"+minuto.substring(0,2)+":00";
            else
                hora=convertirHora+":"+minuto.substring(0,2)+":00";
        String eventTitle = event.getNombre() + " " + CalendarUtils.formattedTime(LocalTime.parse(hora));
        lblEventCell.setText(eventTitle);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("ID", event.getIdEvento());
                Navigation.findNavController(view).navigate(R.id.nav_editar_examen, bundle);
            }
        });
        return convertView;
    }

}

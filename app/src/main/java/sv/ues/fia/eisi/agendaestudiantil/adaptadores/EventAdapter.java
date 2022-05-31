package sv.ues.fia.eisi.agendaestudiantil.adaptadores;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

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

        String eventTitle = event.getNombre() + " " + CalendarUtils.formattedTime(event.getHora());
        lblEventCell.setText(eventTitle);
        return convertView;
    }
}

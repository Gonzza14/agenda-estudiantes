package sv.ues.fia.eisi.agendaestudiantil.adaptadores;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.list.DialogListExtKt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import sv.ues.fia.eisi.agendaestudiantil.InicioActivity;
import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.clases.Event;
import sv.ues.fia.eisi.agendaestudiantil.ui.examen.ExamenViewModel;

public class ListaEventoAdaptador extends RecyclerView.Adapter<ListaEventoAdaptador.EventoViewHolder> {

    private ArrayList<Event> listaEvento;
    private BD helper;

    public ListaEventoAdaptador(ArrayList<Event> listEvento){
        this.listaEvento = listEvento;
    }

    @NonNull
    @Override
    public ListaEventoAdaptador.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_evento, null, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaEventoAdaptador.EventoViewHolder holder, int position) {
        holder.lblNombreEvento.setText(listaEvento.get(position).getNombre());
        holder.lblFechaHoraEvento.setText(listaEvento.get(position).getFecha() + " " + listaEvento.get(position).getHora());
        holder.lblDescripcionEvento.setText(listaEvento.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return listaEvento.size();
    }
    public void ordenarPorHora(){
        Collections.sort(listaEvento, Event.timeComparator);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ordenarPorFecha(){
        Collections.sort(listaEvento, Event.dateComparator);
    }


    public class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView lblNombreEvento, lblFechaHoraEvento, lblDescripcionEvento;
        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNombreEvento = itemView.findViewById(R.id.lblNombreEvento);
            lblFechaHoraEvento = itemView.findViewById(R.id.lblFechaHoraEvento);
            lblDescripcionEvento = itemView.findViewById(R.id.lblDescripcionEvento);

            helper = new BD(itemView.getContext());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", listaEvento.get(getAdapterPosition()).getIdEvento());
                    if (listaEvento.get(getAdapterPosition()).getNombre().equals("Examen"))
                        showPlainListDialog(bundle, view);
                    else if (listaEvento.get(getAdapterPosition()).getNombre().equals("Tarea"))
                        Navigation.findNavController(view).navigate(R.id.nav_editar_tarea, bundle);
                    else if (listaEvento.get(getAdapterPosition()).getNombre().equals("Recordatorio"))
                        Navigation.findNavController(view).navigate(R.id.nav_editar_recordatorio, bundle);
                }
            });
        }

        private void showPlainListDialog(Bundle bundle, View view) {
            String[] args = {"Editar examen", "Agregar nota de examen"};
            List<String> list = Arrays.asList(args);

            MaterialDialog dialog = new MaterialDialog(view.getContext(), MaterialDialog.getDEFAULT_BEHAVIOR());
            dialog.title(null, "Seleccione");
            DialogListExtKt.listItems(dialog, null, list, null, false, (materialDialog, integer, s) -> {
                if (integer == 0)
                    Navigation.findNavController(view).navigate(R.id.nav_editar_examen, bundle);
                if (integer == 1)
                    Navigation.findNavController(view).navigate(R.id.nav_agregar_nota_examen,bundle);
                dialog.dismiss();
                return null;
            });
            dialog.show();
        }
    }
}

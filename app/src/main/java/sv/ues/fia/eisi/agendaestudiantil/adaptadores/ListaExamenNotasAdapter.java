package sv.ues.fia.eisi.agendaestudiantil.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.ui.examen.ExamenViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.examen.NotaExamenViewModel;

public class ListaExamenNotasAdapter extends RecyclerView.Adapter<ListaExamenNotasAdapter.ExamenNotasViewHolder> {

    private ArrayList<NotaExamenViewModel> listaNotaExamen;
    private BD helper;

    public ListaExamenNotasAdapter (ArrayList<NotaExamenViewModel> listaNotaExamen){
        this.listaNotaExamen = listaNotaExamen;
    }

    @NonNull
    @Override
    public ExamenNotasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_examen_notas, null, false);
        return new ExamenNotasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamenNotasViewHolder holder, int position) {
        holder.lblNombreExamen.setText(helper.obtenerTipoExamenDeExamen(listaNotaExamen.get(position).getIdExamen()));
        holder.lblPorcentajeExamen.setText(listaNotaExamen.get(position).getPorcentaje()+"%");
        holder.lblNotaExamen.setText(String.valueOf(listaNotaExamen.get(position).getCalificacion()));
    }

    @Override
    public int getItemCount() {
        return listaNotaExamen.size();
    }

    public class ExamenNotasViewHolder extends RecyclerView.ViewHolder {
        TextView lblNombreExamen, lblPorcentajeExamen, lblNotaExamen;
        public ExamenNotasViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNombreExamen = itemView.findViewById(R.id.lblNombreExamen);
            lblPorcentajeExamen = itemView.findViewById(R.id.lblPorcentajeExamen);
            lblNotaExamen = itemView.findViewById(R.id.lblNotaExamen);

            helper = new BD(itemView.getContext());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}

package sv.ues.fia.eisi.agendaestudiantil.adaptadores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.ui.tarea.TareaViewModel;

public class ListaTareaAdapter extends RecyclerView.Adapter<ListaTareaAdapter.TareaViewHolder> {

    private ArrayList<TareaViewModel> listaTarea;
    private BD helper;

    public ListaTareaAdapter(ArrayList<TareaViewModel> listaTarea){
        this.listaTarea = listaTarea;
    }
    @NonNull
    @Override
    public ListaTareaAdapter.TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_tarea, null, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaTareaAdapter.TareaViewHolder holder, int position) {
        holder.lblTituloTarea.setText(listaTarea.get(position).getTituloTarea());
        holder.lblMateriaTarea.setText(helper.obtenerMateriaDeTarea(listaTarea.get(position).getIdTarea()));
        holder.lblDescripcionTarea.setText(listaTarea.get(position).getDescripcionTarea());
        holder.lblFechaHora.setText(listaTarea.get(position).getFechaTarea() + " " + listaTarea.get(position).getHoraTarea());
    }

    @Override
    public int getItemCount() {
        return listaTarea.size();
    }

    public class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView lblTituloTarea, lblMateriaTarea, lblDescripcionTarea, lblFechaHora;
        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTituloTarea = itemView.findViewById(R.id.lblTituloTarea);
            lblMateriaTarea = itemView.findViewById(R.id.lblMateriaTarea);
            lblDescripcionTarea = itemView.findViewById(R.id.lblDescripcionTarea);
            lblFechaHora = itemView.findViewById(R.id.lblFechaHora);

            helper = new BD(itemView.getContext());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", listaTarea.get(getAdapterPosition()).getIdTarea());
                    Navigation.findNavController(view).navigate(R.id.nav_editar_tarea, bundle);
                }
            });
        }
    }
}

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
import sv.ues.fia.eisi.agendaestudiantil.ui.examen.ExamenViewModel;

public class ListaExamenAdapter extends RecyclerView.Adapter<ListaExamenAdapter.ExamenViewHolder> {

    private ArrayList<ExamenViewModel> listaExamen;
    private BD helper;

    public ListaExamenAdapter(ArrayList<ExamenViewModel> listaExamen){
        this.listaExamen = listaExamen;
    }

    @NonNull
    @Override
    public ListaExamenAdapter.ExamenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_examen, null, false);
        return new ExamenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaExamenAdapter.ExamenViewHolder holder, int position) {
        holder.lblMateriaExamen.setText(helper.obtenerMateriaDeExamen(listaExamen.get(position).getIdExamen()));
        holder.lblTipoExamen.setText(helper.obtenerTipoExamenDeExamen(listaExamen.get(position).getIdExamen()));
        holder.lblFechaHora.setText(listaExamen.get(position).getFechaExamen() + " " + listaExamen.get(position).getHoraExamen());
        holder.lblAulaExamen.setText(listaExamen.get(position).getAulaExamen());
    }

    @Override
    public int getItemCount() {
        return listaExamen.size();
    }

    public class ExamenViewHolder extends RecyclerView.ViewHolder {
        TextView lblMateriaExamen, lblTipoExamen, lblFechaHora, lblAulaExamen;
        public ExamenViewHolder(@NonNull View itemView) {
            super(itemView);
            lblMateriaExamen = itemView.findViewById(R.id.lblMateriaExamen);
            lblTipoExamen = itemView.findViewById(R.id.lblTipoExamen);
            lblFechaHora = itemView.findViewById(R.id.lblFechaHora);
            lblAulaExamen = itemView.findViewById(R.id.lblAulaExamen);

            helper = new BD(itemView.getContext());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", listaExamen.get(getAdapterPosition()).getIdExamen());
                    Navigation.findNavController(view).navigate(R.id.nav_editar_examen, bundle);
                }
            });

        }
    }
}

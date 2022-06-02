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
import sv.ues.fia.eisi.agendaestudiantil.ui.recordatorio.RecordatorioViewModel;

public class ListaRecordatorioAdapter extends RecyclerView.Adapter<ListaRecordatorioAdapter.RecordatorioViewHolder> {

    private ArrayList<RecordatorioViewModel> listaRecordatorio;
    private BD helper;

    public ListaRecordatorioAdapter(ArrayList<RecordatorioViewModel> listaRecordatorio) {
        this.listaRecordatorio = listaRecordatorio;
    }

    @NonNull
    @Override
    public ListaRecordatorioAdapter.RecordatorioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_recordatorio, null, false);
        return new RecordatorioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaRecordatorioAdapter.RecordatorioViewHolder holder, int position) {
        holder.lblDescripcionRecordatorio.setText(listaRecordatorio.get(position).getDescripcionRecordatorio());
        holder.lblFechaHora.setText(listaRecordatorio.get(position).getFecha() + " " + listaRecordatorio.get(position).getHora());
    }

    @Override
    public int getItemCount() {
        return listaRecordatorio.size();
    }

    public class RecordatorioViewHolder extends RecyclerView.ViewHolder {
        TextView lblDescripcionRecordatorio, lblFechaHora;
        public RecordatorioViewHolder(@NonNull View itemView) {
            super(itemView);
            lblDescripcionRecordatorio = itemView.findViewById(R.id.lblDescripcionRecordatorio);
            lblFechaHora = itemView.findViewById(R.id.lblFechaHora);

            helper = new BD(itemView.getContext());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", listaRecordatorio.get(getAdapterPosition()).getIdRecordatorio());
                    Navigation.findNavController(view).navigate(R.id.nav_editar_recordatorio, bundle);
                }
            });
        }
    }
}

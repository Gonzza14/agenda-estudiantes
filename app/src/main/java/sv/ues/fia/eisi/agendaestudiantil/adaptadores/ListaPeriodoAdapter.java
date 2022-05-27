package sv.ues.fia.eisi.agendaestudiantil.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.ui.periodo.PeriodoViewModel;

public class ListaPeriodoAdapter extends RecyclerView.Adapter<ListaPeriodoAdapter.PeriodoViewHolder> {

    private ArrayList<PeriodoViewModel> listaPeriodo;

    public ListaPeriodoAdapter(ArrayList<PeriodoViewModel> listaPeriodo) {
        this.listaPeriodo = listaPeriodo;
    }

    @NonNull
    @Override
    public PeriodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_periodo, null, false);
        return new PeriodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeriodoViewHolder holder, int position) {
        holder.lblPeriodoView.setText(listaPeriodo.get(position).getTituloPeriodo());
        holder.lblFechaView.setText(listaPeriodo.get(position).getInicioPeriodo() + " hasta " + listaPeriodo.get(position).getFinPeriodo());
    }

    @Override
    public int getItemCount() {
        return listaPeriodo.size();
    }

    public class PeriodoViewHolder extends RecyclerView.ViewHolder {
        TextView lblPeriodoView;
        TextView lblFechaView;
        public PeriodoViewHolder(@NonNull View itemView) {
            super(itemView);
            lblPeriodoView = itemView.findViewById(R.id.lblPeriodoView);
            lblFechaView = itemView.findViewById(R.id.lblFechaView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}

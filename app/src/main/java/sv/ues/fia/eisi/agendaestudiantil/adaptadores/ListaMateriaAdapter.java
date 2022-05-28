package sv.ues.fia.eisi.agendaestudiantil.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.ui.materia.MateriaViewModel;

public class ListaMateriaAdapter extends RecyclerView.Adapter<ListaMateriaAdapter.MateriaViewHolder> implements View.OnClickListener{

    private ArrayList<MateriaViewModel> listaMateria;
    View.OnClickListener listener;

    public ListaMateriaAdapter(ArrayList<MateriaViewModel> listaMateria){
        this.listaMateria = listaMateria;
    }

    @NonNull
    @Override
    public MateriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_materia, null, false);
        view.setOnClickListener(this);
        return new MateriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriaViewHolder holder, int position) {
        holder.lblMateriaView.setText(listaMateria.get(position).getNombreMateria());
        holder.lblAulaView.setText(listaMateria.get(position).getAulaMateria());
        holder.lblPeriodoView.setText(listaMateria.get(position).getIdPeriodo());
    }

    @Override
    public int getItemCount() {
        return listaMateria.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        if(listener!=null)
        {
            listener.onClick(view);
        }
    }

    public class MateriaViewHolder extends RecyclerView.ViewHolder {
        TextView lblMateriaView;
        TextView lblAulaView;
        TextView lblPeriodoView;
        public MateriaViewHolder(@NonNull View itemView) {
            super(itemView);
            lblMateriaView = itemView.findViewById(R.id.lblNombreMateria);
            lblAulaView = itemView.findViewById(R.id.lblAulaMateria);
            lblPeriodoView = itemView.findViewById(R.id.lblPeriodoMateria);
        }
    }
}

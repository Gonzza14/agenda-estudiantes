package sv.ues.fia.eisi.agendaestudiantil.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.ui.examen.NotaExamenViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.materia.MateriaViewModel;

public class ListaMateriasNotasAdapter extends RecyclerView.Adapter<ListaMateriasNotasAdapter.MateriasNotasViewHolder>{

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<MateriaViewModel> listaMateria;
    private BD helper;

    public ListaMateriasNotasAdapter (ArrayList<MateriaViewModel> listaMateria){
        this.listaMateria = listaMateria;
    }
    @NonNull
    @Override
    public ListaMateriasNotasAdapter.MateriasNotasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_materias_notas, null, false);
        return new MateriasNotasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaMateriasNotasAdapter.MateriasNotasViewHolder holder, int position) {
        holder.lblMateriasNotas.setText(listaMateria.get(position).getNombreMateria());


        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.listaMateriasNotas.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        float valor, suma = 0, multiplicando = 0, multiplicador=0 ;
        ArrayList<NotaExamenViewModel> listaNotas = helper.mostrarNotasExamenesPorMateria(listaMateria.get(position).getIdMateria());
        for (NotaExamenViewModel nota: listaNotas){
            multiplicando = nota.getCalificacion();
            multiplicador = (float)nota.getPorcentaje()/100;
            valor = multiplicando*multiplicador;
            suma += valor;
        }
        listaMateria.get(position).setSubItemList(listaNotas);
        layoutManager.setInitialPrefetchItemCount(listaMateria.get(position).getSubItemList().size());

        ListaExamenNotasAdapter adapter = new ListaExamenNotasAdapter(listaMateria.get(position).getSubItemList());
        holder.listaMateriasNotas.setLayoutManager(layoutManager);
        holder.listaMateriasNotas.setAdapter(adapter);
        holder.listaMateriasNotas.setRecycledViewPool(viewPool);

        holder.lblPromedio.setText("Promedio: " + suma);
    }

    @Override
    public int getItemCount() {
        return listaMateria.size();
    }

    public class MateriasNotasViewHolder extends RecyclerView.ViewHolder {
        TextView lblMateriasNotas, lblPromedio;
        RecyclerView listaMateriasNotas;
        public MateriasNotasViewHolder(@NonNull View itemView) {
            super(itemView);
            helper = new BD(itemView.getContext());
            lblMateriasNotas = itemView.findViewById(R.id.lblMateriaNotas);
            lblPromedio = itemView.findViewById(R.id.lblPromedio);
            listaMateriasNotas = itemView.findViewById(R.id.listaMateriasNotas);
        }
    }
}

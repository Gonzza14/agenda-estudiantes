package sv.ues.fia.eisi.agendaestudiantil.adaptadores;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.customview.DialogCustomViewExtKt;

import java.util.ArrayList;
import java.util.Collections;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.custom.EditarPeriodoView;
import sv.ues.fia.eisi.agendaestudiantil.ui.periodo.PeriodoViewModel;

public class ListaPeriodoAdapter extends RecyclerView.Adapter<ListaPeriodoAdapter.PeriodoViewHolder> implements View.OnClickListener {

    private ArrayList<PeriodoViewModel> listaPeriodo;
    View.OnClickListener listener;
    /*private PeriodoViewModel periodo;
    private RecyclerView lista;
    private EditText txtPeriodo, txtInicio, txtFin;
    private BD helper;
    private int id = 0;*/

    public ListaPeriodoAdapter(ArrayList<PeriodoViewModel> listaPeriodo) {
        this.listaPeriodo = listaPeriodo;
    }

    @NonNull
    @Override
    public PeriodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_periodo, null, false);
        view.setOnClickListener(this);
        return new PeriodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeriodoViewHolder holder, int position) {
        holder.lblPeriodoView.setText(listaPeriodo.get(position).getTituloPeriodo());
        holder.lblFechaView.setText(listaPeriodo.get(position).getInicioPeriodo() + " hasta " + listaPeriodo.get(position).getFinPeriodo());

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if(listener!=null)
        {
            listener.onClick(v);
        }

    }

    @Override
    public int getItemCount() {
        return listaPeriodo.size();
    }

    public void updateItem(PeriodoViewModel periodo, int position){
        listaPeriodo.set(position, periodo);
        notifyItemChanged(position);
        Collections.sort(listaPeriodo, PeriodoViewModel.dateComparator);
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        listaPeriodo.remove(position);
        notifyItemRemoved(position);
        Collections.sort(listaPeriodo, PeriodoViewModel.dateComparator);
        notifyDataSetChanged();
    }


    public class PeriodoViewHolder extends RecyclerView.ViewHolder {
        TextView lblPeriodoView;
        TextView lblFechaView;
        public PeriodoViewHolder(@NonNull View itemView) {
            super(itemView);
            lblPeriodoView = itemView.findViewById(R.id.lblPeriodoView);
            lblFechaView = itemView.findViewById(R.id.lblFechaView);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    id = listaPeriodo.get(getAdapterPosition()).getIdPeriodo();
                    showCustomViewDialog(view, id);
                }
            });*/
        }
    }

    /*private void showCustomViewDialog(View view, int id) {

        EditarPeriodoView customView = new EditarPeriodoView(view.getContext());
        helper = new BD(view.getContext());


        txtPeriodo = customView.findViewById(R.id.txtTituloPeriodoVIew);
        txtInicio = customView.findViewById(R.id.txtInicioPeriodoView);
        txtFin = customView.findViewById(R.id.txtFinPeriodoView);

        periodo = new PeriodoViewModel();
        periodo = helper.verPeriodo(id);

        if (periodo !=null){
            txtPeriodo.setText(periodo.getTituloPeriodo());
            txtInicio.setText(periodo.getInicioPeriodo());
            txtFin.setText(periodo.getFinPeriodo());
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
        dialog.setView(customView);
        dialog.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                periodo.setTituloPeriodo(txtPeriodo.getText().toString());
                periodo.setInicioPeriodo(txtInicio.getText().toString());
                periodo.setFinPeriodo(txtFin.getText().toString());

                helper.abrir();
                String estado = helper.actualizar(periodo);
                helper.cerrar();


                Toast.makeText(view.getContext(),estado,Toast.LENGTH_SHORT).show();



            }
        });
        dialog.show();
        MaterialDialog.Builder dialog = new MaterialDialog.Builder(view.getContext());
        DialogCustomViewExtKt.customView(dialog, null, customView, false, false, true, true);
        dialog.positiveButton(null, "Editar", new );
        dialog.show();
    }

    public void actualizarAdapter(){

    }*/
}

package sv.ues.fia.eisi.agendaestudiantil.adaptadores;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.ui.profesor.ProfesorViewModel;

public class ListaProfesoresAdapter extends RecyclerView.Adapter<ListaProfesoresAdapter.ProfesorViewHolder> {

    private ArrayList<ProfesorViewModel> listaProfesor;

    public ListaProfesoresAdapter(ArrayList<ProfesorViewModel> listaProfesor){
        this.listaProfesor = listaProfesor;
    }

    @NonNull
    @Override
    public ProfesorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_profesor, null, false);
        return new ProfesorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfesorViewHolder holder, int position) {
        if (listaProfesor.get(position).getImagenProfesor() != null)
            Picasso.get().load(Uri.parse(listaProfesor.get(position).getImagenProfesor())).fit().into(holder.imgProfesorView);
        //holder.imgProfesorView.setImageURI(Uri.parse(listaProfesor.get(position).getImagenProfesor()));
        holder.lblNombreView.setText(listaProfesor.get(position).getNombreProfesor() + " " + listaProfesor.get(position).getApellidoProfesor());
    }

    @Override
    public int getItemCount() {
        return listaProfesor.size();
    }

    public class ProfesorViewHolder extends RecyclerView.ViewHolder {
        TextView lblNombreView;
        CircleImageView imgProfesorView;
        public ProfesorViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNombreView = itemView.findViewById(R.id.lblNombreView);
            imgProfesorView = itemView.findViewById(R.id.imgProfesorView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", listaProfesor.get(getAdapterPosition()).getIdProfesor());
                    Navigation.findNavController(view).navigate(R.id.nav_ver_profesor, bundle);
                    /*Context context = view.getContext();
                    Intent intent = new Intent(context, DetalleProfesorActivity.class);
                    intent.putExtra("ID", listaProfesor.get(getAdapterPosition()).getIdProfesor());
                    context.startActivity(intent);*/
                }
            });
        }
    }
}

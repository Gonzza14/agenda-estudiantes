package sv.ues.fia.eisi.agendaestudiantil.ui.periodo;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaPeriodoAdapter;
import sv.ues.fia.eisi.agendaestudiantil.adaptadores.ListaProfesoresAdapter;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.custom.EditarPeriodoView;

public class PeriodoFragment extends Fragment {

    private PeriodoViewModel periodo;
    private RecyclerView listaPeriodos;
    private FloatingActionButton btnAgregarPeriodo;
    private Button btnEditar, btnEliminar, btnCancelar;
    private ListaPeriodoAdapter adapter;
    private EditText txtPeriodo, txtInicio, txtFin;
    private BD helper;
    ArrayList<PeriodoViewModel> listaArrayPeriodos;
    private int id = 0, position = 0;


    public static PeriodoFragment newInstance() {
        return new PeriodoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_periodo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaPeriodos = view.findViewById(R.id.listaPeriodos);
        listaPeriodos.setLayoutManager(new LinearLayoutManager(view.getContext()));

        helper = new BD(view.getContext());

        listaArrayPeriodos = new ArrayList<>();
        listaArrayPeriodos = helper.mostrarPeriodos();

        adapter = new ListaPeriodoAdapter(listaArrayPeriodos);
        listaPeriodos.setAdapter(adapter);

        btnAgregarPeriodo = view.findViewById(R.id.btnAgregarPeriodo);
        btnAgregarPeriodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_agregar_periodo);
            }
        });

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = helper.mostrarPeriodos().get(listaPeriodos.getChildAdapterPosition(view)).getIdPeriodo();
                position = listaPeriodos.getChildAdapterPosition(view);
                showCustomViewDialog(view, id, position);
            }
        });
    }

    private void showCustomViewDialog(View view, int id, int position) {
        EditarPeriodoView customView = new EditarPeriodoView(view.getContext());
        helper = new BD(view.getContext());

        txtPeriodo = customView.findViewById(R.id.txtTituloPeriodoVIew);
        txtInicio = customView.findViewById(R.id.txtInicioPeriodoView);
        txtFin = customView.findViewById(R.id.txtFinPeriodoView);
        btnEditar = customView.findViewById(R.id.btnEditarPeriodo);
        btnEliminar = customView.findViewById(R.id.btnEliminarPeriodo);
        btnCancelar = customView.findViewById(R.id.btnCancelar);


        periodo = new PeriodoViewModel();
        periodo = helper.verPeriodo(id);

        if (periodo !=null){
            txtPeriodo.setText(periodo.getTituloPeriodo());
            txtInicio.setText(periodo.getInicioPeriodo());
            txtFin.setText(periodo.getFinPeriodo());
        }
        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setView(customView);
        alertDialog = builder.create();
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                periodo.setTituloPeriodo(txtPeriodo.getText().toString());
                periodo.setInicioPeriodo(txtInicio.getText().toString());
                periodo.setFinPeriodo(txtFin.getText().toString());

                helper.abrir();
                String estado = helper.actualizar(periodo);
                helper.cerrar();

                adapter.updateItem(periodo, position);
                Toast.makeText(view.getContext(),estado,Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje;
                periodo.setIdPeriodo(id);

                helper.abrir();
                mensaje = helper.eliminar(periodo);
                helper.cerrar();

                adapter.deleteItem(position);
                Toast.makeText(view.getContext(), mensaje, Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
        /*MaterialDialog.Builder dialog = new MaterialDialog.Builder(view.getContext());
        DialogCustomViewExtKt.customView(dialog, null, customView, false, false, true, true);
        dialog.positiveButton(null, "Editar", new );
        dialog.show();*/
    }
}
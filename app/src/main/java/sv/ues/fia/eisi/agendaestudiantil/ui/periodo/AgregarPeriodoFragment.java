package sv.ues.fia.eisi.agendaestudiantil.ui.periodo;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.clases.DatePickerFragment;


public class AgregarPeriodoFragment extends Fragment {

    private EditText txtPeriodo, txtInicio, txtFin;
    private FloatingActionButton btnAgregarPeriodo;
    private BD helper;
    private PeriodoViewModel periodo;

    public static Date ParseFecha(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }

    public AgregarPeriodoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        periodo = new ViewModelProvider(this).get(PeriodoViewModel.class);
        return inflater.inflate(R.layout.fragment_agregar_periodo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtPeriodo = view.findViewById(R.id.txtTitulo);
        txtInicio = view.findViewById(R.id.txtFechaInicio);
        txtInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(txtInicio);
            }
        });

        helper = new BD(view.getContext());
        txtFin = view.findViewById(R.id.txtFechaFin);
        txtFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(txtFin);
            }
        });
        btnAgregarPeriodo = view.findViewById(R.id.btnGuardarPeriodo);
        btnAgregarPeriodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tituloPeriodo = txtPeriodo.getText().toString();
                String inicioPeriodo = txtInicio.getText().toString();
                String finPeriodo = txtFin.getText().toString();

                String mensaje;

                periodo.setTituloPeriodo(tituloPeriodo);
                periodo.setInicioPeriodo(inicioPeriodo);
                periodo.setFinPeriodo(finPeriodo);

                helper.abrir();
                mensaje = helper.insertar(periodo);
                helper.cerrar();
                Toast.makeText(getActivity().getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();

                txtPeriodo.setText("");
                txtInicio.setText("");
                txtFin.setText("");

            }
        });
    }

    private void showDatePickerDialog(final EditText editText){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = year + "-" + twoDigits(month+1) + "-" + twoDigits(day);
                editText.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}
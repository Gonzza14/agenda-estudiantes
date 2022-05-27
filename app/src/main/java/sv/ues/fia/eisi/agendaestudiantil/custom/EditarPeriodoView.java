package sv.ues.fia.eisi.agendaestudiantil.custom;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import sv.ues.fia.eisi.agendaestudiantil.InicioActivity;
import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.clases.DatePickerFragment;
import sv.ues.fia.eisi.agendaestudiantil.ui.periodo.PeriodoViewModel;

public class EditarPeriodoView extends RelativeLayout {

    private EditText txtPeriodo, txtInicio, txtFin;
    private BD helper;
    private PeriodoViewModel periodo;




    public EditarPeriodoView(Context context) {
        super(context);
        init(context);
    }

    public EditarPeriodoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EditarPeriodoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_editar_periodo, this);
        txtPeriodo = findViewById(R.id.txtTituloPeriodoVIew);
        txtInicio = findViewById(R.id.txtInicioPeriodoView);
        txtInicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showDatePickerDialog(txtInicio, context);
            }
        });
        txtFin = findViewById(R.id.txtFinPeriodoView);
        txtFin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(txtFin, context);
            }
        });

    }

    private void showDatePickerDialog(final EditText editText, Context context){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = year + "-" + twoDigits(month+1) + "-" + twoDigits(day);
                editText.setText(selectedDate);
            }
        });

        newFragment.show(((FragmentActivity)context).getSupportFragmentManager(), "datePicker");
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }



}

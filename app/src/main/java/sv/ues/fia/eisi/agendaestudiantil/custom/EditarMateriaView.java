package sv.ues.fia.eisi.agendaestudiantil.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import sv.ues.fia.eisi.agendaestudiantil.R;

public class EditarMateriaView extends RelativeLayout {

    private EditText txtNombreMateria, txtAulaMateria;
    private Spinner spProfesorMateria, spPeriodoMateria;


    public EditarMateriaView(Context context) {
        super(context);
        init(context);
    }

    public EditarMateriaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EditarMateriaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.view_editar_materia, this);
        txtNombreMateria = findViewById(R.id.txtNombreMateriaView);
        txtAulaMateria = findViewById(R.id.txtAulaMateriaView);
        spProfesorMateria = findViewById(R.id.spProfesorMateriaView);
        spPeriodoMateria = findViewById(R.id.spPeriodoMateriaView);
    }
}

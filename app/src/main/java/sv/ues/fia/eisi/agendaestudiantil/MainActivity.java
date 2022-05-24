package sv.ues.fia.eisi.agendaestudiantil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    BD helper;
    Fragment fragmentCrearAgenda;
    TextView lblBievenida;
    Button btnCrearAgenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new BD(this);
        if (helper.verificarExistenciaDeAgenda()) {
            Intent inte = new Intent(this, sv.ues.fia.eisi.agendaestudiantil.Inicio.class);
            startActivity(inte);
            helper.cerrar();
        }
        fragmentCrearAgenda = new CrearAgendaFragment();

        lblBievenida = (TextView) findViewById(R.id.lblBienvenida);
        btnCrearAgenda = (Button) findViewById(R.id.btnCrearAgenda);

        btnCrearAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(R.id.crearAgendaFragment, fragmentCrearAgenda).commit();
                lblBievenida.setVisibility(View.GONE);
                btnCrearAgenda.setVisibility(View.GONE);
            }
        });
    }
}
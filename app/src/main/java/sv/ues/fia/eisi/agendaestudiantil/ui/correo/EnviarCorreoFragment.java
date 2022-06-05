package sv.ues.fia.eisi.agendaestudiantil.ui.correo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.Correo;

public class EnviarCorreoFragment extends Fragment {


    private EditText txtCorreo, txtAsunto, txtMensaje;
    private FloatingActionButton btnEnviar;
    private String correoRemitente;
    public EnviarCorreoFragment() {
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
        return inflater.inflate(R.layout.fragment_enviar_correo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        correoRemitente = (String) getArguments().getString("CORREO");

        txtCorreo = view.findViewById(R.id.txtCorreo);
        txtAsunto = view.findViewById(R.id.txtAsunto);
        txtMensaje = view.findViewById(R.id.txtMensaje);
        btnEnviar = view.findViewById(R.id.btnEnviar);

        txtCorreo.setText(correoRemitente);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mCorreo = txtCorreo.getText().toString();
                String mAsunto = txtAsunto.getText().toString();
                String mMensaje = txtMensaje.getText().toString();

                Correo correo = new Correo(view.getContext(),mCorreo,mAsunto,mMensaje);

                correo.execute();
                Navigation.findNavController(view).popBackStack();
                Toast.makeText(getContext(),"Correo enviado", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
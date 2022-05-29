package sv.ues.fia.eisi.agendaestudiantil.ui.horario;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tlaabs.timetableview.TimetableView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sv.ues.fia.eisi.agendaestudiantil.R;

public class HorarioFragment extends Fragment {

    private HorarioViewModel mViewModel;
    private FloatingActionButton btnAgregarClase;
    private TimetableView timetable;

    public static HorarioFragment newInstance() {
        return new HorarioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_horario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timetable = view.findViewById(R.id.timetable);
        btnAgregarClase = view.findViewById(R.id.btnAgregarClase);
        btnAgregarClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_agregar_clase);
            }
        });
    }
}
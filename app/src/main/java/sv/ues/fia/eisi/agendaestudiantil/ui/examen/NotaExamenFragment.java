package sv.ues.fia.eisi.agendaestudiantil.ui.examen;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sv.ues.fia.eisi.agendaestudiantil.R;

public class NotaExamenFragment extends Fragment {

    private NotaExamenViewModel mViewModel;

    public static NotaExamenFragment newInstance() {
        return new NotaExamenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nota_examen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NotaExamenViewModel.class);
        // TODO: Use the ViewModel
    }

}
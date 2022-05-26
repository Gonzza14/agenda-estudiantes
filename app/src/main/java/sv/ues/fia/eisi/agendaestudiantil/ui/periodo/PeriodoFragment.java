package sv.ues.fia.eisi.agendaestudiantil.ui.periodo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sv.ues.fia.eisi.agendaestudiantil.R;

public class PeriodoFragment extends Fragment {

    private PeriodoViewModel mViewModel;

    public static PeriodoFragment newInstance() {
        return new PeriodoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_periodo, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PeriodoViewModel.class);
        // TODO: Use the ViewModel
    }

}
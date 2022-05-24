package sv.ues.fia.eisi.agendaestudiantil.ui.recordatorio;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sv.ues.fia.eisi.agendaestudiantil.R;

public class RecordatorioFragment extends Fragment {

    private RecordatorioViewModel mViewModel;

    public static RecordatorioFragment newInstance() {
        return new RecordatorioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recordatorio, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecordatorioViewModel.class);
        // TODO: Use the ViewModel
    }

}
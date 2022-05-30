package sv.ues.fia.eisi.agendaestudiantil.ui.examen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import sv.ues.fia.eisi.agendaestudiantil.databinding.FragmentExamenBinding;

public class ExamenFragment extends Fragment {

    private FragmentExamenBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExamenViewModel slideshowViewModel =
                new ViewModelProvider(this).get(ExamenViewModel.class);

        binding = FragmentExamenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
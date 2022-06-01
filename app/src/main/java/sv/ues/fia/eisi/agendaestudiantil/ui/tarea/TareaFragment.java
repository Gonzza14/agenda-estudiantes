package sv.ues.fia.eisi.agendaestudiantil.ui.tarea;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import sv.ues.fia.eisi.agendaestudiantil.databinding.FragmentTareaBinding;

public class TareaFragment extends Fragment {

    private FragmentTareaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TareaViewModel galleryViewModel =
                new ViewModelProvider(this).get(TareaViewModel.class);

        binding = FragmentTareaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
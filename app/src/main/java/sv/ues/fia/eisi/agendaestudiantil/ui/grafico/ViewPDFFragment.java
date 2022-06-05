package sv.ues.fia.eisi.agendaestudiantil.ui.grafico;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import sv.ues.fia.eisi.agendaestudiantil.R;


public class ViewPDFFragment extends Fragment {

    private PDFView pdfView;
    private String pdfPath;
    private File file;
    public ViewPDFFragment() {
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
        return inflater.inflate(R.layout.fragment_view_p_d_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pdfView = view.findViewById(R.id.pdfView);
        pdfPath = (String) getArguments().getString("PATH");
        if (pdfPath !=null){
            file = new File(pdfPath);
        }

        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .load();
    }
}
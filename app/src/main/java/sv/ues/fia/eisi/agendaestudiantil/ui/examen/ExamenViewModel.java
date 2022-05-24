package sv.ues.fia.eisi.agendaestudiantil.ui.examen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExamenViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ExamenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
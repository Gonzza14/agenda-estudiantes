package sv.ues.fia.eisi.agendaestudiantil.ui.tarea;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TareaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TareaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hola");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
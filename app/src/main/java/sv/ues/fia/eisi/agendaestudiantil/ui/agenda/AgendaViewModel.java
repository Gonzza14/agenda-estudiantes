package sv.ues.fia.eisi.agendaestudiantil.ui.agenda;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AgendaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AgendaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Agenda");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
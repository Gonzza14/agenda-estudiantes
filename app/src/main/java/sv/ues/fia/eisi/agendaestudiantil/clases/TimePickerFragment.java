package sv.ues.fia.eisi.agendaestudiantil.clases;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener listener;

    public static TimePickerFragment newInstance(TimePickerDialog.OnTimeSetListener listener){
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(TimePickerDialog.OnTimeSetListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);


        return new TimePickerDialog(getActivity(),listener,hora,minuto, DateFormat.is24HourFormat(getActivity()));
    }


}

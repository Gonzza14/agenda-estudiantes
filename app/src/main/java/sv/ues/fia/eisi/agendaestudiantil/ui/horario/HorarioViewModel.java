package sv.ues.fia.eisi.agendaestudiantil.ui.horario;

import androidx.lifecycle.ViewModel;

public class HorarioViewModel extends ViewModel {
    private int idHorario;
    private int nombreHorario;

    public HorarioViewModel() {
    }

    public HorarioViewModel(int idHorario, int nombreHorario) {
        this.idHorario = idHorario;
        this.nombreHorario = nombreHorario;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getNombreHorario() {
        return nombreHorario;
    }

    public void setNombreHorario(int nombreHorario) {
        this.nombreHorario = nombreHorario;
    }
}
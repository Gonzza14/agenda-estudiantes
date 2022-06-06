package sv.ues.fia.eisi.agendaestudiantil.ui.horario;

import androidx.lifecycle.ViewModel;

public class HorarioViewModel extends ViewModel {
    private int idHorario;
    private String nombreHorario;

    public HorarioViewModel() {
    }

    public HorarioViewModel(int idHorario, String nombreHorario) {
        this.idHorario = idHorario;
        this.nombreHorario = nombreHorario;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getNombreHorario() {
        return nombreHorario;
    }

    public void setNombreHorario(String nombreHorario) {
        this.nombreHorario = nombreHorario;
    }
}
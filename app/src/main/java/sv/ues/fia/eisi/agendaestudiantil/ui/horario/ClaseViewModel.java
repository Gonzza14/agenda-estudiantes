package sv.ues.fia.eisi.agendaestudiantil.ui.horario;

import androidx.lifecycle.ViewModel;

public class ClaseViewModel extends ViewModel {
    private int idClase;
    private int idHorario;
    private int idMateria;
    private int idProfesor;
    private String aulaClase;
    private String diaClase;
    private String inicioClase;
    private String finClase;
    private String descripcionClase;

    public ClaseViewModel() {
    }

    public ClaseViewModel(int idClase, int idHorario, int idMateria, int idProfesor, String aulaClase, String diaClase, String inicioClase, String finClase, String descripcionClase) {
        this.idClase = idClase;
        this.idHorario = idHorario;
        this.idMateria = idMateria;
        this.idProfesor = idProfesor;
        this.aulaClase = aulaClase;
        this.diaClase = diaClase;
        this.inicioClase = inicioClase;
        this.finClase = finClase;
        this.descripcionClase = descripcionClase;
    }

    public int getIdClase() {
        return idClase;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getAulaClase() {
        return aulaClase;
    }

    public void setAulaClase(String aulaClase) {
        this.aulaClase = aulaClase;
    }

    public String getDiaClase() {
        return diaClase;
    }

    public void setDiaClase(String diaClase) {
        this.diaClase = diaClase;
    }

    public String getInicioClase() {
        return inicioClase;
    }

    public void setInicioClase(String inicioClase) {
        this.inicioClase = inicioClase;
    }

    public String getFinClase() {
        return finClase;
    }

    public void setFinClase(String finClase) {
        this.finClase = finClase;
    }

    public String getDescripcionClase() {
        return descripcionClase;
    }

    public void setDescripcionClase(String descripcionClase) {
        this.descripcionClase = descripcionClase;
    }
}
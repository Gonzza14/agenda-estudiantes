package sv.ues.fia.eisi.agendaestudiantil.ui.materia;

import androidx.lifecycle.ViewModel;

public class MateriaViewModel extends ViewModel {
    private int idMateria;
    private int idProfesor;
    private int idPeriodo;
    private String nombreMateria;
    private String aulaMateria;


    public MateriaViewModel() {
    }

    public MateriaViewModel(int idMateria, int idProfesor, int idPeriodo, String nombreMateria, String aulaMateria) {
        this.idMateria = idMateria;
        this.idProfesor = idProfesor;
        this.idPeriodo = idPeriodo;
        this.nombreMateria = nombreMateria;
        this.aulaMateria = aulaMateria;
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

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getAulaMateria() {
        return aulaMateria;
    }

    public void setAulaMateria(String aulaMateria) {
        this.aulaMateria = aulaMateria;
    }
}
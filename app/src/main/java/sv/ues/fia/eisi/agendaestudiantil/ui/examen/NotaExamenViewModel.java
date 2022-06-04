package sv.ues.fia.eisi.agendaestudiantil.ui.examen;

import androidx.lifecycle.ViewModel;

public class NotaExamenViewModel extends ViewModel {
    private int idNotaExamen;
    private int idExamen;
    private int idMateria;
    private float calificacion;
    private int porcentaje;
    private String descripcionExamen;

    public NotaExamenViewModel() {
    }

    public NotaExamenViewModel(int idNotaExamen, int idExamen, int idMateria, float calificacion, int porcentaje, String descripcionExamen) {
        this.idNotaExamen = idNotaExamen;
        this.idExamen = idExamen;
        this.idMateria = idMateria;
        this.calificacion = calificacion;
        this.porcentaje = porcentaje;
        this.descripcionExamen = descripcionExamen;
    }

    public int getIdNotaExamen() {
        return idNotaExamen;
    }

    public void setIdNotaExamen(int idNotaExamen) {
        this.idNotaExamen = idNotaExamen;
    }

    public int getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
    }

    public int getIdMateria() {
        return idMateria;
    }
    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getDescripcionExamen() {
        return descripcionExamen;
    }

    public void setDescripcionExamen(String descripcionExamen) {
        this.descripcionExamen = descripcionExamen;
    }
}
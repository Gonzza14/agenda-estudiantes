package sv.ues.fia.eisi.agendaestudiantil;

import java.sql.Date;

public class NotaExamen {
    private int idNotaExamen;
    private int idExamen;
    private int idPeriodo;
    private int calificacion;
    private Date fechaExamen;
    private int porcentaje;
    private String descripcionExamen;

    public NotaExamen() {
    }

    public NotaExamen(int idNotaExamen, int idExamen, int idPeriodo, int calificacion, Date fechaExamen, int porcentaje, String descripcionExamen) {
        this.idNotaExamen = idNotaExamen;
        this.idExamen = idExamen;
        this.idPeriodo = idPeriodo;
        this.calificacion = calificacion;
        this.fechaExamen = fechaExamen;
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

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public Date getFechaExamen() {
        return fechaExamen;
    }

    public void setFechaExamen(Date fechaExamen) {
        this.fechaExamen = fechaExamen;
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

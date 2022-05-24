package sv.ues.fia.eisi.agendaestudiantil.clases;

import java.sql.Date;
import java.sql.Time;

public class Examen {
    private int idExamen;
    private int idAgenda;
    private int idTipoExamen;
    private Date fechaExamen;
    private Time horaExamen;
    private String descripcionExamen;
    private String aulaExamen;

    public Examen() {
    }

    public Examen(int idExamen, int idAgenda, int idTipoExamen, Date fechaExamen, Time horaExamen, String descripcionExamen, String aulaExamen) {
        this.idExamen = idExamen;
        this.idAgenda = idAgenda;
        this.idTipoExamen = idTipoExamen;
        this.fechaExamen = fechaExamen;
        this.horaExamen = horaExamen;
        this.descripcionExamen = descripcionExamen;
        this.aulaExamen = aulaExamen;
    }

    public int getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
    }

    public int getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(int idAgenda) {
        this.idAgenda = idAgenda;
    }

    public int getIdTipoExamen() {
        return idTipoExamen;
    }

    public void setIdTipoExamen(int idTipoExamen) {
        this.idTipoExamen = idTipoExamen;
    }

    public Date getFechaExamen() {
        return fechaExamen;
    }

    public void setFechaExamen(Date fechaExamen) {
        this.fechaExamen = fechaExamen;
    }

    public Time getHoraExamen() {
        return horaExamen;
    }

    public void setHoraExamen(Time horaExamen) {
        this.horaExamen = horaExamen;
    }

    public String getDescripcionExamen() {
        return descripcionExamen;
    }

    public void setDescripcionExamen(String descripcionExamen) {
        this.descripcionExamen = descripcionExamen;
    }

    public String getAulaExamen() {
        return aulaExamen;
    }

    public void setAulaExamen(String aulaExamen) {
        this.aulaExamen = aulaExamen;
    }
}

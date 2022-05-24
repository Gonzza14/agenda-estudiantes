package sv.ues.fia.eisi.agendaestudiantil;

import java.sql.Date;
import java.sql.Time;

public class Estudio {
    private int idEstudio;
    private Date diaEstudio;
    private Time inicioEstudio;
    private Time finEstudio;
    private String descripcionEstudio;

    public Estudio() {
    }

    public Estudio(int idEstudio, Date diaEstudio, Time inicioEstudio, Time finEstudio, String descripcionEstudio) {
        this.idEstudio = idEstudio;
        this.diaEstudio = diaEstudio;
        this.inicioEstudio = inicioEstudio;
        this.finEstudio = finEstudio;
        this.descripcionEstudio = descripcionEstudio;
    }

    public int getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(int idEstudio) {
        this.idEstudio = idEstudio;
    }

    public Date getDiaEstudio() {
        return diaEstudio;
    }

    public void setDiaEstudio(Date diaEstudio) {
        this.diaEstudio = diaEstudio;
    }

    public Time getInicioEstudio() {
        return inicioEstudio;
    }

    public void setInicioEstudio(Time inicioEstudio) {
        this.inicioEstudio = inicioEstudio;
    }

    public Time getFinEstudio() {
        return finEstudio;
    }

    public void setFinEstudio(Time finEstudio) {
        this.finEstudio = finEstudio;
    }

    public String getDescripcionEstudio() {
        return descripcionEstudio;
    }

    public void setDescripcionEstudio(String descripcionEstudio) {
        this.descripcionEstudio = descripcionEstudio;
    }
}

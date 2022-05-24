package sv.ues.fia.eisi.agendaestudiantil;

import java.sql.Date;
import java.sql.Time;

public class Clase {
    private int idClase;
    private int idHorario;
    private int idMateria;
    private Date diaClase;
    private Time inicioClase;
    private Time finClase;
    private String descripcionClase;

    public Clase() {
    }

    public Clase(int idClase, int idHorario, int idMateria, Date diaClase, Time inicioClase, Time finClase, String descripcionClase) {
        this.idClase = idClase;
        this.idHorario = idHorario;
        this.idMateria = idMateria;
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

    public Date getDiaClase() {
        return diaClase;
    }

    public void setDiaClase(Date diaClase) {
        this.diaClase = diaClase;
    }

    public Time getInicioClase() {
        return inicioClase;
    }

    public void setInicioClase(Time inicioClase) {
        this.inicioClase = inicioClase;
    }

    public Time getFinClase() {
        return finClase;
    }

    public void setFinClase(Time finClase) {
        this.finClase = finClase;
    }

    public String getDescripcionClase() {
        return descripcionClase;
    }

    public void setDescripcionClase(String descripcionClase) {
        this.descripcionClase = descripcionClase;
    }
}

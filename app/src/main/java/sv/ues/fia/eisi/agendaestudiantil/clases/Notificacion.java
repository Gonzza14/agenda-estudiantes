package sv.ues.fia.eisi.agendaestudiantil.clases;

import java.sql.Time;

public class Notificacion {
    private int idNotificacion;
    private int idAgenda;
    private String diaNotificacion;
    private boolean conNotificacion;
    private Time horaNotificacion;

    public Notificacion() {
    }

    public Notificacion(int idNotificacion, int idAgenda, String diaNotificacion, boolean conNotificacion, Time horaNotificacion) {
        this.idNotificacion = idNotificacion;
        this.idAgenda = idAgenda;
        this.diaNotificacion = diaNotificacion;
        this.conNotificacion = conNotificacion;
        this.horaNotificacion = horaNotificacion;
    }

    public int getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public int getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(int idAgenda) {
        this.idAgenda = idAgenda;
    }

    public String getDiaNotificacion() {
        return diaNotificacion;
    }

    public void setDiaNotificacion(String diaNotificacion) {
        this.diaNotificacion = diaNotificacion;
    }

    public boolean isConNotificacion() {
        return conNotificacion;
    }

    public void setConNotificacion(boolean conNotificacion) {
        this.conNotificacion = conNotificacion;
    }

    public Time getHoraNotificacion() {
        return horaNotificacion;
    }

    public void setHoraNotificacion(Time horaNotificacion) {
        this.horaNotificacion = horaNotificacion;
    }
}

package sv.ues.fia.eisi.agendaestudiantil.clases;

public class Horario {
    private int idHorario;
    private boolean notificacionesHorario;
    private int notificacionClase;

    public Horario() {
    }

    public Horario(int idHorario, boolean notificacionesHorario, int notificacionClase) {
        this.idHorario = idHorario;
        this.notificacionesHorario = notificacionesHorario;
        this.notificacionClase = notificacionClase;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public boolean isNotificacionesHorario() {
        return notificacionesHorario;
    }

    public void setNotificacionesHorario(boolean notificacionesHorario) {
        this.notificacionesHorario = notificacionesHorario;
    }

    public int getNotificacionClase() {
        return notificacionClase;
    }

    public void setNotificacionClase(int notificacionClase) {
        this.notificacionClase = notificacionClase;
    }
}

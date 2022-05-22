package sv.ues.fia.eisi.agendaestudiantil;

public class Agenda {
    private int idAgenda;
    private String nombreAgenda;
    private boolean notificacionAgenda;

    public Agenda() {
    }

    public Agenda(int idAgenda, String nombreAgenda, boolean notificacionAgenda) {
        this.idAgenda = idAgenda;
        this.nombreAgenda = nombreAgenda;
        this.notificacionAgenda = notificacionAgenda;
    }

    public int getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(int idAgenda) {
        this.idAgenda = idAgenda;
    }

    public String getNombreAgenda() {
        return nombreAgenda;
    }

    public void setNombreAgenda(String nombreAgenda) {
        this.nombreAgenda = nombreAgenda;
    }

    public boolean getNotificacionAgenda() {
        return notificacionAgenda;
    }

    public void setNotificacionAgenda(boolean notificacionAgenda) {
        this.notificacionAgenda = notificacionAgenda;
    }
}

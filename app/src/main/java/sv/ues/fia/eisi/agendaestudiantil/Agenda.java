package sv.ues.fia.eisi.agendaestudiantil;

public class Agenda {
    private int idAgenda;
    private String nombreAgenda;

    public Agenda() {
    }

    public Agenda(int idAgenda, String nombreAgenda) {
        this.idAgenda = idAgenda;
        this.nombreAgenda = nombreAgenda;
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
}

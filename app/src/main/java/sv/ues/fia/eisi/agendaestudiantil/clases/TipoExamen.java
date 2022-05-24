package sv.ues.fia.eisi.agendaestudiantil.clases;

public class TipoExamen {
    private int idTipoExamen;
    private String nombreTipoExamen;

    public TipoExamen() {
    }

    public TipoExamen(int idTipoExamen, String nombreTipoExamen) {
        this.idTipoExamen = idTipoExamen;
        this.nombreTipoExamen = nombreTipoExamen;
    }

    public int getIdTipoExamen() {
        return idTipoExamen;
    }

    public void setIdTipoExamen(int idTipoExamen) {
        this.idTipoExamen = idTipoExamen;
    }

    public String getNombreTipoExamen() {
        return nombreTipoExamen;
    }

    public void setNombreTipoExamen(String nombreTipoExamen) {
        this.nombreTipoExamen = nombreTipoExamen;
    }
}

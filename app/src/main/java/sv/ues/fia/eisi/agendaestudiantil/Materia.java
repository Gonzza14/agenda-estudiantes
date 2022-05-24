package sv.ues.fia.eisi.agendaestudiantil;

public class Materia {
    private int idMateria;
    private int idProfesor;
    private String nombreMateria;
    private String aulaMateria;

    public Materia() {
    }

    public Materia(int idMateria, int idProfesor, String nombreMateria, String aulaMateria) {
        this.idMateria = idMateria;
        this.idProfesor = idProfesor;
        this.nombreMateria = nombreMateria;
        this.aulaMateria = aulaMateria;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getAulaMateria() {
        return aulaMateria;
    }

    public void setAulaMateria(String aulaMateria) {
        this.aulaMateria = aulaMateria;
    }
}

package sv.ues.fia.eisi.agendaestudiantil.clases;

public class Profesor {
    private int idProfesor;
    private String nombreProfesor;
    private String apellidoProfesor;
    private String telefonoProfesor;
    private String correoProfesor;

    public Profesor() {
    }

    public Profesor(int idProfesor, String nombreProfesor, String apellidoProfesor, String telefonoProfesor, String correoProfesor) {
        this.idProfesor = idProfesor;
        this.nombreProfesor = nombreProfesor;
        this.apellidoProfesor = apellidoProfesor;
        this.telefonoProfesor = telefonoProfesor;
        this.correoProfesor = correoProfesor;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getApellidoProfesor() {
        return apellidoProfesor;
    }

    public void setApellidoProfesor(String apellidoProfesor) {
        this.apellidoProfesor = apellidoProfesor;
    }

    public String getTelefonoProfesor() {
        return telefonoProfesor;
    }

    public void setTelefonoProfesor(String telefonoProfesor) {
        this.telefonoProfesor = telefonoProfesor;
    }

    public String getCorreoProfesor() {
        return correoProfesor;
    }

    public void setCorreoProfesor(String correoProfesor) {
        this.correoProfesor = correoProfesor;
    }
}

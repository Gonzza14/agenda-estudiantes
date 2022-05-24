package sv.ues.fia.eisi.agendaestudiantil;

public class Archivo {
    private int idArchivo;
    private int idTarea;
    private int idProfesor;
    private int idExamen;
    private String nombreArchivo;
    private String direccionArchivo;

    public Archivo() {
    }

    public Archivo(int idArchivo, int idTarea, int idProfesor, int idExamen, String nombreArchivo, String direccionArchivo) {
        this.idArchivo = idArchivo;
        this.idTarea = idTarea;
        this.idProfesor = idProfesor;
        this.idExamen = idExamen;
        this.nombreArchivo = nombreArchivo;
        this.direccionArchivo = direccionArchivo;
    }

    public int getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(int idArchivo) {
        this.idArchivo = idArchivo;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public int getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getDireccionArchivo() {
        return direccionArchivo;
    }

    public void setDireccionArchivo(String direccionArchivo) {
        this.direccionArchivo = direccionArchivo;
    }
}

package sv.ues.fia.eisi.agendaestudiantil.clases;

import java.sql.Date;

public class Tarea {
    private int idTarea;
    private int idMateria;
    private int idAgenda;
    private String tituloTarea;
    private String descripcionTarea;
    private Date fechaTarea;
    private boolean finalizadaTarea;
    private boolean archivadaTarea;

    public Tarea() {
    }

    public Tarea(int idTarea, int idMateria, int idAgenda, String tituloTarea, String descripcionTarea, Date fechaTarea, boolean finalizadaTarea, boolean archivadaTarea) {
        this.idTarea = idTarea;
        this.idMateria = idMateria;
        this.idAgenda = idAgenda;
        this.tituloTarea = tituloTarea;
        this.descripcionTarea = descripcionTarea;
        this.fechaTarea = fechaTarea;
        this.finalizadaTarea = finalizadaTarea;
        this.archivadaTarea = archivadaTarea;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(int idAgenda) {
        this.idAgenda = idAgenda;
    }

    public String getTituloTarea() {
        return tituloTarea;
    }

    public void setTituloTarea(String tituloTarea) {
        this.tituloTarea = tituloTarea;
    }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
    }

    public Date getFechaTarea() {
        return fechaTarea;
    }

    public void setFechaTarea(Date fechaTarea) {
        this.fechaTarea = fechaTarea;
    }

    public boolean isFinalizadaTarea() {
        return finalizadaTarea;
    }

    public void setFinalizadaTarea(boolean finalizadaTarea) {
        this.finalizadaTarea = finalizadaTarea;
    }

    public boolean isArchivadaTarea() {
        return archivadaTarea;
    }

    public void setArchivadaTarea(boolean archivadaTarea) {
        this.archivadaTarea = archivadaTarea;
    }
}

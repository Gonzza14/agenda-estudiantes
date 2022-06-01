package sv.ues.fia.eisi.agendaestudiantil.ui.tarea;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Date;

public class TareaViewModel extends ViewModel {
    private int idTarea;
    private String nombre;
    private int idMateria;
    private int idAgenda;
    private String tituloTarea;
    private String descripcionTarea;
    private String horaTarea;
    private String fechaTarea;
    private int finalizadaTarea;
    private int archivadaTarea;

    public TareaViewModel() {
    }

    public TareaViewModel(int idTarea, String nombre, int idMateria, int idAgenda, String tituloTarea, String descripcionTarea, String horaTarea, String fechaTarea, int finalizadaTarea, int archivadaTarea) {
        this.idTarea = idTarea;
        this.nombre = nombre;
        this.idMateria = idMateria;
        this.idAgenda = idAgenda;
        this.tituloTarea = tituloTarea;
        this.descripcionTarea = descripcionTarea;
        this.horaTarea = horaTarea;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getHoraTarea() {
        return horaTarea;
    }

    public void setHoraTarea(String horaTarea) {
        this.horaTarea = horaTarea;
    }

    public String getFechaTarea() {
        return fechaTarea;
    }

    public void setFechaTarea(String fechaTarea) {
        this.fechaTarea = fechaTarea;
    }

    public int getFinalizadaTarea() {
        return finalizadaTarea;
    }

    public void setFinalizadaTarea(int finalizadaTarea) {
        this.finalizadaTarea = finalizadaTarea;
    }

    public int getArchivadaTarea() {
        return archivadaTarea;
    }

    public void setArchivadaTarea(int archivadaTarea) {
        this.archivadaTarea = archivadaTarea;
    }
}
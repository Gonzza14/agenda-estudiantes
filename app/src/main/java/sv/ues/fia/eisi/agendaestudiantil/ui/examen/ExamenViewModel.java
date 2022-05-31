package sv.ues.fia.eisi.agendaestudiantil.ui.examen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.clases.BD;

public class ExamenViewModel extends ViewModel {


    private int idExamen;
    private String nombreExamen;
    private int idAgenda;
    private int idMateria;
    private int idTipoExamen;
    private String fechaExamen;
    private String horaExamen;
    private String descripcionExamen;
    private String aulaExamen;

    public ExamenViewModel() {
    }

    public ExamenViewModel(int idExamen, String nombreExamen, int idAgenda, int idMateria, int idTipoExamen, String fechaExamen, String horaExamen, String descripcionExamen, String aulaExamen) {
        this.idExamen = idExamen;
        this.nombreExamen = nombreExamen;
        this.idAgenda = idAgenda;
        this.idMateria = idMateria;
        this.idTipoExamen = idTipoExamen;
        this.fechaExamen = fechaExamen;
        this.horaExamen = horaExamen;
        this.descripcionExamen = descripcionExamen;
        this.aulaExamen = aulaExamen;
    }

    public int getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
    }

    public String getNombreExamen() {
        return nombreExamen;
    }

    public void setNombreExamen(String nombreExamen) {
        this.nombreExamen = nombreExamen;
    }

    public int getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(int idAgenda) {
        this.idAgenda = idAgenda;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getIdTipoExamen() {
        return idTipoExamen;
    }

    public void setIdTipoExamen(int idTipoExamen) {
        this.idTipoExamen = idTipoExamen;
    }

    public String getFechaExamen() {
        return fechaExamen;
    }

    public void setFechaExamen(String fechaExamen) {
        this.fechaExamen = fechaExamen;
    }

    public String getHoraExamen() {
        return horaExamen;
    }

    public void setHoraExamen(String horaExamen) {
        this.horaExamen = horaExamen;
    }

    public String getDescripcionExamen() {
        return descripcionExamen;
    }

    public void setDescripcionExamen(String descripcionExamen) {
        this.descripcionExamen = descripcionExamen;
    }

    public String getAulaExamen() {
        return aulaExamen;
    }

    public void setAulaExamen(String aulaExamen) {
        this.aulaExamen = aulaExamen;
    }
}
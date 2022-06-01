package sv.ues.fia.eisi.agendaestudiantil.ui.recordatorio;

import androidx.lifecycle.ViewModel;


public class RecordatorioViewModel extends ViewModel {
    private int idRecordatorio;
    private String nombreRecordatorio;
    private int idAgenda;
    private String fecha;
    private String hora;
    private String descripcionRecordatorio;

    public RecordatorioViewModel() {
    }

    public RecordatorioViewModel(int idRecordatorio, String nombreRecordatorio, int idAgenda, String fecha, String hora, String descripcionRecordatorio) {
        this.idRecordatorio = idRecordatorio;
        this.nombreRecordatorio = nombreRecordatorio;
        this.idAgenda = idAgenda;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcionRecordatorio = descripcionRecordatorio;
    }

    public int getIdRecordatorio() {
        return idRecordatorio;
    }

    public void setIdRecordatorio(int idRecordatorio) {
        this.idRecordatorio = idRecordatorio;
    }

    public String getNombreRecordatorio() {
        return nombreRecordatorio;
    }

    public void setNombreRecordatorio(String nombreRecordatorio) {
        this.nombreRecordatorio = nombreRecordatorio;
    }

    public int getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(int idAgenda) {
        this.idAgenda = idAgenda;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcionRecordatorio() {
        return descripcionRecordatorio;
    }

    public void setDescripcionRecordatorio(String descripcionRecordatorio) {
        this.descripcionRecordatorio = descripcionRecordatorio;
    }
}
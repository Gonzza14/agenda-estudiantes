package sv.ues.fia.eisi.agendaestudiantil.clases;

import java.sql.Date;

public class Recordatorio {
    private int idRecordatorio;
    private Date fecha;
    private String descripcion_recordatorio;

    public Recordatorio() {
    }

    public Recordatorio(int idRecordatorio, Date fecha, String descripcion_recordatorio) {
        this.idRecordatorio = idRecordatorio;
        this.fecha = fecha;
        this.descripcion_recordatorio = descripcion_recordatorio;
    }

    public int getIdRecordatorio() {
        return idRecordatorio;
    }

    public void setIdRecordatorio(int idRecordatorio) {
        this.idRecordatorio = idRecordatorio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion_recordatorio() {
        return descripcion_recordatorio;
    }

    public void setDescripcion_recordatorio(String descripcion_recordatorio) {
        this.descripcion_recordatorio = descripcion_recordatorio;
    }
}

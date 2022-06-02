package sv.ues.fia.eisi.agendaestudiantil.ui.recordatorio;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import sv.ues.fia.eisi.agendaestudiantil.ui.examen.ExamenViewModel;


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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Comparator<RecordatorioViewModel> dateComparator = new Comparator<RecordatorioViewModel>() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX");

        LocalDateTime fecha1;
        LocalDateTime fecha2;
        String dia1;
        String dia2;

        @Override
        public int compare(RecordatorioViewModel p1, RecordatorioViewModel p2) {
            String [] parts, parts2;
            String tiempo, hora = "", amPm, minuto;
            int convertirHora;
            tiempo = p1.getHora();
            parts = tiempo.split(" ");
            parts2 = tiempo.split(":");
            minuto = parts2[1];
            amPm = parts[1];

            convertirHora = Integer.parseInt(parts2[0]);

            if (amPm.equals("PM") && convertirHora != 12){
                convertirHora += 12;
                hora=convertirHora+":"+minuto.substring(0,2)+":00";
            }
            else if(amPm.equals("PM") && convertirHora == 12)
                hora=convertirHora+":"+minuto.substring(0,2)+":00";

            if (amPm.equals("AM"))
                if (amPm.equals("AM") && convertirHora == 12) {
                    convertirHora -= 12;
                    hora=convertirHora+":"+minuto.substring(0,2)+":00";
                }
            if (convertirHora < 10)
                hora="0"+convertirHora+":"+minuto.substring(0,2)+":00";
            else
                hora=convertirHora+":"+minuto.substring(0,2)+":00";

            dia1 = p1.getFecha()+" "+hora+".000+00";
            fecha1 =LocalDateTime.parse(dia1, format);


            tiempo = p2.getHora();
            parts = tiempo.split(" ");
            parts2 = tiempo.split(":");
            minuto = parts2[1];
            amPm = parts[1];

            convertirHora = Integer.parseInt(parts2[0]);

            if (amPm.equals("PM") && convertirHora != 12){
                convertirHora += 12;
                hora=convertirHora+":"+minuto.substring(0,2)+":00";
            }
            else if(amPm.equals("PM") && convertirHora == 12)
                hora=convertirHora+":"+minuto.substring(0,2)+":00";

            if (amPm.equals("AM"))
                if (amPm.equals("AM") && convertirHora == 12) {
                    convertirHora -= 12;
                    hora=convertirHora+":"+minuto.substring(0,2)+":00";
                }
            if (convertirHora < 10)
                hora="0"+convertirHora+":"+minuto.substring(0,2)+":00";
            else
                hora=convertirHora+":"+minuto.substring(0,2)+":00";

            dia2 = p2.getFecha()+" "+hora+".000+00";
            fecha2 = LocalDateTime.parse(dia2, format);
            return fecha1.compareTo(fecha2);
        }
    };


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
package sv.ues.fia.eisi.agendaestudiantil.ui.examen;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;
import sv.ues.fia.eisi.agendaestudiantil.ui.periodo.PeriodoViewModel;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Comparator<ExamenViewModel> dateComparator = new Comparator<ExamenViewModel>() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX");

        LocalDateTime fecha1;
        LocalDateTime fecha2;
        String dia1;
        String dia2;

        @Override
        public int compare(ExamenViewModel p1, ExamenViewModel p2) {
            String [] parts, parts2;
            String tiempo, hora = "", amPm, minuto;
            int convertirHora;
            tiempo = p1.getHoraExamen();
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

            dia1 = p1.getFechaExamen()+" "+hora+".000+00";
            fecha1 =LocalDateTime.parse(dia1, format);


            tiempo = p2.getHoraExamen();
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

            dia2 = p2.getFechaExamen()+" "+hora+".000+00";
            fecha2 = LocalDateTime.parse(dia2, format);
            return fecha1.compareTo(fecha2);
        }
    };

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
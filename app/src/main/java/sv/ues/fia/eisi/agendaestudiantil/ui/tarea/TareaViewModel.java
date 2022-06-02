package sv.ues.fia.eisi.agendaestudiantil.ui.tarea;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import sv.ues.fia.eisi.agendaestudiantil.ui.examen.ExamenViewModel;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Comparator<TareaViewModel> dateComparator = new Comparator<TareaViewModel>() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX");

        LocalDateTime fecha1;
        LocalDateTime fecha2;
        String dia1;
        String dia2;

        @Override
        public int compare(TareaViewModel p1, TareaViewModel p2) {
            String [] parts, parts2;
            String tiempo, hora = "", amPm, minuto;
            int convertirHora;
            tiempo = p1.getHoraTarea();
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

            dia1 = p1.getFechaTarea()+" "+hora+".000+00";
            fecha1 =LocalDateTime.parse(dia1, format);


            tiempo = p2.getHoraTarea();
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

            dia2 = p2.getFechaTarea()+" "+hora+".000+00";
            fecha2 = LocalDateTime.parse(dia2, format);
            return fecha1.compareTo(fecha2);
        }
    };

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
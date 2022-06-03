package sv.ues.fia.eisi.agendaestudiantil.clases;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import sv.ues.fia.eisi.agendaestudiantil.ui.examen.ExamenViewModel;

public class Event {
    public static ArrayList<Event> eventsList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Event> eventos(LocalDate date){
        ArrayList<Event> eventos = new ArrayList<>();
        if (eventsList != null){
            for (Event event : eventsList){
                LocalDate fecha = LocalDate.parse(event.getFecha());
                if (fecha.equals(date))
                    eventos.add(event);
            }
        }
        return eventos;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Event> eventosSieteDias(LocalDate date){
        ArrayList<Event> eventos = new ArrayList<>();
        if (eventsList != null) {
            for (Event event : eventsList) {
                LocalDate fecha = LocalDate.parse(event.getFecha());
                if (fecha.equals(date) || fecha.isAfter(date) && fecha.isBefore(date.plusDays(7)))
                    eventos.add(event);
            }
        }
        return eventos;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Event> eventosPorMes(LocalDate date){
        ArrayList<Event> eventos = new ArrayList<>();
        if (eventsList != null) {
            for (Event event : eventsList) {
                LocalDate fecha = LocalDate.parse(event.getFecha());
                if (fecha.getMonthValue() == date.getMonthValue())
                    eventos.add(event);
            }
        }
        return eventos;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Event> eventosSiguienteMes(LocalDate date){
        ArrayList<Event> eventos = new ArrayList<>();
        if (eventsList != null) {
            for (Event event : eventsList) {
                LocalDate fecha = LocalDate.parse(event.getFecha());
                int siguiente = date.getMonthValue() + 1;
                if (fecha.getMonthValue() == siguiente)
                    eventos.add(event);
            }
        }
        return eventos;
    }


    private int idEvento;
    private String nombre;
    private String fecha;
    private String hora;
    private String descripcion;

    public Event(int idEvento, String nombre, String fecha, String hora, String descripcion) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
    }

    public static Comparator<Event> timeComparator = new Comparator<Event>() {
        LocalTime tiempo1;
        LocalTime tiempo2;
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(Event e1, Event e2) {
            String [] parts, parts2;
            String tiempo, hora = "", amPm, minuto;
            int convertirHora;
            tiempo = e1.getHora();
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
            tiempo1 = LocalTime.parse(hora);

            tiempo = e2.getHora();
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
            tiempo2 = LocalTime.parse(hora);

            return tiempo1.compareTo(tiempo2);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Comparator<Event> dateComparator = new Comparator<Event>() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX");

        LocalDateTime fecha1;
        LocalDateTime fecha2;
        String dia1;
        String dia2;

        @Override
        public int compare(Event p1, Event p2) {
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

    public Event() {
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

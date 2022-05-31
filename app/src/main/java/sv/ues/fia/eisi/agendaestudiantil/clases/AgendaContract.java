package sv.ues.fia.eisi.agendaestudiantil.clases;

import android.provider.BaseColumns;

public final class AgendaContract {
    private AgendaContract(){
    }

    public static class Agenda implements BaseColumns{
        public static final String TABLE_NAME = "agenda";
        public static final String COLUMN_NAME = "nombre";
    }

    public static class Notificacion implements BaseColumns{
        public static final String TABLE_NAME = "notificacion";
        public static final String COLUMN_ID_AGENDA = "id_agenda";
        public static final String COLUMN_DIA = "dia";
        public static final String COLUMN_NOTIFICACION = "con_notificacion";
        public static final String COLUMN_HORA = "hora";
    }

    public static class Recordatorio implements BaseColumns{
        public static final String TABLE_NAME = "recordatorio";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_ID_AGENDA = "id_agenda";
        public static final String COLUMN_FECHA = "fecha";
        public static final String COLUMN_HORA = "hora";
        public static final String COLUMN_DESCRIPCION = "descripcion";
    }

    public static class Profesor implements BaseColumns{
        public static final String TABLE_NAME = "profesor";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_APELLIDO = "apellido";
        public static final String COLUMN_TELEFONO = "telefono";
        public static final String COLUMN_CORREO = "correo";
        public static final String COLUMN_IMAGEN = "imagen";

    }

    public static class Materia implements BaseColumns{
        public static final String TABLE_NAME = "materia";
        public static final String COLUMN_ID_PROFESOR = "id_profesor";
        public static final String COLUMN_ID_PERIODO = "id_periodo";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_AULA = "aula";
    }

    public static class Horario implements BaseColumns{
        public static final String TABLE_NAME = "horario";
        public static final String COLUMN_NAME = "nombre";
    }

    public static class Clase implements BaseColumns{
        public static final String TABLE_NAME = "clase";
        public static final String COLUMN_ID_HORARIO = "id_horario";
        public static final String COLUMN_ID_MATERIA = "id_materia";
        public static final String COLUMN_ID_PROFESOR = "id_profesor";
        public static final String COLUMN_AULA = "aula";
        public static final String COLUMN_DIA = "dia";
        public static final String COLUMN_INICIO = "inicio";
        public static final String COLUMN_FIN = "fin";
        public static final String COLUMN_DESCRIPCION = "descripcion";
    }

    public static class Tarea implements BaseColumns{
        public static final String TABLE_NAME = "tarea";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_ID_MATERIA = "id_materia";
        public static final String COLUMN_ID_AGENDA = "id_agenda";
        public static final String COLUMN_TITULO = "titulo";
        public static final String COLUMN_DESCRIPCION = "descripcion";
        public static final String COLUMN_FECHA = "fecha";
        public static final String COLUMN_HORA = "hora";
        public static final String COLUMN_FINALIZADA = "finalizada";
        public static final String COLUMN_ARCHIVADA = "archivada";
    }

    public static class TipoExamen implements BaseColumns{
        public static final String TABLE_NAME = "tipo_examen";
        public static final String COLUMN_NAME = "nombre";
    }

    public static class Examen implements BaseColumns{
        public static final String TABLE_NAME = "examen";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_ID_AGENDA = "id_agenda";
        public static final String COLUMN_ID_MATERIA = "id_materia";
        public static final String COLUMN_ID_TIPO_EXAMEN = "id_tipo_examen";
        public static final String COLUMN_FECHA = "fecha";
        public static final String COLUMN_HORA = "hora";
        public static final String COLUMN_DESCRIPCION = "descripcion";
        public static final String COLUMN_AULA = "aula";
    }

    public static class Periodo implements BaseColumns{
        public static final String TABLE_NAME = "periodo";
        public static final String COLUMN_TITULO = "titulo";
        public static final String COLUMN_INICIO= "inicio";
        public static final String COLUMN_FIN = "fin";

    }

    public static class NotaExamen implements BaseColumns{
        public static final String TABLE_NAME = "nota_examen";
        public static final String COLUMN_ID_EXAMEN = "id_examen";
        public static final String COLUMN_ID_PERIODO = "id_periodo";
        public static final String COLUMN_CALIFICACION = "calificacion";
        public static final String COLUMN_FECHA = "fecha";
        public static final String COLUMN_PORCENTAJE = "porcentaje";
        public static final String COLUMN_DESCRIPCION = "descripcion";
    }

    public static class Estudio implements BaseColumns{
        public static final String TABLE_NAME = "estudio";
        public static final String COLUMN_DIA = "dia";
        public static final String COLUMN_INICIO= "inicio";
        public static final String COLUMN_FIN = "fin";
        public static final String COLUMN_DESCRIPCION = "descripcion";
    }

    public static class Archivo implements BaseColumns{
        public static final String TABLE_NAME = "archivo";
        public static final String COLUMN_ID_TAREA = "id_tarea";
        public static final String COLUMN_ID_PROFESOR = "id_profesor";
        public static final String COLUMN_ID_EXAMEN = "id_examen";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_EXTENSION = "extension";
        public static final String COLUMN_ARCHIVO = "archivo";
        public static final String COLUMN_TAMANIO = "tamanio";
        public static final String COLUMN_TIPO_ARCHIVO = "tipo_archivo";

    }
}

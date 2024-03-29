package sv.ues.fia.eisi.agendaestudiantil.clases;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.work.Data;
import androidx.work.WorkManager;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import sv.ues.fia.eisi.agendaestudiantil.ui.examen.ExamenViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.examen.NotaExamenViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.examen.TipoExamenViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.horario.ClaseViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.horario.HorarioViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.materia.MateriaViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.periodo.AgregarPeriodoFragment;
import sv.ues.fia.eisi.agendaestudiantil.ui.periodo.PeriodoViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.profesor.ProfesorViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.recordatorio.RecordatorioViewModel;
import sv.ues.fia.eisi.agendaestudiantil.ui.tarea.TareaViewModel;

public class BD {
    private final Context context;
    private DataBaseHelper bDHelper;
    private SQLiteDatabase bD;


    public BD(Context ctx){
        this.context = ctx;
        bDHelper = new DataBaseHelper(context);
    }
    private static final String SQL_CREATE_AGENDA
            = "CREATE TABLE "
            + AgendaContract.Agenda.TABLE_NAME + " ("
            + AgendaContract.Agenda._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Agenda.COLUMN_NAME + " TEXT(50) NOT NULL)";

    private static final String SQL_DELETE_AGENDA
            = "DROP TABLE IF EXISTS " + AgendaContract.Agenda.TABLE_NAME;


    private static final String SQL_CREATE_RECORDATORIO
            = "CREATE TABLE "
            + AgendaContract.Recordatorio.TABLE_NAME + " ("
            + AgendaContract.Recordatorio._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Recordatorio.COLUMN_NAME + " TEXT(50) NOT NULL, "
            + AgendaContract.Recordatorio.COLUMN_ID_AGENDA + " INTEGER NOT NULL, "
            + AgendaContract.Recordatorio.COLUMN_FECHA + " TEXT NOT NULL, "
            + AgendaContract.Recordatorio.COLUMN_HORA + " TEXT NOT NULL, "
            + AgendaContract.Recordatorio.COLUMN_DESCRIPCION + " TEXT(250), FOREIGN KEY ( "
            + AgendaContract.Recordatorio.COLUMN_ID_AGENDA + " ) REFERENCES "
            + AgendaContract.Agenda.TABLE_NAME + " ("
            + AgendaContract.Agenda._ID + ") ON DELETE CASCADE)";

    private static final String SQL_DELETE_RECORDATORIO
            = "DROP TABLE IF EXISTS " + AgendaContract.Recordatorio.TABLE_NAME;

    private static final String SQL_CREATE_PROFESOR
            = "CREATE TABLE "
            + AgendaContract.Profesor.TABLE_NAME + " ("
            + AgendaContract.Profesor._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Profesor.COLUMN_NAME + " TEXT(50) NOT NULL, "
            + AgendaContract.Profesor.COLUMN_APELLIDO + " TEXT(50) NOT NULL, "
            + AgendaContract.Profesor.COLUMN_TELEFONO + " TEXT(9), "
            + AgendaContract.Profesor.COLUMN_CORREO + " TEXT(50), "
            + AgendaContract.Profesor.COLUMN_IMAGEN + " TEXT)";

    private static final String SQL_DELETE_PROFESOR
            = "DROP TABLE IF EXISTS " + AgendaContract.Profesor.TABLE_NAME;

    private static final String SQL_CREATE_MATERIA
            = "CREATE TABLE "
            + AgendaContract.Materia.TABLE_NAME + " ("
            + AgendaContract.Materia._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Materia.COLUMN_ID_PROFESOR + " INTEGER NOT NULL, "
            + AgendaContract.Materia.COLUMN_ID_PERIODO + " INTEGER NOT NULL, "
            + AgendaContract.Materia.COLUMN_NAME + " TEXT(50) NOT NULL, "
            + AgendaContract.Materia.COLUMN_AULA + " TEXT(20), FOREIGN KEY ( "
            + AgendaContract.Materia.COLUMN_ID_PROFESOR + " ) REFERENCES "
            + AgendaContract.Profesor.TABLE_NAME + " ("
            + AgendaContract.Profesor._ID + ") ON DELETE RESTRICT, FOREIGN KEY ( "
            + AgendaContract.Materia.COLUMN_ID_PERIODO + " ) REFERENCES "
            + AgendaContract.Periodo.TABLE_NAME + " ("
            + AgendaContract.Periodo._ID + ") ON DELETE RESTRICT)";

    private static final String SQL_DELETE_MATERIA
            = "DROP TABLE IF EXISTS " + AgendaContract.Materia.TABLE_NAME;

    private static final String SQL_CREATE_HORARIO
            = "CREATE TABLE "
            + AgendaContract.Horario.TABLE_NAME + " ("
            + AgendaContract.Horario._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Horario.COLUMN_NAME+ " TEXT(20) NOT NULL)";

    private static final String SQL_DELETE_HORARIO
            = "DROP TABLE IF EXISTS " + AgendaContract.Horario.TABLE_NAME;

    private static final String SQL_CREATE_CLASE
            = "CREATE TABLE "
            + AgendaContract.Clase.TABLE_NAME + " ("
            + AgendaContract.Clase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Clase.COLUMN_ID_HORARIO + " INTEGER NOT NULL, "
            + AgendaContract.Clase.COLUMN_ID_MATERIA + " INTEGER NOT NULL, "
            + AgendaContract.Clase.COLUMN_ID_PROFESOR+ " INTEGER NOT NULL, "
            + AgendaContract.Clase.COLUMN_AULA+ " TEXT(20), "
            + AgendaContract.Clase.COLUMN_DIA + " TEXT NOT NULL, "
            + AgendaContract.Clase.COLUMN_INICIO + " TEXT NOT NULL, "
            + AgendaContract.Clase.COLUMN_FIN + " TEXT NOT NULL, "
            + AgendaContract.Clase.COLUMN_DESCRIPCION + " TEXT(250), FOREIGN KEY ( "
            + AgendaContract.Clase.COLUMN_ID_HORARIO + " ) REFERENCES "
            + AgendaContract.Horario.TABLE_NAME + " ("
            + AgendaContract.Horario._ID + ") ON DELETE CASCADE, FOREIGN KEY ( "
            + AgendaContract.Clase.COLUMN_ID_MATERIA + " ) REFERENCES "
            + AgendaContract.Materia.TABLE_NAME + " ("
            + AgendaContract.Materia._ID + ") ON DELETE CASCADE, FOREIGN KEY ( "
            + AgendaContract.Clase.COLUMN_ID_PROFESOR + " ) REFERENCES "
            + AgendaContract.Profesor.TABLE_NAME + " ("
            + AgendaContract.Profesor._ID + ") ON DELETE CASCADE)";

    private static final String SQL_DELETE_CLASE
            = "DROP TABLE IF EXISTS " + AgendaContract.Clase.TABLE_NAME;

    private static final String SQL_CREATE_TAREA
            = "CREATE TABLE "
            + AgendaContract.Tarea.TABLE_NAME + " ("
            + AgendaContract.Tarea._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Tarea.COLUMN_NAME + " TEXT(50) NOT NULL, "
            + AgendaContract.Tarea.COLUMN_ID_MATERIA + " INTEGER NOT NULL, "
            + AgendaContract.Tarea.COLUMN_ID_AGENDA + " INTEGER NOT NULL, "
            + AgendaContract.Tarea.COLUMN_TITULO + " TEXT(100) NOT NULL, "
            + AgendaContract.Tarea.COLUMN_DESCRIPCION + " TEXT(250), "
            + AgendaContract.Tarea.COLUMN_FECHA + " TEXT NOT NULL, "
            + AgendaContract.Tarea.COLUMN_HORA + " TEXT NOT NULL, "
            + AgendaContract.Tarea.COLUMN_FINALIZADA + " INTEGER DEFAULT 0, "
            + AgendaContract.Tarea.COLUMN_ARCHIVADA + " INTEGER DEFAULT 0, FOREIGN KEY ( "
            + AgendaContract.Tarea.COLUMN_ID_AGENDA + " ) REFERENCES "
            + AgendaContract.Agenda.TABLE_NAME + " ("
            + AgendaContract.Agenda._ID + ") ON DELETE CASCADE, FOREIGN KEY ( "
            + AgendaContract.Tarea.COLUMN_ID_MATERIA + " ) REFERENCES "
            + AgendaContract.Materia.TABLE_NAME + " ("
            + AgendaContract.Materia._ID + ") ON DELETE CASCADE)";

    private static final String SQL_DELETE_TAREA
            = "DROP TABLE IF EXISTS " + AgendaContract.Tarea.TABLE_NAME;

    private static final String SQL_CREATE_TIPO_EXAMEN
            = "CREATE TABLE "
            + AgendaContract.TipoExamen.TABLE_NAME + " ("
            + AgendaContract.TipoExamen._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.TipoExamen.COLUMN_NAME + " TEXT(50) NOT NULL)";

    private static final String SQL_DELETE_TIPO_EXAMEN
            = "DROP TABLE IF EXISTS " + AgendaContract.TipoExamen.TABLE_NAME;

    private static final String SQL_CREATE_EXAMEN
            = "CREATE TABLE "
            + AgendaContract.Examen.TABLE_NAME + " ("
            + AgendaContract.Examen._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Examen.COLUMN_NAME + " TEXT(50) NOT NULL, "
            + AgendaContract.Examen.COLUMN_ID_AGENDA + " INTEGER NOT NULL, "
            + AgendaContract.Examen.COLUMN_ID_MATERIA + " INTEGER NOT NULL, "
            + AgendaContract.Examen.COLUMN_ID_TIPO_EXAMEN + " INTEGER NOT NULL, "
            + AgendaContract.Examen.COLUMN_FECHA + " TEXT NOT NULL, "
            + AgendaContract.Examen.COLUMN_HORA + " TEXT NOT NULL, "
            + AgendaContract.Examen.COLUMN_DESCRIPCION + " TEXT(250), "
            + AgendaContract.Examen.COLUMN_AULA + " TEXT(20), FOREIGN KEY ( "
            + AgendaContract.Examen.COLUMN_ID_AGENDA + " ) REFERENCES "
            + AgendaContract.Agenda.TABLE_NAME + " ("
            + AgendaContract.Agenda._ID + ") ON DELETE CASCADE, FOREIGN KEY ( "
            + AgendaContract.Examen.COLUMN_ID_MATERIA + " ) REFERENCES "
            + AgendaContract.Materia.TABLE_NAME + " ("
            + AgendaContract.Materia._ID + ") ON DELETE CASCADE, FOREIGN KEY ( "
            + AgendaContract.Examen.COLUMN_ID_TIPO_EXAMEN + " ) REFERENCES "
            + AgendaContract.TipoExamen.TABLE_NAME + " ("
            + AgendaContract.TipoExamen._ID + ") ON DELETE RESTRICT)";

    private static final String SQL_DELETE_EXAMEN
            = "DROP TABLE IF EXISTS " + AgendaContract.Examen.TABLE_NAME;

    private static final String SQL_CREATE_PERIODO
            = "CREATE TABLE "
            + AgendaContract.Periodo.TABLE_NAME + " ("
            + AgendaContract.Periodo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Periodo.COLUMN_TITULO + " TEXT(100) NOT NULL, "
            + AgendaContract.Periodo.COLUMN_INICIO + " TEXT NOT NULL, "
            + AgendaContract.Periodo.COLUMN_FIN + " TEXT NOT NULL)";

    private static final String SQL_DELETE_PERIODO
            = "DROP TABLE IF EXISTS " + AgendaContract.Periodo.TABLE_NAME;

    private static final String SQL_CREATE_NOTA_EXAMEN
            = "CREATE TABLE "
            + AgendaContract.NotaExamen.TABLE_NAME + " ("
            + AgendaContract.NotaExamen._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.NotaExamen.COLUMN_ID_EXAMEN + " INTEGER NOT NULL, "
            + AgendaContract.NotaExamen.COLUMN_ID_MATERIA + " INTEGER NOT NULL, "
            + AgendaContract.NotaExamen.COLUMN_CALIFICACION + " REAL, "
            + AgendaContract.NotaExamen.COLUMN_PORCENTAJE + " INTEGER, "
            + AgendaContract.NotaExamen.COLUMN_DESCRIPCION + " TEXT(250), FOREIGN KEY ( "
            + AgendaContract.NotaExamen.COLUMN_ID_EXAMEN + " ) REFERENCES "
            + AgendaContract.Examen.TABLE_NAME + " ("
            + AgendaContract.Examen._ID + ") ON DELETE CASCADE, FOREIGN KEY ( "
            + AgendaContract.NotaExamen.COLUMN_ID_MATERIA + " ) REFERENCES "
            + AgendaContract.Materia.TABLE_NAME + " ("
            + AgendaContract.Materia._ID + ") ON DELETE CASCADE)";

    private static final String SQL_DELETE_NOTA_EXAMEN
            = "DROP TABLE IF EXISTS " + AgendaContract.NotaExamen.TABLE_NAME;

    private static final String SQL_INSERT_HORARIO
            = "INSERT INTO "
            + AgendaContract.Horario.TABLE_NAME + "(" +AgendaContract.Horario._ID + ", " + AgendaContract.Horario.COLUMN_NAME + ") VALUES (1,'Horario predeterminado')";

    private static final String SQL_INSERT_AGENDA
            = "INSERT INTO "
            + AgendaContract.Agenda.TABLE_NAME + "(" +AgendaContract.Agenda._ID + ", " + AgendaContract.Agenda.COLUMN_NAME + ") VALUES (1,'Agenda predeterminada')";

    private static final String SQL_INSERT_TIPO_EXAMEN
            = "INSERT INTO "
            + AgendaContract.TipoExamen.TABLE_NAME + "(" +AgendaContract.TipoExamen._ID + ", " + AgendaContract.TipoExamen.COLUMN_NAME + ") VALUES (1,'Examen parcial')";

    private static final String SQL_INSERT_TIPO_EXAMEN_2
            = "INSERT INTO "
            + AgendaContract.TipoExamen.TABLE_NAME + "(" +AgendaContract.TipoExamen._ID + ", " + AgendaContract.TipoExamen.COLUMN_NAME + ") VALUES (2,'Examen corto')";

    private static final String SQL_INSERT_TIPO_EXAMEN_3
            = "INSERT INTO "
            + AgendaContract.TipoExamen.TABLE_NAME + "(" +AgendaContract.TipoExamen._ID + ", " + AgendaContract.TipoExamen.COLUMN_NAME + ") VALUES (3,'Evaluado de laboratorio')";

    private static final String SQL_INSERT_TIPO_EXAMEN_4
            = "INSERT INTO "
            + AgendaContract.TipoExamen.TABLE_NAME + "(" +AgendaContract.TipoExamen._ID + ", " + AgendaContract.TipoExamen.COLUMN_NAME + ") VALUES (4,'Examen oral')";

    private static final String SQL_INSERT_TIPO_EXAMEN_5
            = "INSERT INTO "
            + AgendaContract.TipoExamen.TABLE_NAME + "(" +AgendaContract.TipoExamen._ID + ", " + AgendaContract.TipoExamen.COLUMN_NAME + ") VALUES (5,'Examen escrito')";

    private static final String SQL_INSERT_TIPO_EXAMEN_6
            = "INSERT INTO "
            + AgendaContract.TipoExamen.TABLE_NAME + "(" +AgendaContract.TipoExamen._ID + ", " + AgendaContract.TipoExamen.COLUMN_NAME + ") VALUES (6,'Proyecto/Tarea')";

    private static class DataBaseHelper extends SQLiteOpenHelper{
        private static final String BASE_DATOS = "agenda-bd.s3db";
        private static final int VERSION = 1;

        public DataBaseHelper(Context context) {
            super(context, BASE_DATOS, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase bD){
            try {
                bD.execSQL(SQL_CREATE_AGENDA);
                bD.execSQL(SQL_CREATE_RECORDATORIO);
                bD.execSQL(SQL_CREATE_PROFESOR);
                bD.execSQL(SQL_CREATE_MATERIA);
                bD.execSQL(SQL_CREATE_HORARIO);
                bD.execSQL(SQL_CREATE_CLASE);
                bD.execSQL(SQL_CREATE_TAREA);
                bD.execSQL(SQL_CREATE_TIPO_EXAMEN);
                bD.execSQL(SQL_CREATE_EXAMEN);
                bD.execSQL(SQL_CREATE_PERIODO);
                bD.execSQL(SQL_CREATE_NOTA_EXAMEN);
                bD.execSQL(SQL_INSERT_HORARIO);
                bD.execSQL(SQL_INSERT_AGENDA);
                bD.execSQL(SQL_INSERT_TIPO_EXAMEN);
                bD.execSQL(SQL_INSERT_TIPO_EXAMEN_2);
                bD.execSQL(SQL_INSERT_TIPO_EXAMEN_3);
                bD.execSQL(SQL_INSERT_TIPO_EXAMEN_4);
                bD.execSQL(SQL_INSERT_TIPO_EXAMEN_5);
                bD.execSQL(SQL_INSERT_TIPO_EXAMEN_6);

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        public void onUpgrade(SQLiteDatabase bD, int oldVersion, int newVersion){
            bD.execSQL(SQL_DELETE_AGENDA);
            bD.execSQL(SQL_DELETE_RECORDATORIO);
            bD.execSQL(SQL_DELETE_PROFESOR);
            bD.execSQL(SQL_DELETE_MATERIA);
            bD.execSQL(SQL_DELETE_HORARIO);
            bD.execSQL(SQL_DELETE_CLASE);
            bD.execSQL(SQL_DELETE_TAREA);
            bD.execSQL(SQL_DELETE_TIPO_EXAMEN);
            bD.execSQL(SQL_DELETE_EXAMEN);
            bD.execSQL(SQL_DELETE_PERIODO);
            bD.execSQL(SQL_DELETE_NOTA_EXAMEN);
            onCreate(bD);
        }
        public void onDowngrade(SQLiteDatabase bD, int oldVersion, int newVersion) {
            onUpgrade(bD, oldVersion, newVersion);
        }
    }

    public void abrir() throws SQLException{
        bD = bDHelper.getWritableDatabase();
        return;
    }

    public void cerrar(){
        bDHelper.close();
    }

    public String insertar(Agenda agenda){
        String mensaje = "Agenda guardada";
        long nuevaFilaId = 0;

        ContentValues agendas = new ContentValues();
        agendas.put(AgendaContract.Agenda.COLUMN_NAME, agenda.getNombreAgenda());

        nuevaFilaId = bD.insert(AgendaContract.Agenda.TABLE_NAME,null,agendas);

        if (nuevaFilaId == -1 || nuevaFilaId == 0)
            mensaje = "Error al insertar la agenda";
        return mensaje;
    }

    public String insertar(HorarioViewModel horario){
        String mensaje = "Horario guardado";
        long nuevaFilaId = 0;

        ContentValues horarios = new ContentValues();
        horarios.put(AgendaContract.Horario.COLUMN_NAME, horario.getNombreHorario());

        nuevaFilaId = bD.insert(AgendaContract.Horario.TABLE_NAME,null,horarios);

        if (nuevaFilaId == -1 || nuevaFilaId == 0)
            mensaje = "Error al insertar el horario";
        return mensaje;
    }

    public String insertar(TipoExamenViewModel tipoExamen){
        String mensaje = "Tipo de examen guardado";
        long nuevaFilaId = 0;

        ContentValues tipos = new ContentValues();
        tipos.put(AgendaContract.TipoExamen.COLUMN_NAME, tipoExamen.getNombreTipoExamen());

        nuevaFilaId = bD.insert(AgendaContract.TipoExamen.TABLE_NAME,null,tipos);

        if (nuevaFilaId == -1 || nuevaFilaId == 0)
            mensaje = "Error al insertar el tipo de examen";
        return mensaje;
    }



    public Boolean verificarExistenciaDeAgenda(){
        try
        {
            bD = bDHelper.getReadableDatabase();
            Cursor filas = bD.rawQuery("SELECT * FROM agenda", null);
            if (filas.moveToFirst()){
                return true;
            }else {
                return false;
            }
        }catch (Exception ex){
            return false;
        }
    }

    public String insertar(ProfesorViewModel profesor){
        String mensaje = "Profesor guardado";
        long nuevaFilaId = 0;

        ContentValues profesores = new ContentValues();
        profesores.put(AgendaContract.Profesor.COLUMN_NAME, profesor.getNombreProfesor());
        profesores.put(AgendaContract.Profesor.COLUMN_APELLIDO, profesor.getApellidoProfesor());
        profesores.put(AgendaContract.Profesor.COLUMN_TELEFONO, profesor.getTelefonoProfesor());
        profesores.put(AgendaContract.Profesor.COLUMN_CORREO, profesor.getCorreoProfesor());
        profesores.put(AgendaContract.Profesor.COLUMN_IMAGEN, profesor.getImagenProfesor());

        nuevaFilaId = bD.insert(AgendaContract.Profesor.TABLE_NAME,null,profesores);

        if (nuevaFilaId == -1 || nuevaFilaId == 0)
            mensaje = "Error al insertar el profesor";
        return mensaje;
    }

    public String insertar(ExamenViewModel examen){
        String mensaje = "Examen guardado";
        long nuevaFilaId = 0;

        ContentValues examenes = new ContentValues();
        examenes.put(AgendaContract.Examen.COLUMN_NAME, examen.getNombreExamen());
        examenes.put(AgendaContract.Examen.COLUMN_ID_AGENDA, examen.getIdAgenda());
        examenes.put(AgendaContract.Examen.COLUMN_ID_MATERIA, examen.getIdMateria());
        examenes.put(AgendaContract.Examen.COLUMN_ID_TIPO_EXAMEN, examen.getIdTipoExamen());
        examenes.put(AgendaContract.Examen.COLUMN_FECHA, examen.getFechaExamen());
        examenes.put(AgendaContract.Examen.COLUMN_HORA, examen.getHoraExamen());
        examenes.put(AgendaContract.Examen.COLUMN_DESCRIPCION, examen.getDescripcionExamen());
        examenes.put(AgendaContract.Examen.COLUMN_AULA, examen.getAulaExamen());

        nuevaFilaId = bD.insert(AgendaContract.Examen.TABLE_NAME,null,examenes);

        if (nuevaFilaId == -1 || nuevaFilaId == 0)
            mensaje = "Error al insertar el examen";
        return mensaje;
    }

    public String insertar(NotaExamenViewModel notaExamen){
        String mensaje = "Nota de examen guardada";
        long nuevaFilaId = 0;

        ContentValues notas = new ContentValues();
        notas.put(AgendaContract.NotaExamen.COLUMN_ID_EXAMEN, notaExamen.getIdExamen());
        notas.put(AgendaContract.NotaExamen.COLUMN_ID_MATERIA, notaExamen.getIdMateria());
        notas.put(AgendaContract.NotaExamen.COLUMN_CALIFICACION, notaExamen.getCalificacion());
        notas.put(AgendaContract.NotaExamen.COLUMN_PORCENTAJE, notaExamen.getPorcentaje());
        notas.put(AgendaContract.NotaExamen.COLUMN_DESCRIPCION, notaExamen.getDescripcionExamen());

        nuevaFilaId = bD.insert(AgendaContract.NotaExamen.TABLE_NAME,null,notas);

        if (nuevaFilaId == -1 || nuevaFilaId == 0)
            mensaje = "Error al insertar la nota de examen";
        return mensaje;
    }

    public String insertar(TareaViewModel tarea){
        String mensaje = "Tarea guardada";
        long nuevaFilaId = 0;

        ContentValues tareas = new ContentValues();
        tareas.put(AgendaContract.Tarea.COLUMN_NAME, tarea.getNombre());
        tareas.put(AgendaContract.Tarea.COLUMN_ID_MATERIA, tarea.getIdMateria());
        tareas.put(AgendaContract.Tarea.COLUMN_ID_AGENDA, tarea.getIdAgenda());
        tareas.put(AgendaContract.Tarea.COLUMN_TITULO, tarea.getTituloTarea());
        tareas.put(AgendaContract.Tarea.COLUMN_DESCRIPCION, tarea.getDescripcionTarea());
        tareas.put(AgendaContract.Tarea.COLUMN_FECHA, tarea.getFechaTarea());
        tareas.put(AgendaContract.Tarea.COLUMN_HORA, tarea.getHoraTarea());
        tareas.put(AgendaContract.Tarea.COLUMN_FINALIZADA, tarea.getFinalizadaTarea());
        tareas.put(AgendaContract.Tarea.COLUMN_ARCHIVADA, tarea.getArchivadaTarea());

        nuevaFilaId = bD.insert(AgendaContract.Tarea.TABLE_NAME,null,tareas);

        if (nuevaFilaId == -1 || nuevaFilaId == 0)
            mensaje = "Error al insertar la tarea";
        return mensaje;
    }

    public String insertar(PeriodoViewModel periodo){
        String mensaje = "Periodo guardado";
        long nuevaFilaId = 0;

        ContentValues periodos = new ContentValues();
        periodos.put(AgendaContract.Periodo.COLUMN_TITULO, periodo.getTituloPeriodo());
        periodos.put(AgendaContract.Periodo.COLUMN_INICIO, String.valueOf(periodo.getInicioPeriodo()));
        periodos.put(AgendaContract.Periodo.COLUMN_FIN, String.valueOf(periodo.getFinPeriodo()));

        nuevaFilaId = bD.insert(AgendaContract.Periodo.TABLE_NAME, null, periodos);

        if (nuevaFilaId == -1 || nuevaFilaId == 0)
            mensaje = "Error al insertar el periodo";
        return mensaje;

    }

    public String insertar(MateriaViewModel materia){
        String mensaje = "Materia guardada";
        long nuevaFilaId = 0;

        ContentValues materias = new ContentValues();
        materias.put(AgendaContract.Materia.COLUMN_ID_PROFESOR, materia.getIdProfesor());
        materias.put(AgendaContract.Materia.COLUMN_ID_PERIODO, materia.getIdPeriodo());
        materias.put(AgendaContract.Materia.COLUMN_NAME, materia.getNombreMateria());
        materias.put(AgendaContract.Materia.COLUMN_AULA, materia.getAulaMateria());

        nuevaFilaId = bD.insert(AgendaContract.Materia.TABLE_NAME, null, materias);

        if (nuevaFilaId == -1 || nuevaFilaId == 0)
            mensaje = "Error al insertar la materia";
        return mensaje;

    }

    public String insertar(ClaseViewModel clase){
        String mensaje = "Clase guardada";
        long nuevaFilaId = 0;

        ContentValues clases = new ContentValues();
        clases.put(AgendaContract.Clase.COLUMN_ID_HORARIO, clase.getIdHorario());
        clases.put(AgendaContract.Clase.COLUMN_ID_MATERIA, clase.getIdMateria());
        clases.put(AgendaContract.Clase.COLUMN_ID_PROFESOR, clase.getIdProfesor());
        clases.put(AgendaContract.Clase.COLUMN_AULA, clase.getAulaClase());
        clases.put(AgendaContract.Clase.COLUMN_DIA, clase.getDiaClase());
        clases.put(AgendaContract.Clase.COLUMN_INICIO, clase.getInicioClase());
        clases.put(AgendaContract.Clase.COLUMN_FIN, clase.getFinClase());
        clases.put(AgendaContract.Clase.COLUMN_DESCRIPCION, clase.getDescripcionClase());

        nuevaFilaId = bD.insert(AgendaContract.Clase.TABLE_NAME, null, clases);

        if (nuevaFilaId == -1 || nuevaFilaId == 0)
            mensaje = "Error al insertar la clase";
        return mensaje;
    }

    public String insertar(RecordatorioViewModel recordatorio){
        String mensaje = "Recordatorio guardado";
        long nuevaFilaId = 0;

        ContentValues recordatorios = new ContentValues();
        recordatorios.put(AgendaContract.Recordatorio.COLUMN_NAME, recordatorio.getNombreRecordatorio());
        recordatorios.put(AgendaContract.Recordatorio.COLUMN_ID_AGENDA, recordatorio.getIdAgenda());
        recordatorios.put(AgendaContract.Recordatorio.COLUMN_FECHA, recordatorio.getFecha());
        recordatorios.put(AgendaContract.Recordatorio.COLUMN_HORA, recordatorio.getHora());
        recordatorios.put(AgendaContract.Recordatorio.COLUMN_DESCRIPCION, recordatorio.getDescripcionRecordatorio());

        nuevaFilaId = bD.insert(AgendaContract.Recordatorio.TABLE_NAME, null, recordatorios);

        if (nuevaFilaId == -1 || nuevaFilaId == 0)
            mensaje = "Error al insertar el recordatorio";
        return mensaje;
    }

    public ArrayList<ProfesorViewModel> mostrarProfesores(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<ProfesorViewModel> listaProfesores = new ArrayList<>();
        ProfesorViewModel profesor = null;
        Cursor cursorProfesores = null;

        cursorProfesores = bD.rawQuery("SELECT * FROM " + AgendaContract.Profesor.TABLE_NAME + " ORDER BY " + AgendaContract.Profesor.COLUMN_NAME + " ASC ", null);

        if (cursorProfesores.moveToFirst()){
            do {
                profesor = new ProfesorViewModel();
                profesor.setIdProfesor(cursorProfesores.getInt(0));
                profesor.setNombreProfesor(cursorProfesores.getString(1));
                profesor.setApellidoProfesor(cursorProfesores.getString(2));
                profesor.setTelefonoProfesor(cursorProfesores.getString(3));
                profesor.setCorreoProfesor(cursorProfesores.getString(4));
                profesor.setImagenProfesor(cursorProfesores.getString(5));
                listaProfesores.add(profesor);
            }while (cursorProfesores.moveToNext());
        }
        cursorProfesores.close();

        return listaProfesores;
    }



    public ArrayList<PeriodoViewModel> mostrarPeriodos(){

        bD = bDHelper.getWritableDatabase();

        ArrayList<PeriodoViewModel> listaPeriodos = new ArrayList<>();
        PeriodoViewModel periodo = null;
        Cursor cursorPeriodos = null;

        cursorPeriodos = bD.rawQuery("SELECT * FROM " + AgendaContract.Periodo.TABLE_NAME + " ORDER BY " + AgendaContract.Periodo.COLUMN_INICIO, null);

        if (cursorPeriodos.moveToFirst()){
            do {
                periodo = new PeriodoViewModel();
                periodo.setIdPeriodo(cursorPeriodos.getInt(0));
                periodo.setTituloPeriodo(cursorPeriodos.getString(1));
                periodo.setInicioPeriodo((cursorPeriodos.getString(2)));
                periodo.setFinPeriodo((cursorPeriodos.getString(3)));
                listaPeriodos.add(periodo);
            }while (cursorPeriodos.moveToNext());
        }
        cursorPeriodos.close();
        bDHelper.close();
        return listaPeriodos;
    }

    public ArrayList<ExamenViewModel> mostrarExamenes(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<ExamenViewModel> listaExamenes = new ArrayList<>();
        ExamenViewModel examen = null;
        Cursor cursorExamenes = null;

        cursorExamenes = bD.rawQuery("SELECT * FROM " + AgendaContract.Examen.TABLE_NAME, null);

        if (cursorExamenes.moveToFirst()) {
            do {
                examen = new ExamenViewModel();
                examen.setIdExamen(cursorExamenes.getInt(0));
                examen.setNombreExamen(cursorExamenes.getString(1));
                examen.setIdAgenda(cursorExamenes.getInt(2));
                examen.setIdMateria(cursorExamenes.getInt(3));
                examen.setIdTipoExamen(cursorExamenes.getInt(4));
                examen.setFechaExamen(cursorExamenes.getString(5));
                examen.setHoraExamen(cursorExamenes.getString(6));
                examen.setDescripcionExamen(cursorExamenes.getString(7));
                examen.setAulaExamen(cursorExamenes.getString(8));
                listaExamenes.add(examen);
            } while (cursorExamenes.moveToNext());
        }
            cursorExamenes.close();
            return listaExamenes;
    }

    public ArrayList<ExamenViewModel> mostrarExamenesAyer(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<ExamenViewModel> listaExamenes = new ArrayList<>();
        ExamenViewModel examen = null;
        Cursor cursorExamenes = null;

        cursorExamenes = bD.rawQuery("SELECT * FROM " + AgendaContract.Examen.TABLE_NAME + " WHERE " + AgendaContract.Examen.COLUMN_FECHA + " = date(datetime('now', 'localtime'), '-1 days')", null);

        if (cursorExamenes.moveToFirst()){
            do {
                examen = new ExamenViewModel();
                examen.setIdExamen(cursorExamenes.getInt(0));
                examen.setNombreExamen(cursorExamenes.getString(1));
                examen.setIdAgenda(cursorExamenes.getInt(2));
                examen.setIdMateria(cursorExamenes.getInt(3));
                examen.setIdTipoExamen(cursorExamenes.getInt(4));
                examen.setFechaExamen(cursorExamenes.getString(5));
                examen.setHoraExamen(cursorExamenes.getString(6));
                examen.setDescripcionExamen(cursorExamenes.getString(7));
                examen.setAulaExamen(cursorExamenes.getString(8));
                listaExamenes.add(examen);
            } while (cursorExamenes.moveToNext());
        }
        cursorExamenes.close();
        return listaExamenes;
    }

    public ArrayList<ExamenViewModel> mostrarExamenesHoy(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<ExamenViewModel> listaExamenes = new ArrayList<>();
        ExamenViewModel examen = null;
        Cursor cursorExamenes = null;

        cursorExamenes = bD.rawQuery("SELECT * FROM " + AgendaContract.Examen.TABLE_NAME + " WHERE " + AgendaContract.Examen.COLUMN_FECHA + " = date(datetime('now', 'localtime'))", null);

        if (cursorExamenes.moveToFirst()){
            do {
                examen = new ExamenViewModel();
                examen.setIdExamen(cursorExamenes.getInt(0));
                examen.setNombreExamen(cursorExamenes.getString(1));
                examen.setIdAgenda(cursorExamenes.getInt(2));
                examen.setIdMateria(cursorExamenes.getInt(3));
                examen.setIdTipoExamen(cursorExamenes.getInt(4));
                examen.setFechaExamen(cursorExamenes.getString(5));
                examen.setHoraExamen(cursorExamenes.getString(6));
                examen.setDescripcionExamen(cursorExamenes.getString(7));
                examen.setAulaExamen(cursorExamenes.getString(8));
                listaExamenes.add(examen);
            } while (cursorExamenes.moveToNext());
        }
        cursorExamenes.close();
        return listaExamenes;
    }

    public ArrayList<ExamenViewModel> mostrarExamenesMañana(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<ExamenViewModel> listaExamenes = new ArrayList<>();
        ExamenViewModel examen = null;
        Cursor cursorExamenes = null;

        cursorExamenes = bD.rawQuery("SELECT * FROM " + AgendaContract.Examen.TABLE_NAME + " WHERE " + AgendaContract.Examen.COLUMN_FECHA +" = date(datetime('now', 'localtime'), '+1 days')", null);

        if (cursorExamenes.moveToFirst()){
            do {
                examen = new ExamenViewModel();
                examen.setIdExamen(cursorExamenes.getInt(0));
                examen.setNombreExamen(cursorExamenes.getString(1));
                examen.setIdAgenda(cursorExamenes.getInt(2));
                examen.setIdMateria(cursorExamenes.getInt(3));
                examen.setIdTipoExamen(cursorExamenes.getInt(4));
                examen.setFechaExamen(cursorExamenes.getString(5));
                examen.setHoraExamen(cursorExamenes.getString(6));
                examen.setDescripcionExamen(cursorExamenes.getString(7));
                examen.setAulaExamen(cursorExamenes.getString(8));
                listaExamenes.add(examen);
            } while (cursorExamenes.moveToNext());
        }
        cursorExamenes.close();
        return listaExamenes;
    }

    public ArrayList<ExamenViewModel> mostrarExamenesSieteDias(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<ExamenViewModel> listaExamenes = new ArrayList<>();
        ExamenViewModel examen = null;
        Cursor cursorExamenes = null;

        cursorExamenes = bD.rawQuery("SELECT * FROM " + AgendaContract.Examen.TABLE_NAME + " WHERE " + AgendaContract.Examen.COLUMN_FECHA +" >= date(datetime('now', 'localtime')) AND " + AgendaContract.Examen.COLUMN_FECHA + " < date(datetime('now', 'localtime'), '+1 days')", null);

        if (cursorExamenes.moveToFirst()){
            do {
                examen = new ExamenViewModel();
                examen.setIdExamen(cursorExamenes.getInt(0));
                examen.setNombreExamen(cursorExamenes.getString(1));
                examen.setIdAgenda(cursorExamenes.getInt(2));
                examen.setIdMateria(cursorExamenes.getInt(3));
                examen.setIdTipoExamen(cursorExamenes.getInt(4));
                examen.setFechaExamen(cursorExamenes.getString(5));
                examen.setHoraExamen(cursorExamenes.getString(6));
                examen.setDescripcionExamen(cursorExamenes.getString(7));
                examen.setAulaExamen(cursorExamenes.getString(8));
                listaExamenes.add(examen);
            } while (cursorExamenes.moveToNext());
        }
        cursorExamenes.close();
        return listaExamenes;
    }

    public ArrayList<ExamenViewModel> mostrarExamenesEsteMes(String mes){
        bD = bDHelper.getWritableDatabase();

        ArrayList<ExamenViewModel> listaExamenes = new ArrayList<>();
        ExamenViewModel examen = null;
        Cursor cursorExamenes = null;

        cursorExamenes = bD.rawQuery("SELECT * FROM " + AgendaContract.Examen.TABLE_NAME + " WHERE strftime('%m', " + AgendaContract.Examen.COLUMN_FECHA + ") = " + "'" + mes +"'", null);

        if (cursorExamenes.moveToFirst()){
            do {
                examen = new ExamenViewModel();
                examen.setIdExamen(cursorExamenes.getInt(0));
                examen.setNombreExamen(cursorExamenes.getString(1));
                examen.setIdAgenda(cursorExamenes.getInt(2));
                examen.setIdMateria(cursorExamenes.getInt(3));
                examen.setIdTipoExamen(cursorExamenes.getInt(4));
                examen.setFechaExamen(cursorExamenes.getString(5));
                examen.setHoraExamen(cursorExamenes.getString(6));
                examen.setDescripcionExamen(cursorExamenes.getString(7));
                examen.setAulaExamen(cursorExamenes.getString(8));
                listaExamenes.add(examen);
            } while (cursorExamenes.moveToNext());
        }
        cursorExamenes.close();
        return listaExamenes;
    }

    public ArrayList<ExamenViewModel> mostrarExamenesSiguienteMes(String mes){
        bD = bDHelper.getWritableDatabase();

        ArrayList<ExamenViewModel> listaExamenes = new ArrayList<>();
        ExamenViewModel examen = null;
        Cursor cursorExamenes = null;

        cursorExamenes = bD.rawQuery("SELECT * FROM " + AgendaContract.Examen.TABLE_NAME + " WHERE strftime('%m', " + AgendaContract.Examen.COLUMN_FECHA + ") = " + "'" + mes +"'", null);

        if (cursorExamenes.moveToFirst()){
            do {
                examen = new ExamenViewModel();
                examen.setIdExamen(cursorExamenes.getInt(0));
                examen.setNombreExamen(cursorExamenes.getString(1));
                examen.setIdAgenda(cursorExamenes.getInt(2));
                examen.setIdMateria(cursorExamenes.getInt(3));
                examen.setIdTipoExamen(cursorExamenes.getInt(4));
                examen.setFechaExamen(cursorExamenes.getString(5));
                examen.setHoraExamen(cursorExamenes.getString(6));
                examen.setDescripcionExamen(cursorExamenes.getString(7));
                examen.setAulaExamen(cursorExamenes.getString(8));
                listaExamenes.add(examen);
            } while (cursorExamenes.moveToNext());
        }
        cursorExamenes.close();
        return listaExamenes;
    }

    public ArrayList<TareaViewModel> mostrarTareas(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<TareaViewModel> listaTareas = new ArrayList<>();
        TareaViewModel tarea = null;
        Cursor cursorTareas = null;

        cursorTareas = bD.rawQuery("SELECT * FROM " + AgendaContract.Tarea.TABLE_NAME, null);
        if (cursorTareas.moveToFirst()){
            do {
                tarea = new TareaViewModel();
                tarea.setIdTarea(cursorTareas.getInt(0));
                tarea.setNombre(cursorTareas.getString(1));
                tarea.setIdMateria(cursorTareas.getInt(2));
                tarea.setIdAgenda(cursorTareas.getInt(3));
                tarea.setTituloTarea(cursorTareas.getString(4));
                tarea.setDescripcionTarea(cursorTareas.getString(5));
                tarea.setFechaTarea(cursorTareas.getString(6));
                tarea.setHoraTarea(cursorTareas.getString(7));
                tarea.setFinalizadaTarea(cursorTareas.getInt(8));
                tarea.setArchivadaTarea(cursorTareas.getInt(9));
                listaTareas.add(tarea);
            }while (cursorTareas.moveToNext());
        }
        cursorTareas.close();
        return listaTareas;
    }

    public ArrayList<TareaViewModel> mostrarTareasAyer(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<TareaViewModel> listaTareas = new ArrayList<>();
        TareaViewModel tarea = null;
        Cursor cursorTareas = null;

        cursorTareas = bD.rawQuery("SELECT * FROM " + AgendaContract.Tarea.TABLE_NAME + " WHERE " + AgendaContract.Tarea.COLUMN_FECHA + " = date(datetime('now', 'localtime'), '-1 days')", null);

        if (cursorTareas.moveToFirst()){
            do {
                tarea = new TareaViewModel();
                tarea.setIdTarea(cursorTareas.getInt(0));
                tarea.setNombre(cursorTareas.getString(1));
                tarea.setIdMateria(cursorTareas.getInt(2));
                tarea.setIdAgenda(cursorTareas.getInt(3));
                tarea.setTituloTarea(cursorTareas.getString(4));
                tarea.setDescripcionTarea(cursorTareas.getString(5));
                tarea.setFechaTarea(cursorTareas.getString(6));
                tarea.setHoraTarea(cursorTareas.getString(7));
                tarea.setFinalizadaTarea(cursorTareas.getInt(8));
                tarea.setArchivadaTarea(cursorTareas.getInt(9));
                listaTareas.add(tarea);
            } while (cursorTareas.moveToNext());
        }
        cursorTareas.close();
        return listaTareas;
    }

    public ArrayList<TareaViewModel> mostrarTareasHoy(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<TareaViewModel> listaTareas = new ArrayList<>();
        TareaViewModel tarea = null;
        Cursor cursorTareas = null;

        cursorTareas = bD.rawQuery("SELECT * FROM " + AgendaContract.Tarea.TABLE_NAME + " WHERE " + AgendaContract.Tarea.COLUMN_FECHA + " = date(datetime('now', 'localtime'))", null);

        if (cursorTareas.moveToFirst()){
            do {
                tarea = new TareaViewModel();
                tarea.setIdTarea(cursorTareas.getInt(0));
                tarea.setNombre(cursorTareas.getString(1));
                tarea.setIdMateria(cursorTareas.getInt(2));
                tarea.setIdAgenda(cursorTareas.getInt(3));
                tarea.setTituloTarea(cursorTareas.getString(4));
                tarea.setDescripcionTarea(cursorTareas.getString(5));
                tarea.setFechaTarea(cursorTareas.getString(6));
                tarea.setHoraTarea(cursorTareas.getString(7));
                tarea.setFinalizadaTarea(cursorTareas.getInt(8));
                tarea.setArchivadaTarea(cursorTareas.getInt(9));
                listaTareas.add(tarea);
            } while (cursorTareas.moveToNext());
        }
        cursorTareas.close();
        return listaTareas;
    }

    public ArrayList<TareaViewModel> mostrarTareasMañana(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<TareaViewModel> listaTareas = new ArrayList<>();
        TareaViewModel tarea = null;
        Cursor cursorTareas = null;

        cursorTareas = bD.rawQuery("SELECT * FROM " + AgendaContract.Tarea.TABLE_NAME + " WHERE " + AgendaContract.Tarea.COLUMN_FECHA +" = date(datetime('now', 'localtime'), '+1 days')", null);

        if (cursorTareas.moveToFirst()){
            do {
                tarea = new TareaViewModel();
                tarea.setIdTarea(cursorTareas.getInt(0));
                tarea.setNombre(cursorTareas.getString(1));
                tarea.setIdMateria(cursorTareas.getInt(2));
                tarea.setIdAgenda(cursorTareas.getInt(3));
                tarea.setTituloTarea(cursorTareas.getString(4));
                tarea.setDescripcionTarea(cursorTareas.getString(5));
                tarea.setFechaTarea(cursorTareas.getString(6));
                tarea.setHoraTarea(cursorTareas.getString(7));
                tarea.setFinalizadaTarea(cursorTareas.getInt(8));
                tarea.setArchivadaTarea(cursorTareas.getInt(9));
                listaTareas.add(tarea);
            } while (cursorTareas.moveToNext());
        }
        cursorTareas.close();
        return listaTareas;
    }

    public ArrayList<TareaViewModel> mostrarTareasSieteDias(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<TareaViewModel> listaTareas = new ArrayList<>();
        TareaViewModel tarea = null;
        Cursor cursorTareas = null;

        cursorTareas = bD.rawQuery("SELECT * FROM " + AgendaContract.Tarea.TABLE_NAME + " WHERE " + AgendaContract.Tarea.COLUMN_FECHA +" >= date(datetime('now', 'localtime')) AND " + AgendaContract.Tarea.COLUMN_FECHA + " < date(datetime('now', 'localtime'), '+7 days')", null);


        if (cursorTareas.moveToFirst()){
            do {
                tarea = new TareaViewModel();
                tarea.setIdTarea(cursorTareas.getInt(0));
                tarea.setNombre(cursorTareas.getString(1));
                tarea.setIdMateria(cursorTareas.getInt(2));
                tarea.setIdAgenda(cursorTareas.getInt(3));
                tarea.setTituloTarea(cursorTareas.getString(4));
                tarea.setDescripcionTarea(cursorTareas.getString(5));
                tarea.setFechaTarea(cursorTareas.getString(6));
                tarea.setHoraTarea(cursorTareas.getString(7));
                tarea.setFinalizadaTarea(cursorTareas.getInt(8));
                tarea.setArchivadaTarea(cursorTareas.getInt(9));
                listaTareas.add(tarea);
            } while (cursorTareas.moveToNext());
        }
        cursorTareas.close();
        return listaTareas;
    }

    public ArrayList<TareaViewModel> mostrarTareasEsteMes(String mes){
        bD = bDHelper.getWritableDatabase();

        ArrayList<TareaViewModel> listaTareas = new ArrayList<>();
        TareaViewModel tarea = null;
        Cursor cursorTareas = null;

        cursorTareas = bD.rawQuery("SELECT * FROM " + AgendaContract.Tarea.TABLE_NAME + " WHERE strftime('%m', " + AgendaContract.Tarea.COLUMN_FECHA + ") = " + "'" + mes +"'", null);


        if (cursorTareas.moveToFirst()){
            do {
                tarea = new TareaViewModel();
                tarea.setIdTarea(cursorTareas.getInt(0));
                tarea.setNombre(cursorTareas.getString(1));
                tarea.setIdMateria(cursorTareas.getInt(2));
                tarea.setIdAgenda(cursorTareas.getInt(3));
                tarea.setTituloTarea(cursorTareas.getString(4));
                tarea.setDescripcionTarea(cursorTareas.getString(5));
                tarea.setFechaTarea(cursorTareas.getString(6));
                tarea.setHoraTarea(cursorTareas.getString(7));
                tarea.setFinalizadaTarea(cursorTareas.getInt(8));
                tarea.setArchivadaTarea(cursorTareas.getInt(9));
                listaTareas.add(tarea);
            } while (cursorTareas.moveToNext());
        }
        cursorTareas.close();
        return listaTareas;
    }

    public ArrayList<TareaViewModel> mostrarTareasSiguienteMes(String mes){
        bD = bDHelper.getWritableDatabase();

        ArrayList<TareaViewModel> listaTareas = new ArrayList<>();
        TareaViewModel tarea = null;
        Cursor cursorTareas = null;

        cursorTareas = bD.rawQuery("SELECT * FROM " + AgendaContract.Tarea.TABLE_NAME + " WHERE strftime('%m', " + AgendaContract.Tarea.COLUMN_FECHA + ") = " + "'" + mes +"'", null);


        if (cursorTareas.moveToFirst()){
            do {
                tarea = new TareaViewModel();
                tarea.setIdTarea(cursorTareas.getInt(0));
                tarea.setNombre(cursorTareas.getString(1));
                tarea.setIdMateria(cursorTareas.getInt(2));
                tarea.setIdAgenda(cursorTareas.getInt(3));
                tarea.setTituloTarea(cursorTareas.getString(4));
                tarea.setDescripcionTarea(cursorTareas.getString(5));
                tarea.setFechaTarea(cursorTareas.getString(6));
                tarea.setHoraTarea(cursorTareas.getString(7));
                tarea.setFinalizadaTarea(cursorTareas.getInt(8));
                tarea.setArchivadaTarea(cursorTareas.getInt(9));
                listaTareas.add(tarea);
            } while (cursorTareas.moveToNext());
        }
        cursorTareas.close();
        return listaTareas;
    }



    public ArrayList<MateriaViewModel> mostrarMaterias(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<MateriaViewModel> listaMaterias = new ArrayList<>();
        MateriaViewModel materia = null;
        Cursor cursorMaterias = null;

        cursorMaterias = bD.rawQuery("SELECT * FROM " + AgendaContract.Materia.TABLE_NAME + " ORDER BY " + AgendaContract.Materia.COLUMN_NAME, null);

        if (cursorMaterias.moveToFirst()){
            do {
                materia = new MateriaViewModel();
                materia.setIdMateria(cursorMaterias.getInt(0));
                materia.setIdProfesor(cursorMaterias.getInt(1));
                materia.setIdPeriodo(cursorMaterias.getInt(2));
                materia.setNombreMateria(cursorMaterias.getString(3));
                materia.setAulaMateria(cursorMaterias.getString(4));
                listaMaterias.add(materia);
            }while (cursorMaterias.moveToNext());
        }
        cursorMaterias.close();
        return listaMaterias;
    }

    public ArrayList<MateriaViewModel> mostrarMateriasPorPeriodo(int id){
        bD = bDHelper.getWritableDatabase();

        ArrayList<MateriaViewModel> listaMaterias = new ArrayList<>();
        MateriaViewModel materia = null;
        Cursor cursorMaterias = null;

        cursorMaterias = bD.rawQuery("SELECT * FROM " + AgendaContract.Materia.TABLE_NAME + " WHERE " + AgendaContract.Materia.COLUMN_ID_PERIODO + " = " + id + " ORDER BY " + AgendaContract.Materia.COLUMN_NAME, null);

        if (cursorMaterias.moveToFirst()){
            do {
                materia = new MateriaViewModel();
                materia.setIdMateria(cursorMaterias.getInt(0));
                materia.setIdProfesor(cursorMaterias.getInt(1));
                materia.setIdPeriodo(cursorMaterias.getInt(2));
                materia.setNombreMateria(cursorMaterias.getString(3));
                materia.setAulaMateria(cursorMaterias.getString(4));
                listaMaterias.add(materia);
            }while (cursorMaterias.moveToNext());
        }
        cursorMaterias.close();
        return listaMaterias;
    }

    public ArrayList<ClaseViewModel> mostrarClases(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<ClaseViewModel> listaClases = new ArrayList<>();
        ClaseViewModel clase = null;
        Cursor cursorClases = null;

        cursorClases = bD.rawQuery("SELECT * FROM " + AgendaContract.Clase.TABLE_NAME, null);

        if (cursorClases.moveToFirst()){
            do {
                clase = new ClaseViewModel();
                clase.setIdClase(cursorClases.getInt(0));
                clase.setIdHorario(cursorClases.getInt(1));
                clase.setIdMateria(cursorClases.getInt(2));
                clase.setIdProfesor(cursorClases.getInt(3));
                clase.setAulaClase(cursorClases.getString(4));
                clase.setDiaClase(cursorClases.getString(5));
                clase.setInicioClase(cursorClases.getString(6));
                clase.setFinClase(cursorClases.getString(7));
                clase.setDescripcionClase(cursorClases.getString(8));
                listaClases.add(clase);
            }while (cursorClases.moveToNext());
        }
        cursorClases.close();
        return listaClases;
    }

    public ArrayList<NotaExamenViewModel> mostrarNotasExamenesPorMateria(int id){
        bD = bDHelper.getWritableDatabase();

        ArrayList<NotaExamenViewModel> listaNotasExamenes = new ArrayList<>();
        NotaExamenViewModel notaExamen = null;
        Cursor cursorNotas = null;

        cursorNotas = bD.rawQuery("SELECT * FROM " + AgendaContract.NotaExamen.TABLE_NAME + " WHERE " + AgendaContract.NotaExamen.COLUMN_ID_MATERIA + " = " + id, null);

        if (cursorNotas.moveToFirst()){
            do {
                notaExamen = new NotaExamenViewModel();
                notaExamen.setIdNotaExamen(cursorNotas.getInt(0));
                notaExamen.setIdExamen(cursorNotas.getInt(1));
                notaExamen.setIdMateria(cursorNotas.getInt(2));
                notaExamen.setCalificacion(cursorNotas.getInt(3));
                notaExamen.setPorcentaje(cursorNotas.getInt(4));
                notaExamen.setDescripcionExamen(cursorNotas.getString(5));
                listaNotasExamenes.add(notaExamen);
            }while (cursorNotas.moveToNext());
        }
        cursorNotas.close();
        return listaNotasExamenes;
    }

    public ArrayList<RecordatorioViewModel> mostrarRecordatorios(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<RecordatorioViewModel> listaRecordatorios = new ArrayList<>();
        RecordatorioViewModel recordatorio = null;
        Cursor cursorRecordatorios = null;

        cursorRecordatorios = bD.rawQuery("SELECT * FROM " + AgendaContract.Recordatorio.TABLE_NAME, null);

        if (cursorRecordatorios.moveToFirst()){
            do {
                recordatorio = new RecordatorioViewModel();
                recordatorio.setIdRecordatorio(cursorRecordatorios.getInt(0));
                recordatorio.setNombreRecordatorio(cursorRecordatorios.getString(1));
                recordatorio.setIdAgenda(cursorRecordatorios.getInt(2));
                recordatorio.setFecha(cursorRecordatorios.getString(3));
                recordatorio.setHora(cursorRecordatorios.getString(4));
                recordatorio.setDescripcionRecordatorio(cursorRecordatorios.getString(5));
                listaRecordatorios.add(recordatorio);
            }while (cursorRecordatorios.moveToNext());
        }
        cursorRecordatorios.close();
        return listaRecordatorios;
    }

    public ArrayList<RecordatorioViewModel> mostrarRecordatoriosAyer(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<RecordatorioViewModel> listaRecordatorios = new ArrayList<>();
        RecordatorioViewModel recordatorio = null;
        Cursor cursorRecordatorios = null;

        cursorRecordatorios = bD.rawQuery("SELECT * FROM " + AgendaContract.Recordatorio.TABLE_NAME + " WHERE " + AgendaContract.Recordatorio.COLUMN_FECHA + " = date(datetime('now', 'localtime'), '-1 days')", null);

        if (cursorRecordatorios.moveToFirst()){
            do {
                recordatorio = new RecordatorioViewModel();
                recordatorio.setIdRecordatorio(cursorRecordatorios.getInt(0));
                recordatorio.setNombreRecordatorio(cursorRecordatorios.getString(1));
                recordatorio.setIdAgenda(cursorRecordatorios.getInt(2));
                recordatorio.setFecha(cursorRecordatorios.getString(3));
                recordatorio.setHora(cursorRecordatorios.getString(4));
                recordatorio.setDescripcionRecordatorio(cursorRecordatorios.getString(5));
                listaRecordatorios.add(recordatorio);
            }while (cursorRecordatorios.moveToNext());
        }
        cursorRecordatorios.close();
        return listaRecordatorios;
    }

    public ArrayList<RecordatorioViewModel> mostrarRecordatoriosHoy(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<RecordatorioViewModel> listaRecordatorios = new ArrayList<>();
        RecordatorioViewModel recordatorio = null;
        Cursor cursorRecordatorios = null;

        cursorRecordatorios = bD.rawQuery("SELECT * FROM " + AgendaContract.Recordatorio.TABLE_NAME + " WHERE " + AgendaContract.Recordatorio.COLUMN_FECHA + " = date(datetime('now', 'localtime'))", null);

        if (cursorRecordatorios.moveToFirst()){
            do {
                recordatorio = new RecordatorioViewModel();
                recordatorio.setIdRecordatorio(cursorRecordatorios.getInt(0));
                recordatorio.setNombreRecordatorio(cursorRecordatorios.getString(1));
                recordatorio.setIdAgenda(cursorRecordatorios.getInt(2));
                recordatorio.setFecha(cursorRecordatorios.getString(3));
                recordatorio.setHora(cursorRecordatorios.getString(4));
                recordatorio.setDescripcionRecordatorio(cursorRecordatorios.getString(5));
                listaRecordatorios.add(recordatorio);
            }while (cursorRecordatorios.moveToNext());
        }
        cursorRecordatorios.close();
        return listaRecordatorios;
    }

    public ArrayList<RecordatorioViewModel> mostrarRecordatoriosMañana(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<RecordatorioViewModel> listaRecordatorios = new ArrayList<>();
        RecordatorioViewModel recordatorio = null;
        Cursor cursorRecordatorios = null;

        cursorRecordatorios = bD.rawQuery("SELECT * FROM " + AgendaContract.Recordatorio.TABLE_NAME + " WHERE " + AgendaContract.Recordatorio.COLUMN_FECHA +" = date(datetime('now', 'localtime'), '+1 days')", null);

        if (cursorRecordatorios.moveToFirst()){
            do {
                recordatorio = new RecordatorioViewModel();
                recordatorio.setIdRecordatorio(cursorRecordatorios.getInt(0));
                recordatorio.setNombreRecordatorio(cursorRecordatorios.getString(1));
                recordatorio.setIdAgenda(cursorRecordatorios.getInt(2));
                recordatorio.setFecha(cursorRecordatorios.getString(3));
                recordatorio.setHora(cursorRecordatorios.getString(4));
                recordatorio.setDescripcionRecordatorio(cursorRecordatorios.getString(5));
                listaRecordatorios.add(recordatorio);
            }while (cursorRecordatorios.moveToNext());
        }
        cursorRecordatorios.close();
        return listaRecordatorios;
    }

    public ArrayList<RecordatorioViewModel> mostrarRecordatoriosSieteDias(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<RecordatorioViewModel> listaRecordatorios = new ArrayList<>();
        RecordatorioViewModel recordatorio = null;
        Cursor cursorRecordatorios = null;

        cursorRecordatorios = bD.rawQuery("SELECT * FROM " + AgendaContract.Recordatorio.TABLE_NAME + " WHERE " + AgendaContract.Recordatorio.COLUMN_FECHA +" >= date(datetime('now', 'localtime')) AND " + AgendaContract.Recordatorio.COLUMN_FECHA + " < date(datetime('now', 'localtime'), '+7 days')", null);

        if (cursorRecordatorios.moveToFirst()){
            do {
                recordatorio = new RecordatorioViewModel();
                recordatorio.setIdRecordatorio(cursorRecordatorios.getInt(0));
                recordatorio.setNombreRecordatorio(cursorRecordatorios.getString(1));
                recordatorio.setIdAgenda(cursorRecordatorios.getInt(2));
                recordatorio.setFecha(cursorRecordatorios.getString(3));
                recordatorio.setHora(cursorRecordatorios.getString(4));
                recordatorio.setDescripcionRecordatorio(cursorRecordatorios.getString(5));
                listaRecordatorios.add(recordatorio);
            }while (cursorRecordatorios.moveToNext());
        }
        cursorRecordatorios.close();
        return listaRecordatorios;
    }

    public ArrayList<RecordatorioViewModel> mostrarRecordatoriosEsteMes(String mes){
        bD = bDHelper.getWritableDatabase();

        ArrayList<RecordatorioViewModel> listaRecordatorios = new ArrayList<>();
        RecordatorioViewModel recordatorio = null;
        Cursor cursorRecordatorios = null;

        cursorRecordatorios = bD.rawQuery("SELECT * FROM " + AgendaContract.Recordatorio.TABLE_NAME + " WHERE strftime('%m', " + AgendaContract.Recordatorio.COLUMN_FECHA + ") = " + "'" + mes +"'", null);

        if (cursorRecordatorios.moveToFirst()){
            do {
                recordatorio = new RecordatorioViewModel();
                recordatorio.setIdRecordatorio(cursorRecordatorios.getInt(0));
                recordatorio.setNombreRecordatorio(cursorRecordatorios.getString(1));
                recordatorio.setIdAgenda(cursorRecordatorios.getInt(2));
                recordatorio.setFecha(cursorRecordatorios.getString(3));
                recordatorio.setHora(cursorRecordatorios.getString(4));
                recordatorio.setDescripcionRecordatorio(cursorRecordatorios.getString(5));
                listaRecordatorios.add(recordatorio);
            }while (cursorRecordatorios.moveToNext());
        }
        cursorRecordatorios.close();
        return listaRecordatorios;
    }

    public ArrayList<RecordatorioViewModel> mostrarRecordatoriosSiguienteMes(String mes){
        bD = bDHelper.getWritableDatabase();

        ArrayList<RecordatorioViewModel> listaRecordatorios = new ArrayList<>();
        RecordatorioViewModel recordatorio = null;
        Cursor cursorRecordatorios = null;

        cursorRecordatorios = bD.rawQuery("SELECT * FROM " + AgendaContract.Recordatorio.TABLE_NAME + " WHERE strftime('%m', " + AgendaContract.Recordatorio.COLUMN_FECHA + ") = " + "'" + mes +"'", null);

        if (cursorRecordatorios.moveToFirst()){
            do {
                recordatorio = new RecordatorioViewModel();
                recordatorio.setIdRecordatorio(cursorRecordatorios.getInt(0));
                recordatorio.setNombreRecordatorio(cursorRecordatorios.getString(1));
                recordatorio.setIdAgenda(cursorRecordatorios.getInt(2));
                recordatorio.setFecha(cursorRecordatorios.getString(3));
                recordatorio.setHora(cursorRecordatorios.getString(4));
                recordatorio.setDescripcionRecordatorio(cursorRecordatorios.getString(5));
                listaRecordatorios.add(recordatorio);
            }while (cursorRecordatorios.moveToNext());
        }
        cursorRecordatorios.close();
        return listaRecordatorios;
    }

    public ArrayList<TipoExamenViewModel> mostrarTipoExamen(){
        bD = bDHelper.getWritableDatabase();

        ArrayList<TipoExamenViewModel> listaTipoExamen = new ArrayList<>();
        TipoExamenViewModel tipoExamen = null;
        Cursor cursorTipoExamen = null;

        cursorTipoExamen = bD.rawQuery("SELECT * FROM " + AgendaContract.TipoExamen.TABLE_NAME, null);

        if (cursorTipoExamen.moveToFirst()){
            do {
                tipoExamen = new TipoExamenViewModel();
                tipoExamen.setIdTipoExamen(cursorTipoExamen.getInt(0));
                tipoExamen.setNombreTipoExamen(cursorTipoExamen.getString(1));
                listaTipoExamen.add(tipoExamen);
            }while (cursorTipoExamen.moveToNext());
        }
        cursorTipoExamen.close();
        return listaTipoExamen;
    }

    public ClaseViewModel verClase(int id){
        bD = bDHelper.getWritableDatabase();

        ClaseViewModel clase = null;
        Cursor cursorClases = null;

        cursorClases = bD.rawQuery("SELECT * FROM " + AgendaContract.Clase.TABLE_NAME + " WHERE " + AgendaContract.Clase._ID  + " = " + id + " LIMIT 1", null);

        if (cursorClases.moveToFirst()){
            clase = new ClaseViewModel();
            clase.setIdClase(cursorClases.getInt(0));
            clase.setIdHorario(cursorClases.getInt(1));
            clase.setIdMateria(cursorClases.getInt(2));
            clase.setIdProfesor(cursorClases.getInt(3));
            clase.setAulaClase(cursorClases.getString(4));
            clase.setDiaClase(cursorClases.getString(5));
            clase.setInicioClase(cursorClases.getString(6));
            clase.setFinClase(cursorClases.getString(7));
            clase.setDescripcionClase(cursorClases.getString(8));
        }
        cursorClases.close();
        return clase;
    }

    public ProfesorViewModel verProfesor(int id){
        bD = bDHelper.getWritableDatabase();

        ProfesorViewModel profesor = null;
        Cursor cursorProfesores = null;

        cursorProfesores = bD.rawQuery("SELECT * FROM " + AgendaContract.Profesor.TABLE_NAME + " WHERE " + AgendaContract.Profesor._ID  + " = " + id + " LIMIT 1", null);

        if (cursorProfesores.moveToFirst()){
                profesor = new ProfesorViewModel();
                profesor.setIdProfesor(cursorProfesores.getInt(0));
                profesor.setNombreProfesor(cursorProfesores.getString(1));
                profesor.setApellidoProfesor(cursorProfesores.getString(2));
                profesor.setTelefonoProfesor(cursorProfesores.getString(3));
                profesor.setCorreoProfesor(cursorProfesores.getString(4));
                profesor.setImagenProfesor(cursorProfesores.getString(5));
        }
        cursorProfesores.close();

        return profesor;
    }

    public String obtenerProfesorDeMateria(int id){
        bD = bDHelper.getWritableDatabase();
        String cadena = "";
        Cursor cursor = null;
        cursor = bD.rawQuery("SELECT " + AgendaContract.Profesor.TABLE_NAME + "." + AgendaContract.Profesor.COLUMN_NAME + ", " + AgendaContract.Profesor.TABLE_NAME + "." + AgendaContract.Profesor.COLUMN_APELLIDO + " FROM " + AgendaContract.Materia.TABLE_NAME + ", " + AgendaContract.Profesor.TABLE_NAME + " WHERE " + AgendaContract.Materia.TABLE_NAME + "." +AgendaContract.Materia.COLUMN_ID_PROFESOR + " = " + AgendaContract.Profesor.TABLE_NAME + "." + AgendaContract.Profesor._ID + " AND " + AgendaContract.Materia.TABLE_NAME + "." + AgendaContract.Materia._ID + " = " + id , null );
        if (cursor.moveToFirst()) {
            cadena = cursor.getString(0) + " " + cursor.getString(1);
        }
        cursor.close();
        return cadena;
    }

    public String obtenerPeriodoDeMateria(int id){
        bD = bDHelper.getWritableDatabase();
        String cadena = "";
        Cursor cursor = null;
        cursor = bD.rawQuery("SELECT " + AgendaContract.Periodo.TABLE_NAME + "." + AgendaContract.Periodo.COLUMN_TITULO + " FROM " + AgendaContract.Materia.TABLE_NAME + ", " + AgendaContract.Periodo.TABLE_NAME + " WHERE " + AgendaContract.Materia.TABLE_NAME + "." + AgendaContract.Materia.COLUMN_ID_PERIODO + " = " + AgendaContract.Periodo.TABLE_NAME + "." + AgendaContract.Periodo._ID + " AND " + AgendaContract.Materia.TABLE_NAME + "." + AgendaContract.Materia._ID + " = " + id, null );
        if (cursor.moveToFirst()) {
            cadena = cursor.getString(0);
        }
        cursor.close();
        return cadena;
    }

    public int obtenerPeriodoDeMateriaId(int id){
        bD = bDHelper.getWritableDatabase();
        int cadena = 0;
        Cursor cursor = null;
        cursor = bD.rawQuery("SELECT " + AgendaContract.Materia.COLUMN_ID_PERIODO + " FROM " + AgendaContract.Materia.TABLE_NAME + " WHERE " + AgendaContract.Materia._ID + " = " + id, null );
        if (cursor.moveToFirst()) {
            cadena = cursor.getInt(0);
        }
        cursor.close();
        return cadena;
    }

    public String obtenerMateriaDeClase(int id){
        bD = bDHelper.getWritableDatabase();
        String cadena = "";
        Cursor cursor = null;
        cursor = bD.rawQuery("SELECT " + AgendaContract.Materia.TABLE_NAME + "." + AgendaContract.Materia.COLUMN_NAME + " FROM " + AgendaContract.Clase.TABLE_NAME + ", " + AgendaContract.Materia.TABLE_NAME + " WHERE " + AgendaContract.Clase.TABLE_NAME + "." + AgendaContract.Clase.COLUMN_ID_MATERIA + " = " + AgendaContract.Materia.TABLE_NAME + "." + AgendaContract.Materia._ID + " AND " + AgendaContract.Clase.TABLE_NAME + "." + AgendaContract.Clase._ID + " = " + id, null);
        if (cursor.moveToFirst()){
            cadena = cursor.getString(0);
        }
        cursor.close();
        return cadena;
    }

    public String obtenerMateriaDeExamen(int id){
        bD = bDHelper.getWritableDatabase();
        String cadena = "";
        Cursor cursor = null;
        cursor = bD.rawQuery("SELECT " + AgendaContract.Materia.TABLE_NAME + "." + AgendaContract.Materia.COLUMN_NAME + " FROM " + AgendaContract.Examen.TABLE_NAME + ", " + AgendaContract.Materia.TABLE_NAME + " WHERE " + AgendaContract.Examen.TABLE_NAME + "." + AgendaContract.Examen.COLUMN_ID_MATERIA + " = " + AgendaContract.Materia.TABLE_NAME + "." + AgendaContract.Materia._ID + " AND " + AgendaContract.Examen.TABLE_NAME + "." + AgendaContract.Examen._ID + " = " + id, null);
        if (cursor.moveToFirst()){
            cadena = cursor.getString(0);
        }
        cursor.close();
        return cadena;
    }

    public String obtenerMateriaDeTarea(int id){
        bD = bDHelper.getWritableDatabase();
        String cadena = "";
        Cursor cursor = null;
        cursor = bD.rawQuery("SELECT " + AgendaContract.Materia.TABLE_NAME + "." + AgendaContract.Materia.COLUMN_NAME + " FROM " + AgendaContract.Tarea.TABLE_NAME + ", " + AgendaContract.Materia.TABLE_NAME + " WHERE " + AgendaContract.Tarea.TABLE_NAME + "." + AgendaContract.Tarea.COLUMN_ID_MATERIA + " = " + AgendaContract.Materia.TABLE_NAME + "." + AgendaContract.Materia._ID + " AND " + AgendaContract.Tarea.TABLE_NAME + "." + AgendaContract.Tarea._ID + " = " + id, null);
        if (cursor.moveToFirst()){
            cadena = cursor.getString(0);
        }
        cursor.close();
        return cadena;
    }

    public String obtenerTipoExamenDeExamen(int id){
        bD = bDHelper.getWritableDatabase();
        String cadena = "";
        Cursor cursor = null;
        cursor = bD.rawQuery("SELECT " + AgendaContract.TipoExamen.TABLE_NAME + "." + AgendaContract.TipoExamen.COLUMN_NAME + " FROM " + AgendaContract.Examen.TABLE_NAME + ", " + AgendaContract.TipoExamen.TABLE_NAME + " WHERE " + AgendaContract.Examen.TABLE_NAME + "." + AgendaContract.Examen.COLUMN_ID_TIPO_EXAMEN + " = " + AgendaContract.TipoExamen.TABLE_NAME + "." + AgendaContract.TipoExamen._ID + " AND " + AgendaContract.Examen.TABLE_NAME + "." + AgendaContract.Examen._ID + " = " + id, null);
        if (cursor.moveToFirst()){
            cadena = cursor.getString(0);
        }
        cursor.close();
        return cadena;
    }


    public String obtenerProfesorDeClase(int id){
        bD = bDHelper.getWritableDatabase();
        String cadena = "";
        Cursor cursor = null;
        cursor = bD.rawQuery("SELECT " + AgendaContract.Profesor.TABLE_NAME + "." + AgendaContract.Profesor.COLUMN_NAME + ", " + AgendaContract.Profesor.TABLE_NAME + "." + AgendaContract.Profesor.COLUMN_APELLIDO + " FROM " + AgendaContract.Clase.TABLE_NAME + ", " + AgendaContract.Profesor.TABLE_NAME + " WHERE " + AgendaContract.Clase.TABLE_NAME + "." +AgendaContract.Clase.COLUMN_ID_PROFESOR + " = " + AgendaContract.Profesor.TABLE_NAME + "." + AgendaContract.Profesor._ID + " AND " + AgendaContract.Clase.TABLE_NAME + "." + AgendaContract.Clase._ID + " = " + id , null );
        if (cursor.moveToFirst()) {
            cadena = cursor.getString(0) + " " + cursor.getString(1);
        }
        cursor.close();
        return cadena;
    }

    public int obtenerUltimoIdFilaInsertadaClase(){
        bD = bDHelper.getWritableDatabase();
        int id = 0;
        Cursor cursor = null;
        cursor = bD.rawQuery(" SELECT " + AgendaContract.Clase._ID + " FROM " +AgendaContract.Clase.TABLE_NAME +" ORDER BY " + AgendaContract.Clase._ID + " DESC LIMIT 1;  ", null);
        if (cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public int obtenerUltimoIdFilaInsertadaExamen(){
        bD = bDHelper.getWritableDatabase();
        int id = 0;
        Cursor cursor = null;
        cursor = bD.rawQuery(" SELECT " + AgendaContract.Examen._ID + " FROM " +AgendaContract.Examen.TABLE_NAME +" ORDER BY " + AgendaContract.Examen._ID + " DESC LIMIT 1;  ", null);
        if (cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public int obtenerUltimoIdFilaInsertadaTarea(){
        bD = bDHelper.getWritableDatabase();
        int id = 0;
        Cursor cursor = null;
        cursor = bD.rawQuery(" SELECT " + AgendaContract.Tarea._ID + " FROM " +AgendaContract.Tarea.TABLE_NAME +" ORDER BY " + AgendaContract.Tarea._ID + " DESC LIMIT 1;  ", null);
        if (cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public int obtenerUltimoIdFilaInsertadaRecordatorio(){
        bD = bDHelper.getWritableDatabase();
        int id = 0;
        Cursor cursor = null;
        cursor = bD.rawQuery(" SELECT " + AgendaContract.Recordatorio._ID + " FROM " +AgendaContract.Recordatorio.TABLE_NAME +" ORDER BY " + AgendaContract.Recordatorio._ID + " DESC LIMIT 1;  ", null);
        if (cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public PeriodoViewModel verPeriodo(int id){
        bD = bDHelper.getWritableDatabase();

        PeriodoViewModel periodo = null;
        Cursor cursorPeriodos = null;

        cursorPeriodos = bD.rawQuery("SELECT * FROM " + AgendaContract.Periodo.TABLE_NAME + " WHERE " + AgendaContract.Periodo._ID  + " = " + id + " LIMIT 1", null);

        if (cursorPeriodos.moveToFirst()){
                periodo = new PeriodoViewModel();
                periodo.setIdPeriodo(cursorPeriodos.getInt(0));
                periodo.setTituloPeriodo(cursorPeriodos.getString(1));
                periodo.setInicioPeriodo((cursorPeriodos.getString(2)));
                periodo.setFinPeriodo((cursorPeriodos.getString(3)));
        }
        cursorPeriodos.close();

        return periodo;
    }

    public MateriaViewModel verMateria(int id){
        bD = bDHelper.getWritableDatabase();

        MateriaViewModel materia = null;
        Cursor cursorMaterias = null;

        cursorMaterias = bD.rawQuery("SELECT * FROM " + AgendaContract.Materia.TABLE_NAME + " WHERE " + AgendaContract.Materia._ID  + " = " + id + " LIMIT 1", null);

        if (cursorMaterias.moveToFirst()){
            materia = new MateriaViewModel();
            materia.setIdMateria(cursorMaterias.getInt(0));
            materia.setIdProfesor(cursorMaterias.getInt(1));
            materia.setIdPeriodo(cursorMaterias.getInt(2));
            materia.setNombreMateria(cursorMaterias.getString(3));
            materia.setAulaMateria(cursorMaterias.getString(4));
        }
        cursorMaterias.close();
        return materia;
    }

    public ExamenViewModel verExamen(int id){
        bD = bDHelper.getWritableDatabase();

        ExamenViewModel examen = null;
        Cursor cursorExamenes = null;

        cursorExamenes = bD.rawQuery("SELECT * FROM " + AgendaContract.Examen.TABLE_NAME + " WHERE " + AgendaContract.Examen._ID  + " = " + id + " LIMIT 1", null);

        if (cursorExamenes.moveToFirst()){
            examen = new ExamenViewModel();
            examen.setIdExamen(cursorExamenes.getInt(0));
            examen.setNombreExamen(cursorExamenes.getString(1));
            examen.setIdAgenda(cursorExamenes.getInt(2));
            examen.setIdMateria(cursorExamenes.getInt(3));
            examen.setIdTipoExamen(cursorExamenes.getInt(4));
            examen.setFechaExamen(cursorExamenes.getString(5));
            examen.setHoraExamen(cursorExamenes.getString(6));
            examen.setDescripcionExamen(cursorExamenes.getString(7));
            examen.setAulaExamen(cursorExamenes.getString(8));
        }
        cursorExamenes.close();
        return examen;
    }

    public TareaViewModel verTarea(int id){
        bD = bDHelper.getWritableDatabase();

        TareaViewModel tarea = null;
        Cursor cursorTareas = null;

        cursorTareas = bD.rawQuery("SELECT * FROM " + AgendaContract.Tarea.TABLE_NAME + " WHERE " + AgendaContract.Tarea._ID  + " = " + id + " LIMIT 1", null);

        if (cursorTareas.moveToFirst()){
            tarea = new TareaViewModel();
            tarea.setIdTarea(cursorTareas.getInt(0));
            tarea.setNombre(cursorTareas.getString(1));
            tarea.setIdMateria(cursorTareas.getInt(2));
            tarea.setIdAgenda(cursorTareas.getInt(3));
            tarea.setTituloTarea(cursorTareas.getString(4));
            tarea.setDescripcionTarea(cursorTareas.getString(5));
            tarea.setFechaTarea(cursorTareas.getString(6));
            tarea.setHoraTarea(cursorTareas.getString(7));
            tarea.setFinalizadaTarea(cursorTareas.getInt(8));
            tarea.setArchivadaTarea(cursorTareas.getInt(9));
        }
        cursorTareas.close();
        return tarea;
    }

    public RecordatorioViewModel verRecordatorio(int id) {
        bD = bDHelper.getWritableDatabase();
        RecordatorioViewModel recordatorio = null;
        Cursor cursorRecordatorios = null;

        cursorRecordatorios = bD.rawQuery("SELECT * FROM " + AgendaContract.Recordatorio.TABLE_NAME + " WHERE " + AgendaContract.Recordatorio._ID  + " = " + id + " LIMIT 1", null);

        if (cursorRecordatorios.moveToFirst()){
            recordatorio = new RecordatorioViewModel();
            recordatorio.setIdRecordatorio(cursorRecordatorios.getInt(0));
            recordatorio.setNombreRecordatorio(cursorRecordatorios.getString(1));
            recordatorio.setIdAgenda(cursorRecordatorios.getInt(2));
            recordatorio.setFecha(cursorRecordatorios.getString(3));
            recordatorio.setHora(cursorRecordatorios.getString(4));
            recordatorio.setDescripcionRecordatorio(cursorRecordatorios.getString(5));
        }
        cursorRecordatorios.close();
        return recordatorio;
    }

    public NotaExamenViewModel verNotaExamen(int id){
        bD = bDHelper.getWritableDatabase();
        NotaExamenViewModel notaExamen = null;
        Cursor cursorNotas = null;

        cursorNotas = bD.rawQuery("SELECT * FROM " + AgendaContract.NotaExamen.TABLE_NAME + " WHERE " + AgendaContract.NotaExamen.COLUMN_ID_EXAMEN  + " = " + id + " LIMIT 1", null);

        if (cursorNotas.moveToFirst()){
            notaExamen = new NotaExamenViewModel();
            notaExamen.setIdNotaExamen(cursorNotas.getInt(0));
            notaExamen.setIdExamen(cursorNotas.getInt(1));
            notaExamen.setIdMateria(cursorNotas.getInt(2));
            notaExamen.setCalificacion(cursorNotas.getInt(3));
            notaExamen.setPorcentaje(cursorNotas.getInt(4));
            notaExamen.setDescripcionExamen(cursorNotas.getString(5));
        }
        cursorNotas.close();
        return notaExamen;
    }

    public String actualizar(ProfesorViewModel profesor){
        String[] id = {String.valueOf(profesor.getIdProfesor())};
        ContentValues profesores = new ContentValues();

        profesores.put(AgendaContract.Profesor.COLUMN_NAME, profesor.getNombreProfesor());
        profesores.put(AgendaContract.Profesor.COLUMN_APELLIDO, profesor.getApellidoProfesor());
        profesores.put(AgendaContract.Profesor.COLUMN_TELEFONO, profesor.getTelefonoProfesor());
        profesores.put(AgendaContract.Profesor.COLUMN_CORREO, profesor.getCorreoProfesor());
        profesores.put(AgendaContract.Profesor.COLUMN_IMAGEN, profesor.getImagenProfesor());

        bD.update(AgendaContract.Profesor.TABLE_NAME, profesores, AgendaContract.Profesor._ID + " = ?", id);

        return "Profesor actualizado correctamente";
    }

    public String actualizar(NotaExamenViewModel notaExamen){
        String[] id = {String.valueOf(notaExamen.getIdNotaExamen())};
        ContentValues notas = new ContentValues();

        notas.put(AgendaContract.NotaExamen.COLUMN_ID_EXAMEN, notaExamen.getIdExamen());
        notas.put(AgendaContract.NotaExamen.COLUMN_ID_MATERIA, notaExamen.getIdMateria());
        notas.put(AgendaContract.NotaExamen.COLUMN_CALIFICACION, notaExamen.getCalificacion());
        notas.put(AgendaContract.NotaExamen.COLUMN_PORCENTAJE, notaExamen.getPorcentaje());
        notas.put(AgendaContract.NotaExamen.COLUMN_DESCRIPCION, notaExamen.getDescripcionExamen());

        bD.update(AgendaContract.NotaExamen.TABLE_NAME, notas, AgendaContract.NotaExamen._ID + " = ?", id);

        return "Nota de examen guardada correctamente";
    }

    public String actualizar(PeriodoViewModel periodo){
        String[] id = {String.valueOf(periodo.getIdPeriodo())};
        ContentValues periodos = new ContentValues();

        periodos.put(AgendaContract.Periodo.COLUMN_TITULO, periodo.getTituloPeriodo());
        periodos.put(AgendaContract.Periodo.COLUMN_INICIO, periodo.getInicioPeriodo());
        periodos.put(AgendaContract.Periodo.COLUMN_FIN, periodo.getFinPeriodo());

        bD.update(AgendaContract.Periodo.TABLE_NAME, periodos, AgendaContract.Periodo._ID + " = ?", id);

        return "Periodo actualizado correctamente";
    }

    public String actualizar(MateriaViewModel materia){
        String[] id = {String.valueOf(materia.getIdMateria())};
        ContentValues materias = new ContentValues();

        materias.put(AgendaContract.Materia.COLUMN_ID_PROFESOR, materia.getIdProfesor());
        materias.put(AgendaContract.Materia.COLUMN_ID_PERIODO, materia.getIdPeriodo());
        materias.put(AgendaContract.Materia.COLUMN_NAME, materia.getNombreMateria());
        materias.put(AgendaContract.Materia.COLUMN_AULA, materia.getAulaMateria());

        bD.update(AgendaContract.Materia.TABLE_NAME, materias,AgendaContract.Materia._ID + " = ?", id);

        return "Materia actualizada correctamente";
    }

    public String actualizar(ClaseViewModel clase){
        String[] id = {String.valueOf(clase.getIdClase())};
        ContentValues clases = new ContentValues();

        clases.put(AgendaContract.Clase.COLUMN_ID_HORARIO, clase.getIdHorario());
        clases.put(AgendaContract.Clase.COLUMN_ID_MATERIA, clase.getIdMateria());
        clases.put(AgendaContract.Clase.COLUMN_ID_PROFESOR, clase.getIdProfesor());
        clases.put(AgendaContract.Clase.COLUMN_AULA, clase.getAulaClase());
        clases.put(AgendaContract.Clase.COLUMN_DIA, clase.getDiaClase());
        clases.put(AgendaContract.Clase.COLUMN_INICIO, clase.getInicioClase());
        clases.put(AgendaContract.Clase.COLUMN_FIN, clase.getFinClase());
        clases.put(AgendaContract.Clase.COLUMN_DESCRIPCION, clase.getDescripcionClase());

        bD.update(AgendaContract.Clase.TABLE_NAME, clases,AgendaContract.Clase._ID + " = ?", id);

        return "Clase actualizada correctamente";
    }

    public String actualizar(ExamenViewModel examen){
        String[] id = {String.valueOf(examen.getIdExamen())};
        ContentValues examenes = new ContentValues();

        examenes.put(AgendaContract.Examen.COLUMN_NAME, examen.getNombreExamen());
        examenes.put(AgendaContract.Examen.COLUMN_ID_AGENDA, examen.getIdAgenda());
        examenes.put(AgendaContract.Examen.COLUMN_ID_MATERIA, examen.getIdMateria());
        examenes.put(AgendaContract.Examen.COLUMN_ID_TIPO_EXAMEN, examen.getIdTipoExamen());
        examenes.put(AgendaContract.Examen.COLUMN_FECHA, examen.getFechaExamen());
        examenes.put(AgendaContract.Examen.COLUMN_HORA, examen.getHoraExamen());
        examenes.put(AgendaContract.Examen.COLUMN_DESCRIPCION, examen.getDescripcionExamen());
        examenes.put(AgendaContract.Examen.COLUMN_AULA, examen.getAulaExamen());

        bD.update(AgendaContract.Examen.TABLE_NAME, examenes,AgendaContract.Examen._ID + " = ?", id);

        return "Examen actualizado correctamente";
    }

    public String actualizar(TareaViewModel tarea){
        String[] id = {String.valueOf(tarea.getIdTarea())};
        ContentValues tareas = new ContentValues();

        tareas.put(AgendaContract.Tarea.COLUMN_NAME, tarea.getNombre());
        tareas.put(AgendaContract.Tarea.COLUMN_ID_MATERIA, tarea.getIdMateria());
        tareas.put(AgendaContract.Tarea.COLUMN_ID_AGENDA, tarea.getIdAgenda());
        tareas.put(AgendaContract.Tarea.COLUMN_TITULO, tarea.getTituloTarea());
        tareas.put(AgendaContract.Tarea.COLUMN_DESCRIPCION, tarea.getDescripcionTarea());
        tareas.put(AgendaContract.Tarea.COLUMN_FECHA, tarea.getFechaTarea());
        tareas.put(AgendaContract.Tarea.COLUMN_HORA, tarea.getHoraTarea());
        tareas.put(AgendaContract.Tarea.COLUMN_FINALIZADA, tarea.getFinalizadaTarea());
        tareas.put(AgendaContract.Tarea.COLUMN_ARCHIVADA, tarea.getArchivadaTarea());

        bD.update(AgendaContract.Tarea.TABLE_NAME, tareas,AgendaContract.Tarea._ID + " = ?", id);

        return "Tarea actualizada correctamente";
    }

    public String actualizar(RecordatorioViewModel recordatorio){
        String[] id = {String.valueOf(recordatorio.getIdRecordatorio())};
        ContentValues recordatorios = new ContentValues();

        recordatorios.put(AgendaContract.Recordatorio.COLUMN_NAME, recordatorio.getNombreRecordatorio());
        recordatorios.put(AgendaContract.Recordatorio.COLUMN_ID_AGENDA, recordatorio.getIdAgenda());
        recordatorios.put(AgendaContract.Recordatorio.COLUMN_FECHA, recordatorio.getFecha());
        recordatorios.put(AgendaContract.Recordatorio.COLUMN_HORA, recordatorio.getHora());
        recordatorios.put(AgendaContract.Recordatorio.COLUMN_DESCRIPCION, recordatorio.getDescripcionRecordatorio());

        bD.update(AgendaContract.Recordatorio.TABLE_NAME, recordatorios,AgendaContract.Recordatorio._ID + " = ?", id);

        return "Recordatorio actualizado correctamente";
    }

    public String eliminar(ProfesorViewModel profesor){
        String mensaje = "Profesor eliminado";
        long contador = 0;

        String[] id = {String.valueOf(profesor.getIdProfesor())};
        bD.delete(AgendaContract.Profesor.TABLE_NAME, AgendaContract.Profesor._ID + " = ?", id);
        return mensaje;
    }

    public String eliminar(PeriodoViewModel periodo){
        String mensaje = "Periodo eliminado";
        long contador = 0;

        String[] id = {String.valueOf(periodo.getIdPeriodo())};
        bD.delete(AgendaContract.Periodo.TABLE_NAME, AgendaContract.Periodo._ID + " = ?", id);
        return mensaje;
    }

    public String eliminar(MateriaViewModel materia){
        String mensaje = "Materia eliminada";
        long contado = 0;

        String[] id = {String.valueOf(materia.getIdMateria())};
        bD.delete(AgendaContract.Materia.TABLE_NAME,AgendaContract.Materia._ID + " = ?", id);
        return mensaje;
    }

    public String eliminar(ClaseViewModel clase){
        String mensaje = "Clase eliminada";
        long contado = 0;

        String[] id = {String.valueOf(clase.getIdClase())};
        bD.delete(AgendaContract.Clase.TABLE_NAME,AgendaContract.Clase._ID + " = ?", id);
        return mensaje;
    }

    public String eliminar(ExamenViewModel examen){
        String mensaje = "Examen eliminado";
        long contado = 0;

        String[] id = {String.valueOf(examen.getIdExamen())};
        bD.delete(AgendaContract.Examen.TABLE_NAME,AgendaContract.Examen._ID + " = ?", id);
        return mensaje;
    }

    public String eliminar(TareaViewModel tarea){
        String mensaje = "Tarea eliminada";
        long contado = 0;

        String[] id = {String.valueOf(tarea.getIdTarea())};
        bD.delete(AgendaContract.Tarea.TABLE_NAME,AgendaContract.Tarea._ID + " = ?", id);
        return mensaje;
    }

    public String eliminar(RecordatorioViewModel recordatorio){
        String mensaje = "Recordatorio eliminado";
        long contado = 0;

        String[] id = {String.valueOf(recordatorio.getIdRecordatorio())};
        bD.delete(AgendaContract.Recordatorio.TABLE_NAME,AgendaContract.Recordatorio._ID + " = ?", id);
        return mensaje;
    }

    public String eliminar(NotaExamenViewModel notaExamen){
        String mensaje = "Nota de examen eliminada";
        long contado = 0;

        String[] id = {String.valueOf(notaExamen.getIdExamen())};
        bD.delete(AgendaContract.NotaExamen.TABLE_NAME,AgendaContract.NotaExamen.COLUMN_ID_EXAMEN + " = ?", id);
        return mensaje;
    }

    public String llenarBD(){

        final String[] nombreAgenda = {"Agenda predeterminada"};

        final String[] nombreHorario = {"Horario predeterminado"};

        final String[] tituloPeriodo = {"CICLO I-2022", "CICLO II-2022"};
        final String[] inicioPeriodo = {"2022-02-20", "2022-06-20"};
        final String[] finPeriodo = {"2022-07-20", "2022-12-20"};

        final String[] nombreProfesor = {"Oscar", "Edwin", "Braian", "Gonzalo"};
        final String[] apellidoProfesor = {"Elías", "Chávez", "Pineda", "Ortiz"};
        final String[] telefonoProfesor = {"22224444", "55552222", "77778888", "79460607"};
        final String[] correoProfesor = {"em19009@ues.edu.sv","cz18012@ues.edu.sv","pr17013@ues.edu.sv", "om18026@ues.edu.sv"};

        final int[] idProfesorMateria = {1, 2, 3, 4, 1, 2};
        final int[] idPeriodoMateria = {1, 1, 1, 2, 2, 2};
        final String[] nombreMateria = {"Matematica I", "Introduccion a la informatica", "Psicologia", "Fisica I", "Matematica II", "Programacion I"};
        final String[] aulaMateria = {"B13","C12","A23","D12","B15","B22"};

        final String[] nombreTipoExamen = {"Examen parcial", "Examen corto", "Evaluado de laboratorio", "Examen oral", "Examen escrito", "Proyecto/Tarea"};

        final int[] idHorarioClase = {1, 1};
        final int[] idMateriaClase = {1, 3};
        final int[] idProfesorClase = {4 , 2};
        final String[] aulaClase = {"B13", "C12"};
        final String[] diaClase = {"Lunes", "Jueves"};
        final String[] inicioClase = {"8:05 AM", "3:05 PM"};
        final String[] finClase = {"9:45 AM", "4:45 PM"};
        final String[] descripcionClase = {"Clase de matematica I en la mañana", "Clase de Psicologia en la tarde"};

        abrir();
        bD.execSQL(SQL_DELETE_AGENDA);
        bD.execSQL(SQL_DELETE_RECORDATORIO);
        bD.execSQL(SQL_DELETE_PROFESOR);
        bD.execSQL(SQL_DELETE_MATERIA);
        bD.execSQL(SQL_DELETE_HORARIO);
        bD.execSQL(SQL_DELETE_CLASE);
        bD.execSQL(SQL_DELETE_TAREA);
        bD.execSQL(SQL_DELETE_TIPO_EXAMEN);
        bD.execSQL(SQL_DELETE_EXAMEN);
        bD.execSQL(SQL_DELETE_PERIODO);
        bD.execSQL(SQL_DELETE_NOTA_EXAMEN);
        bD.execSQL(SQL_CREATE_AGENDA);
        bD.execSQL(SQL_CREATE_RECORDATORIO);
        bD.execSQL(SQL_CREATE_PROFESOR);
        bD.execSQL(SQL_CREATE_MATERIA);
        bD.execSQL(SQL_CREATE_HORARIO);
        bD.execSQL(SQL_CREATE_CLASE);
        bD.execSQL(SQL_CREATE_TAREA);
        bD.execSQL(SQL_CREATE_TIPO_EXAMEN);
        bD.execSQL(SQL_CREATE_EXAMEN);
        bD.execSQL(SQL_CREATE_PERIODO);
        bD.execSQL(SQL_CREATE_NOTA_EXAMEN);

        Agenda agenda = new Agenda();
        agenda.setNombreAgenda(nombreAgenda[0]);
        insertar(agenda);

        HorarioViewModel horario = new HorarioViewModel();
        horario.setNombreHorario(nombreHorario[0]);
        insertar(horario);

        PeriodoViewModel periodo = new PeriodoViewModel();
        for (int i = 0; i < 2; i++) {
            periodo.setTituloPeriodo(tituloPeriodo[i]);
            periodo.setInicioPeriodo(inicioPeriodo[i]);
            periodo.setFinPeriodo(finPeriodo[i]);
            insertar(periodo);
        }

        ProfesorViewModel profesor = new ProfesorViewModel();
        for (int i = 0; i < 4; i++) {
            profesor.setNombreProfesor(nombreProfesor[i]);
            profesor.setApellidoProfesor(apellidoProfesor[i]);
            profesor.setTelefonoProfesor(telefonoProfesor[i]);
            profesor.setCorreoProfesor(correoProfesor[i]);
            insertar(profesor);
        }

        MateriaViewModel materia = new MateriaViewModel();
        for (int i = 0; i< 6; i++){
            materia.setIdProfesor(idProfesorMateria[i]);
            materia.setIdPeriodo(idPeriodoMateria[i]);
            materia.setNombreMateria(nombreMateria[i]);
            materia.setAulaMateria(aulaMateria[i]);
            insertar(materia);
        }

        TipoExamenViewModel tipoExamen = new TipoExamenViewModel();
        for (int i = 0; i< 6; i++){
            tipoExamen.setNombreTipoExamen(nombreTipoExamen[i]);
            insertar(tipoExamen);
        }

        ClaseViewModel clase = new ClaseViewModel();
        for (int i = 0; i < 2; i++){
            clase.setIdHorario(idHorarioClase[i]);
            clase.setIdMateria(idMateriaClase[i]);
            clase.setIdProfesor(idProfesorClase[i]);
            clase.setAulaClase(aulaClase[i]);
            clase.setDiaClase(diaClase[i]);
            clase.setInicioClase(inicioClase[i]);
            clase.setFinClase(finClase[i]);
            clase.setDescripcionClase(descripcionClase[i]);
            insertar(clase);
        }
        cerrar();
        return "Guardado correctamente";
    }

    private Data guardarData(String titulo, String detalle, String ticker){
        return new Data.Builder()
                .putString("TITULO", titulo)
                .putString("DETALLE", detalle)
                .putString("TICKER", ticker).build();
    }

    private void eliminarNotificacion(String tag){
        WorkManager.getInstance(context.getApplicationContext()).cancelAllWorkByTag(tag);
        Toast.makeText(context.getApplicationContext(), "Notificacion eliminada", Toast.LENGTH_SHORT).show();
    }
}

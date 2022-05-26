package sv.ues.fia.eisi.agendaestudiantil.clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.clases.Agenda;
import sv.ues.fia.eisi.agendaestudiantil.clases.AgendaContract;
import sv.ues.fia.eisi.agendaestudiantil.ui.profesor.ProfesorViewModel;

public class BD {
    private static final String [] camposNotificacion = new String[]{"id_agenda", "id_dia", "con_notificacion", "hora"};
    private static final String [] camposAgenda = new String[]{"id_agenda", "nombre", "notificacion"};
    private static final String [] camposRecordatorio = new String[]{"id_recordatorio","id_agenda","fecha", "descripcion_recordatorio"};
    private static final String [] camposClase = new String[]{"id_clase","id_horario","id_materia", "dia", "inicio", "fin", "descripcion_clase"};
    private static final String [] camposHorario = new String[]{"id_horario", "notificaciones", "notificacion_clase"};
    private static final String [] camposMateria = new String[]{"id_materia", "id_profesor", "nombre_materia", "aula_materia"};
    private static final String [] camposTarea = new String[]{"id_tarea","id_materia","id_agenda","titulo_tarea", "descripcion_tarea", "fecha_tarea", "finalizada", "archivada"};
    private static final String [] camposExamen = new String[]{"id_examen", "id_agenda", "id_tipo_examen", "fecha_examen", "hora_examen", "descripcion_examen","aula_examen"};
    private static final String [] camposTipoExamen = new String[]{"id_tipo_examen", "nombre_tipo_examen"};
    private static final String [] camposEstudio = new String[]{"id_estudio", "dia", "inicio", "fin", "descripcion_estudio"};
    private static final String [] camposNotaExamen = new String[]{"id_nota_examen", "id_examen", "id_periodo", "calificaion", "fecha", "porcentaje", "descripcion_nota"};
    private static final String [] camposProfesor = new String[]{"id_profesor, nombre, apellido, telefono,correo"};
    private static final String [] camposArchivo = new String[]{"id_archivo", "id_tarea", "id_profesor", "id_examen", "nombre_archivo", "extension", "archivo", "tamaño", "tipo_archivo"};
    private static final String [] camposPeriodo = new String[]{"id_periodo","titulo","inicio", "fin"};

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

    private static final String SQL_CREATE_NOTIFICACION
            = "CREATE TABLE "
            + AgendaContract.Notificacion.TABLE_NAME + " ("
            + AgendaContract.Notificacion._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Notificacion.COLUMN_ID_AGENDA + " INTEGER NOT NULL, "
            + AgendaContract.Notificacion.COLUMN_DIA + " TEXT(10) NOT NULL, "
            + AgendaContract.Notificacion.COLUMN_NOTIFICACION + " INTEGER DEFAULT 0, "
            + AgendaContract.Notificacion.COLUMN_HORA + " TEXT NOT NULL, FOREIGN KEY ( "
            + AgendaContract.Notificacion.COLUMN_ID_AGENDA + " ) REFERENCES "
            + AgendaContract.Agenda.TABLE_NAME + " ("
            + AgendaContract.Agenda._ID + ") ON DELETE CASCADE)";


    private static final String SQL_DELETE_NOTIFICACION
            = "DROP TABLE IF EXISTS " + AgendaContract.Notificacion.TABLE_NAME;

    private static final String SQL_CREATE_RECORDATORIO
            = "CREATE TABLE "
            + AgendaContract.Recordatorio.TABLE_NAME + " ("
            + AgendaContract.Recordatorio._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Recordatorio.COLUMN_ID_AGENDA + " INTEGER NOT NULL, "
            + AgendaContract.Recordatorio.COLUMN_FECHA + " TEXT NOT NULL, "
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
            + AgendaContract.Materia.COLUMN_NAME + " TEXT(50) NOT NULL, "
            + AgendaContract.Materia.COLUMN_AULA + " TEXT(20), FOREIGN KEY ( "
            + AgendaContract.Materia.COLUMN_ID_PROFESOR + " ) REFERENCES "
            + AgendaContract.Profesor.TABLE_NAME + " ("
            + AgendaContract.Profesor._ID + ") ON DELETE RESTRICT)";

    private static final String SQL_DELETE_MATERIA
            = "DROP TABLE IF EXISTS " + AgendaContract.Materia.TABLE_NAME;

    private static final String SQL_CREATE_HORARIO
            = "CREATE TABLE "
            + AgendaContract.Horario.TABLE_NAME + " ("
            + AgendaContract.Horario._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Horario.COLUMN_NOTIFICACIONES + " INTEGER DEFAULT 0, "
            + AgendaContract.Horario.COLUMN_NOTIFICACION_CLASE+ " INTEGER NOT NULL)";

    private static final String SQL_DELETE_HORARIO
            = "DROP TABLE IF EXISTS " + AgendaContract.Horario.TABLE_NAME;

    private static final String SQL_CREATE_CLASE
            = "CREATE TABLE "
            + AgendaContract.Clase.TABLE_NAME + " ("
            + AgendaContract.Clase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Clase.COLUMN_ID_HORARIO + " INTEGER NOT NULL, "
            + AgendaContract.Clase.COLUMN_ID_MATERIA + " INTEGER NOT NULL, "
            + AgendaContract.Clase.COLUMN_DIA + " TEXT NOT NULL, "
            + AgendaContract.Clase.COLUMN_INICIO + " TEXT NOT NULL, "
            + AgendaContract.Clase.COLUMN_FIN + " TEXT NOT NULL, "
            + AgendaContract.Clase.COLUMN_DESCRIPCION + " TEXT(250), FOREIGN KEY ( "
            + AgendaContract.Clase.COLUMN_ID_HORARIO + " ) REFERENCES "
            + AgendaContract.Horario.TABLE_NAME + " ("
            + AgendaContract.Horario._ID + ") ON DELETE CASCADE, FOREIGN KEY ( "
            + AgendaContract.Clase.COLUMN_ID_MATERIA + " ) REFERENCES "
            + AgendaContract.Materia.TABLE_NAME + " ("
            + AgendaContract.Materia._ID + ") ON DELETE CASCADE)";

    private static final String SQL_DELETE_CLASE
            = "DROP TABLE IF EXISTS " + AgendaContract.Clase.TABLE_NAME;

    private static final String SQL_CREATE_TAREA
            = "CREATE TABLE "
            + AgendaContract.Tarea.TABLE_NAME + " ("
            + AgendaContract.Tarea._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Tarea.COLUMN_ID_MATERIA + " INTEGER NOT NULL, "
            + AgendaContract.Tarea.COLUMN_ID_AGENDA + " INTEGER NOT NULL, "
            + AgendaContract.Tarea.COLUMN_TITULO + " TEXT(100) NOT NULL, "
            + AgendaContract.Tarea.COLUMN_DESCRIPCION + " TEXT(250), "
            + AgendaContract.Tarea.COLUMN_FECHA + " TEXT NOT NULL, "
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
            + AgendaContract.Examen.COLUMN_ID_AGENDA + " INTEGER NOT NULL, "
            + AgendaContract.Examen.COLUMN_ID_TIPO_EXAMEN + " INTEGER NOT NULL, "
            + AgendaContract.Examen.COLUMN_FECHA + " TEXT NOT NULL, "
            + AgendaContract.Examen.COLUMN_HORA + " TEXT NOT NULL, "
            + AgendaContract.Examen.COLUMN_DESCRIPCION + " TEXT(250), "
            + AgendaContract.Examen.COLUMN_AULA + " TEXT(20), FOREIGN KEY ( "
            + AgendaContract.Examen.COLUMN_ID_AGENDA + " ) REFERENCES "
            + AgendaContract.Agenda.TABLE_NAME + " ("
            + AgendaContract.Agenda._ID + ") ON DELETE CASCADE, FOREIGN KEY ( "
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
            + AgendaContract.NotaExamen.COLUMN_ID_PERIODO + " INTEGER NOT NULL, "
            + AgendaContract.NotaExamen.COLUMN_CALIFICACION + " INTEGER NOT NULL, "
            + AgendaContract.NotaExamen.COLUMN_FECHA + " INTEGER NOT NULL, "
            + AgendaContract.NotaExamen.COLUMN_PORCENTAJE + " INTEGER NOT NULL, "
            + AgendaContract.NotaExamen.COLUMN_DESCRIPCION + " TEXT(250), FOREIGN KEY ( "
            + AgendaContract.NotaExamen.COLUMN_ID_EXAMEN + " ) REFERENCES "
            + AgendaContract.Examen.TABLE_NAME + " ("
            + AgendaContract.Examen._ID + ") ON DELETE CASCADE, FOREIGN KEY ( "
            + AgendaContract.NotaExamen.COLUMN_ID_PERIODO+ " ) REFERENCES "
            + AgendaContract.Periodo.TABLE_NAME + " ("
            + AgendaContract.Periodo._ID + ") ON DELETE CASCADE)";

    private static final String SQL_DELETE_NOTA_EXAMEN
            = "DROP TABLE IF EXISTS " + AgendaContract.NotaExamen.TABLE_NAME;

    private static final String SQL_CREATE_ESTUDIO
            = "CREATE TABLE "
            + AgendaContract.Estudio.TABLE_NAME + " ("
            + AgendaContract.Estudio._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Estudio.COLUMN_DIA + " TEXT NOT NULL, "
            + AgendaContract.Estudio.COLUMN_INICIO + " TEXT NOT NULL, "
            + AgendaContract.Estudio.COLUMN_FIN + " TEXT NOT NULL, "
            + AgendaContract.Estudio.COLUMN_DESCRIPCION + " TEXT(250))";

    private static final String SQL_DELETE_ESTUDIO
            = "DROP TABLE IF EXISTS " + AgendaContract.Estudio.TABLE_NAME;

    private static final String SQL_CREATE_ARCHIVO
            = "CREATE TABLE "
            + AgendaContract.Archivo.TABLE_NAME + " ("
            + AgendaContract.Archivo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AgendaContract.Archivo.COLUMN_ID_TAREA + " INTEGER NOT NULL, "
            + AgendaContract.Archivo.COLUMN_ID_PROFESOR+ " INTEGER NOT NULL, "
            + AgendaContract.Archivo.COLUMN_ID_EXAMEN + " INTEGER NOT NULL, "
            + AgendaContract.Archivo.COLUMN_NAME + " TEXT(250) NOT NULL, "
            + AgendaContract.Archivo.COLUMN_ARCHIVO + " TEXT NOT NULL, FOREIGN KEY ( "
            + AgendaContract.Archivo.COLUMN_ID_TAREA+ " ) REFERENCES "
            + AgendaContract.Tarea.TABLE_NAME + " ("
            + AgendaContract.Tarea._ID + ") ON DELETE CASCADE, FOREIGN KEY ( "
            + AgendaContract.Archivo.COLUMN_ID_PROFESOR+ " ) REFERENCES "
            + AgendaContract.Profesor.TABLE_NAME + " ("
            + AgendaContract.Profesor._ID + ") ON DELETE CASCADE, FOREIGN KEY ( "
            + AgendaContract.Archivo.COLUMN_ID_EXAMEN+ " ) REFERENCES "
            + AgendaContract.Examen.TABLE_NAME + " ("
            + AgendaContract.Examen._ID + ") ON DELETE CASCADE)";


    private static final String SQL_DELETE_ARCHIVO
            = "DROP TABLE IF EXISTS " + AgendaContract.Archivo.TABLE_NAME;

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
                bD.execSQL(SQL_CREATE_NOTIFICACION);
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
                bD.execSQL(SQL_CREATE_ARCHIVO);
                bD.execSQL(SQL_CREATE_ESTUDIO);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        public void onUpgrade(SQLiteDatabase bD, int oldVersion, int newVersion){
            bD.execSQL(SQL_DELETE_AGENDA);
            bD.execSQL(SQL_DELETE_NOTIFICACION);
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
            bD.execSQL(SQL_DELETE_ARCHIVO);
            bD.execSQL(SQL_DELETE_ESTUDIO);
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
}

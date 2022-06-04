package sv.ues.fia.eisi.agendaestudiantil.clases;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import sv.ues.fia.eisi.agendaestudiantil.InicioActivity;
import sv.ues.fia.eisi.agendaestudiantil.R;

public class Notificacion extends Worker {
    public Notificacion(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static void guardarNotificacion(long duracion, Data data, String tag){
        OneTimeWorkRequest notificacion = new OneTimeWorkRequest.Builder(Notificacion.class)
                .setInitialDelay(duracion, TimeUnit.MILLISECONDS).addTag(tag)
                .setInputData(data).build();
        WorkManager instance = WorkManager.getInstance();
        instance.enqueue(notificacion);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void crearNotificacion(String t, String d, String ti){
        String id = "message";
        NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),id);

        NotificationChannel nc = new NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH);
        nc.setDescription("Notificacion");
        nc.setShowBadge(true);
        assert nm != null;
        nm.createNotificationChannel(nc);

        Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_ONE_SHOT);

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(t)
                .setTicker(ti)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(d)
                .setContentIntent(pendingIntent)
                .setContentInfo("nuevo");

        Random random = new Random();
        int idNotify = random.nextInt(8000);

        assert nm != null;
        nm.notify(idNotify, builder.build());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {
        String titulo = getInputData().getString("TITULO");
        String detalle = getInputData().getString("DETALLE");
        String ticker = getInputData().getString("TICKER");
        int id = (int) getInputData().getLong("ID", 0);


        crearNotificacion(titulo,detalle,ticker);
        return Result.success();
    }
}

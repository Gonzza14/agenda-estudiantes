package sv.ues.fia.eisi.agendaestudiantil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import sv.ues.fia.eisi.agendaestudiantil.clases.BD;
import sv.ues.fia.eisi.agendaestudiantil.databinding.ActivityInicioBinding;

public class InicioActivity extends AppCompatActivity {

    private static final String TAG = "Fallo";
    public static Context contextOfApplicaction;
    public static Context getContextOfApplicaction(){
        return contextOfApplicaction;
    }

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioBinding binding;
    private ImageView imageView;
    private TextView lblNombreUsuario, lblCorreoUsuario, logout;
    private GoogleSignInClient mGoogleSignInClient;
    private BD helper;
    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        helper = new BD(this);
        helper.abrir();
        helper.cerrar();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        contextOfApplicaction = getApplicationContext();
        binding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        NavigationView navigationViewA = findViewById(R.id.nav_view);
        imageView = navigationViewA.getHeaderView(0).findViewById(R.id.imageView);
        lblNombreUsuario = navigationViewA.getHeaderView(0).findViewById(R.id.lblNombreUsuario);
        lblCorreoUsuario = navigationViewA.getHeaderView(0).findViewById(R.id.lblCorreoUsuario);




        setSupportActionBar(binding.appBarInicio.toolbar);

        /*binding.appBarInicio.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_agenda, R.id.nav_calendario, R.id.nav_tarea, R.id.nav_examen, R.id.nav_recordatorio, R.id.nav_profesor, R.id.nav_periodo, R.id.nav_materia, R.id.nav_horario, R.id.nav_ver_notas, R.id.nav_graficos)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Task<GoogleSignInAccount> opr = mGoogleSignInClient.silentSignIn();
        if (opr.isSuccessful()) {
            GoogleSignInAccount signInAccount = opr.getResult();
            lblNombreUsuario.setText(signInAccount.getDisplayName());
            lblCorreoUsuario.setText(signInAccount.getEmail());

            Glide.with(this).load(signInAccount.getPhotoUrl()).into(imageView);
            //imageView.setImageURI(signInAccount.getPhotoUrl());
            /*Uri imgUrl = signInAccount.getPhotoUrl();
            Picasso.get().load(imgUrl).fit().into(imageView);*/


        }else {
            opr.addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
                @Override
                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                    try {
                        GoogleSignInAccount signInAccount = task.getResult(ApiException.class);
                        lblNombreUsuario.setText(signInAccount.getDisplayName());
                        lblCorreoUsuario.setText(signInAccount.getEmail());
                        Glide.with(getApplicationContext()).load(signInAccount.getPhotoUrl()).into(imageView);
                        //imageView.setImageURI(signInAccount.getPhotoUrl());
                    }catch (ApiException apiException) {
                        Log.w(TAG, "signInResult:failed code=" + apiException.getStatusCode());
                        goLogInScreen();
                    }
                }
            });
        }
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                goLogInScreen();
                            }else {
                                Toast.makeText(getApplicationContext(), "No se pudo cerrar sesion", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        if (id == R.id.llenar_bd){
            helper.abrir();
            String toast = helper.llenarBD();
            helper.cerrar();
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
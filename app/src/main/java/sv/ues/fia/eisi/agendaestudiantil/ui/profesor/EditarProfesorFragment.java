package sv.ues.fia.eisi.agendaestudiantil.ui.profesor;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;
import sv.ues.fia.eisi.agendaestudiantil.R;
import sv.ues.fia.eisi.agendaestudiantil.clases.BD;

public class EditarProfesorFragment extends Fragment {

    private CircleImageView imgProfesor;
    private EditText txtNombre, txtApellido, txtTelefono, txtCorreo;
    private FloatingActionButton btnGuardar;
    private ProfesorViewModel profesor;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    private String[] cameraPermissions;
    private String[] storagePermissions;

    private Uri imageUri;

    private int id = 0;

    private BD helper;

    public EditarProfesorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("picUri", imageUri);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            imageUri= savedInstanceState.getParcelable("picUri");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profesor = new ViewModelProvider(this).get(ProfesorViewModel.class);

        return inflater.inflate(R.layout.fragment_editar_profesor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgProfesor = (CircleImageView) view.findViewById(R.id.imgProfesor);
        imgProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePickDialog();
            }
        });

        helper = new BD(view.getContext());

        txtNombre = (EditText) view.findViewById(R.id.txtNombre);
        txtApellido = (EditText) view.findViewById(R.id.txtApellido);
        txtTelefono = (EditText) view.findViewById(R.id.txtTelefono);
        txtCorreo = (EditText) view.findViewById(R.id.txtCorreo);

        id = (int) getArguments().getInt("ID");
        profesor = helper.verProfesor(id);

        if (profesor != null){
            txtNombre.setText(profesor.getNombreProfesor());
            txtApellido.setText(profesor.getApellidoProfesor());
            txtTelefono.setText(profesor.getTelefonoProfesor());
            txtCorreo.setText(profesor.getCorreoProfesor());
            imageUri = Uri.parse(profesor.getImagenProfesor());
            imgProfesor.setImageURI(Uri.parse(profesor.getImagenProfesor()));
        }

        btnGuardar = (FloatingActionButton) view.findViewById(R.id.btnGuardarProfesor);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profesor.setNombreProfesor(txtNombre.getText().toString());
                profesor.setApellidoProfesor(txtApellido.getText().toString());
                profesor.setTelefonoProfesor(txtTelefono.getText().toString());
                profesor.setCorreoProfesor(txtCorreo.getText().toString());
                profesor.setImagenProfesor(imageUri.toString());

                helper.abrir();
                String estado = helper.actualizar(profesor);
                helper.cerrar();

                Toast.makeText(view.getContext(),estado,Toast.LENGTH_SHORT).show();

                Navigation.findNavController(view).popBackStack();
            }
        });

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    private void imagePickDialog(){
        String[] options = {"Camara", "Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Seleccionar imagen");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    if (!checkCameraPermission())
                        requestCameraPermission();
                    else
                        pickFromCamera();
                }
                else if (i == 1){
                    if (!checkStoragePermission())
                        requestStoragePermission();
                    else
                        pickFromGallery();
                }
            }
        });
        builder.create().show();
    }
    private void pickFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        galeriaResultLauncher.launch(intent.createChooser(intent,"Seleccione la aplicacion"));

    }

    ActivityResultLauncher<Intent> galeriaResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        imageUri = data.getData();
                        imgProfesor.setImageURI(imageUri);
                    }

                    new ActivityCompat.OnRequestPermissionsResultCallback() {
                        @Override
                        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                            if (requestCode == STORAGE_REQUEST_CODE){
                                if (grantResults.length>0){
                                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                                    if (storageAccepted)
                                        pickFromGallery();
                                    else
                                        Toast.makeText(getContext(), "Se requieren permisos almacenamiento", Toast.LENGTH_SHORT);
                                }
                            }
                        }
                    };
                }
            });

    private void pickFromCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripcion de la imagen");

        imageUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        camaraResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> camaraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        try {
                            imgProfesor.setImageURI(imageUri);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    new ActivityCompat.OnRequestPermissionsResultCallback() {
                        @Override
                        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                            if (requestCode == CAMERA_REQUEST_CODE){
                                if (grantResults.length>0){
                                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                                    if (cameraAccepted && storageAccepted)
                                        pickFromCamera();
                                    else
                                        Toast.makeText(getContext(), "Se requieren permisos de camara y almacenamiento", Toast.LENGTH_SHORT);
                                }
                            }
                        }
                    };
                }
            });

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(getActivity(),cameraPermissions, CAMERA_REQUEST_CODE);
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(getActivity(),storagePermissions, STORAGE_REQUEST_CODE);
    }

}
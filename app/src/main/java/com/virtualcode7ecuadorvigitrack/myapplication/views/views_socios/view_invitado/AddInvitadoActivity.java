package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_invitado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.activity.cActivityInicioSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Permission;
import java.security.Permissions;
import java.text.SimpleDateFormat;
import java.util.Date;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;


public class AddInvitadoActivity extends cActivityInicioSocio implements View.OnClickListener {
    private static final int CODE_PICK_PHOTO_GALERY = 800;
    private static final int CODE_TAKE_PHOTO_CAMERA = 214;
    private static final int CODE_PERMISOS_CAMERA = 785;
    private MaterialButton mMaterialButtonTakePhotoGalery;
    private MaterialButton mMaterialButtonDeletePhoto;
    private ImageView mImageViewDni;
    private View mViewLoadPhoto;
    private View mViewEditPhoto;
    private MaterialButton mMaterialButtonTakePhotoCamera;
    private Runnable mRunnableBitmapPhoto;
    private String[] list;

    private AlertDialog mAlertDialog;

    private androidx.appcompat.app.AlertDialog mAlertDialogPermisos = null;
    private Uri mUriPhotoCamera;
    private File mFilePhoto;
    private String currentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invitado);
        cToolbar.show(this,getResources().getString(R.string.add_invitados),true,1);
        mMaterialButtonTakePhotoGalery = findViewById(R.id.btnUpArchivo);
        mImageViewDni = findViewById(R.id.idImgDniInvitado);
        mViewEditPhoto = findViewById(R.id.idViewBtnEditLoadPhoto);
        mViewLoadPhoto = findViewById(R.id.idViewBtnLoadPhoto);
        mMaterialButtonTakePhotoCamera = findViewById(R.id.idBtnTakePhotoCamera);

        mMaterialButtonDeletePhoto = findViewById(R.id.id_btn_delete_photo_socio);
        mMaterialButtonDeletePhoto.setOnClickListener(this::onClick);
        mMaterialButtonTakePhotoGalery.setOnClickListener(this::onClick);
        mMaterialButtonTakePhotoCamera.setOnClickListener(this::onClick);



    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnUpArchivo:
                openGalery();
                break;
            case R.id.id_btn_delete_photo_socio:
                loadingPhotoDniDefault();
                visibleViewLoadPhoto();
                break;
            case R.id.idBtnTakePhotoCamera:
                showCapturePhotoCamera();
                break;
        }
    }

    private void loadingPhotoDniDefault()
    {
        Picasso.with(AddInvitadoActivity.this)
                .load(R.drawable.dni_mexico)
                .error(getDrawable(R.drawable.img_load))
                .placeholder(getResources().getDrawable(R.drawable.img_error))
                .into(mImageViewDni);
    }

    private void visibleViewLoadPhoto()
    {
        mViewEditPhoto.setVisibility(View.GONE);
        mViewLoadPhoto.setVisibility(View.VISIBLE);
    }

    private void openGalery()
    {
        Intent mIntent = new Intent(Intent.ACTION_PICK);
        //mIntent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mIntent.setType("image/*");
        mIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(mIntent,CODE_PICK_PHOTO_GALERY);
    }
    private void showCapturePhotoCamera()
    {
        /*Intent intent = new Intent(this, CameraControllerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("inputData", new CameraBundleBuilder()
                .setFullscreenMode(false)
                .setDoneButtonString("Add")
                .setSinglePhotoMode(false)
                .setMax_photo(1)
                .setManualFocus(true)
                .setBucketName(getClass().getName())
                .setPreviewEnableCount(true)
                .setPreviewIconVisiblity(true)
                .setPreviewPageRedirection(true)
                .setEnableDone(true)
                .setClearBucket(true)
                .createCameraBundle());
        startActivityForResult(intent, 214);*/

        if (ContextCompat.checkSelfPermission(AddInvitadoActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            Intent mIntent = new Intent();
            mIntent.setAction("android.media.action.IMAGE_CAPTURE");

            //startActivityForResult(mIntent,CODE_TAKE_PHOTO_CAMERA);

            mFilePhoto = createImageFile(); /**Creo el File**/

            if (mFilePhoto!=null)
            {
                mUriPhotoCamera = FileProvider.getUriForFile(this,
                        "com.virtualcode7ecuadorvigitrack.myapplication.fileprovider",
                        mFilePhoto);

                mIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPhotoCamera);
                startActivityForResult(mIntent, CODE_TAKE_PHOTO_CAMERA);

            }else{
                Toasty.error(AddInvitadoActivity.this,
                        "No se pudo crear el archivo."
                        ,Toasty.LENGTH_LONG).show();
            }


        }else{
            solicitarPermisos();
        }



    }

    private void solicitarPermisos()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(AddInvitadoActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == true){
            /**Alert SolicitarPermiso**/


            androidx.appcompat.app.AlertDialog.Builder mBuilder =
                    new androidx.appcompat.app.AlertDialog.Builder(AddInvitadoActivity.this);
            mBuilder.setMessage(getApplicationContext().getResources()
                    .getString(R.string.permisos_store));
            mBuilder.setCancelable(false);
            mBuilder.setTitle(getApplicationContext().getResources()
                    .getString(R.string.title_permisos_store));
            mBuilder.setIcon(getApplicationContext()
                    .getResources().getDrawable(R.drawable.icono_app));

            mBuilder.setPositiveButton(getApplicationContext().getResources()
                    .getString(R.string.accept_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    mAlertDialogPermisos.cancel();
                    mAlertDialogPermisos.hide();
                    showRequestPermissions();
                }
            });


            mAlertDialogPermisos = mBuilder.create();
            mAlertDialogPermisos.show();

        }else{
            showRequestPermissions();
        }
    }

    private void showRequestPermissions()
    {
        ActivityCompat.requestPermissions(AddInvitadoActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                ,CODE_PERMISOS_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {

        switch (requestCode){
            case CODE_PICK_PHOTO_GALERY:
                if (resultCode == RESULT_OK)
                {

                    insertPhotoinImageView(data.getData());
                }else{
                    Toasty.error(AddInvitadoActivity.this
                            ,"No se pudo obtener la imagen."
                            ,Toasty.LENGTH_LONG).show();
                }
                break;
            case CODE_TAKE_PHOTO_CAMERA:
                if (resultCode == RESULT_OK)
                {
                    /*assert data != null;

                    list = data.getStringArrayExtra("resultData");
                    Log.e("camera","tam : "+list.length);
                    Log.e("camera","data : "+data.getData());
                    for (int i=0;i<list.length;i++){
                        Log.e("camera","list : "+list[i]);
                    }

                    mAlertDialog = new cAlertDialogProgress().showAlertProgress(AddInvitadoActivity.this,
                            "Agregando fotografia...",false);
                    mAlertDialog.show();

                    mThread = new Handler();
                    mThread.postDelayed(mRunnableBitmapPhoto,1000);*/

                    /**Obtener imagen en miniatura**/

                    /*Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mImageViewDni.setImageBitmap(imageBitmap);*/


                    savePhotoGalerry();


                }else{
                    Toasty.warning(AddInvitadoActivity.this,
                            "No se pudo capturar la imagen."
                            ,Toasty.LENGTH_LONG).show();
                }
                break;
        }

    }

    private void savePhotoGalerry()
    {
        /*try {
            Log.e("PHOTOGALLERY",currentPhotoPath);
            File f = new File(currentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri);
            sendBroadcast(mediaScanIntent);

            Toast.makeText(this, "IN GALLERY", Toast.LENGTH_SHORT).show();
        }catch (Exception mException){
            Toast.makeText(this, mException.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }*/

        try {

            MediaStore.Images.Media.insertImage(getContentResolver(),
                    currentPhotoPath, mFilePhoto.getName(), null);

            this.sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(mFilePhoto)));
        } catch (FileNotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    private File createImageFile()
    {
        try {

            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "DNI_CAM" + timeStamp + "_";
            File storageDir = null;

            storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = image.getAbsolutePath();
            return image;

        }catch (IOException e){
            Log.e("FileImg",e.getMessage());
            return null;
        }
    }



    private void insertPhotoinImageView(Uri path)
    {
        Picasso.with(AddInvitadoActivity.this)
                .load(path)
                .error(getDrawable(R.drawable.img_load))
                .placeholder(getResources().getDrawable(R.drawable.img_error))
                .into(mImageViewDni);

        mViewLoadPhoto.setVisibility(View.GONE);
        mViewEditPhoto.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode
            ,String[] permissions,int[] grantResults)
    {
        if (requestCode == CODE_PERMISOS_CAMERA &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            showCapturePhotoCamera();
        }else{
            solicitarPermisos();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
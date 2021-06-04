package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_invitado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
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

import dmax.dialog.SpotsDialog;
import in.balakrishnan.easycam.BitmapHelper;
import in.balakrishnan.easycam.CameraBundleBuilder;
import in.balakrishnan.easycam.CameraControllerActivity;
import in.balakrishnan.easycam.FileUtils;

public class AddInvitadoActivity extends cActivityInicioSocio implements View.OnClickListener {
    private static final int CODE_PICK_PHOTO_GALERY = 800;
    private MaterialButton mMaterialButtonTakePhotoGalery;
    private MaterialButton mMaterialButtonDeletePhoto;
    private ImageView mImageViewDni;
    private View mViewLoadPhoto;
    private View mViewEditPhoto;
    private MaterialButton mMaterialButtonTakePhotoCamera;
    private Runnable mRunnableBitmapPhoto;
    private String[] list;

    private AlertDialog mAlertDialog;
    private Handler mThread;

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

        mRunnableBitmapPhoto = new Runnable() {
            @Override
            public void run()
            {

                insertPhotoinImageView2(list[0]);
            }
        };

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
        mIntent.setType("image/*");
        mIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(mIntent,CODE_PICK_PHOTO_GALERY);
    }
    private void showCapturePhotoCamera()
    {
        Intent intent = new Intent(this, CameraControllerActivity.class);
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
        startActivityForResult(intent, 214);
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
                    Toast.makeText(this, "no ok ", Toast.LENGTH_SHORT).show();
                }
                break;
            case 214:
                if (resultCode == RESULT_OK) {
                    assert data != null;

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
                    mThread.postDelayed(mRunnableBitmapPhoto,1000);

                }
                break;
        }

    }

    private void insertPhotoinImageView2(String s)
    {
        String path = FileUtils.getFilePath(getApplicationContext(),getClass().getName());
        Log.e("camera","path : "+path);

        Bitmap mBitmap = BitmapFactory.decodeFile(s);
        Uri mUri = BitmapHelper.getFileUriForBitmap(mBitmap);

        Picasso.with(AddInvitadoActivity.this)
                .load(mUri)
                .error(getDrawable(R.drawable.img_load))
                .placeholder(getResources().getDrawable(R.drawable.img_error))
                .into(mImageViewDni);

        mViewLoadPhoto.setVisibility(View.GONE);
        mViewEditPhoto.setVisibility(View.VISIBLE);

        if (mAlertDialog.isShowing()){cAlertDialogProgress.closeAlertProgress(mAlertDialog);}
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy()
    {
        FileUtils.clearAllFiles(this, getClass().getName());
        super.onDestroy();
    }
}
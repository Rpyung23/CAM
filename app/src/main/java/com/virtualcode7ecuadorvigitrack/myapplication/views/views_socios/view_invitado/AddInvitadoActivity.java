package com.virtualcode7ecuadorvigitrack.myapplication.views.views_socios.view_invitado;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.virtualcode7ecuadorvigitrack.myapplication.R;
import com.virtualcode7ecuadorvigitrack.myapplication.activity.cActivityInicioSocio;
import com.virtualcode7ecuadorvigitrack.myapplication.utils.*;

public class AddInvitadoActivity extends cActivityInicioSocio implements View.OnClickListener {
    private static final int CODE_PICK_PHOTO_GALERY = 800;
    private MaterialButton mMaterialButtonTakePhotoGalery;
    private MaterialButton mMaterialButtonDeletePhoto;
    private ImageView mImageViewDni;
    private View mViewLoadPhoto;
    private View mViewEditPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invitado);
        cToolbar.show(this,getResources().getString(R.string.add_invitados),true,1);
        mMaterialButtonTakePhotoGalery = findViewById(R.id.btnUpArchivo);
        mImageViewDni = findViewById(R.id.idImgDniInvitado);
        mViewEditPhoto = findViewById(R.id.idViewBtnEditLoadPhoto);
        mViewLoadPhoto = findViewById(R.id.idViewBtnLoadPhoto);
        mMaterialButtonDeletePhoto = findViewById(R.id.id_btn_delete_photo_socio);
        mMaterialButtonDeletePhoto.setOnClickListener(this::onClick);
        mMaterialButtonTakePhotoGalery.setOnClickListener(this::onClick);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        if (requestCode == CODE_PICK_PHOTO_GALERY){
            if (resultCode == RESULT_OK)
            {
                Picasso.with(AddInvitadoActivity.this)
                        .load(data.getData())
                        .error(getDrawable(R.drawable.img_load))
                        .placeholder(getResources().getDrawable(R.drawable.img_error))
                .into(mImageViewDni);
                mViewLoadPhoto.setVisibility(View.GONE);
                mViewEditPhoto.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(this, "no ok ", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "no requestcode", Toast.LENGTH_SHORT).show();
        }

    }
}
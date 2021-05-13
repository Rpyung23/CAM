package com.virtualcode7ecuadorvigitrack.myapplication.models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;

import com.virtualcode7ecuadorvigitrack.myapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class cImageGetter implements Html.ImageGetter
{
    Context mContext;

    public cImageGetter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Drawable getDrawable(String source)
    {
        Log.e("IMGSRC",source);

        Drawable mDrawable;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);


        try {
            InputStream is = (InputStream) new URL(source).getContent();
            mDrawable = Drawable.createFromStream(is,"imgSrc");

        } catch (IOException e) {
            e.printStackTrace();
            mDrawable = mContext.getDrawable(R.drawable.img_error);
        }
        mDrawable.setBounds(0,0,mDrawable.getIntrinsicWidth()+100
                ,mDrawable.getIntrinsicHeight()+100);
        return mDrawable;
    }
}

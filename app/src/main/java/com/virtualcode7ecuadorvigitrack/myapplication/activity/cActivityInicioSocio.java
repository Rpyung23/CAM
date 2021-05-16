package com.virtualcode7ecuadorvigitrack.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.virtualcode7ecuadorvigitrack.myapplication.views.InicioActivity;

import java.util.concurrent.TimeUnit;

public class cActivityInicioSocio extends AppCompatActivity
{
    private cCountDownTimer mCCountDownTimer = new cCountDownTimer(TimeUnit.MILLISECONDS
            .convert(5,TimeUnit.MINUTES),
            TimeUnit.MILLISECONDS.convert(1,TimeUnit.MINUTES));

    //private cCountDownTimer mCCountDownTimer = new cCountDownTimer(60000,1000);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        startCounterDown();
    }


    @Override
    protected void onDestroy()
    {
        stopCounterDown();
        super.onDestroy();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        stopCounterDown();
        startCounterDown();
    }

    private void stopCounterDown()
    {
        mCCountDownTimer.cancel();
    }

    private void startCounterDown()
    {
        mCCountDownTimer.start();
    }

    public class cCountDownTimer extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */

        public cCountDownTimer(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            //Toast.makeText(cActivityInicioSocio.this, " TIME : "+millisUntilFinished, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish()
        {
            Intent mIntent = new Intent(getApplicationContext(), InicioActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mIntent);
            finish();
        }


    }



}

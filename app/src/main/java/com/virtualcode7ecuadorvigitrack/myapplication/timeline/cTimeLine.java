package com.virtualcode7ecuadorvigitrack.myapplication.timeline;

import android.graphics.drawable.Drawable;

public class cTimeLine
{
    private boolean status;/**Focus actual**/
    private int color_inactive;
    private int color_active;
    private int color_check;/**Los datos se llenaron completos**/
    private boolean checkCircle;
    private Drawable imgMarker;
    private Drawable imgCircleCheck;
    private int colorLineActive;
    private int colorLineInactive;
    private Drawable background;

    public cTimeLine()
    {
        this.status = false;
        this.checkCircle = false;
    }

    public Drawable getBackground() {
        return background;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    public int getColorTimeLineActive() {
        return colorLineActive;
    }

    public void setColorTimeLineActive(int colorTimeLineActive) {
        this.colorLineActive = colorTimeLineActive;
    }

    public int getColorTimeLineInactive() {
        return colorLineInactive;
    }

    public void setColorTimeLineInactive(int colorTimeLineInactive) {
        this.colorLineInactive = colorTimeLineInactive;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getColor_inactive() {
        return color_inactive;
    }

    public void setColor_inactive(int color_inactive) {
        this.color_inactive = color_inactive;
    }

    public int getColor_active() {
        return color_active;
    }

    public void setColor_active(int color_active) {
        this.color_active = color_active;
    }

    public int getColor_check() {
        return color_check;
    }

    public void setColor_check(int color_check) {
        this.color_check = color_check;
    }

    public Drawable getImgMarker() {
        return imgMarker;
    }

    public void setImgMarker(Drawable imgCircle) {
        this.imgMarker = imgCircle;
    }

    public boolean isCheckCircle() {
        return checkCircle;
    }

    public void setCheckCircle(boolean checkCircle) {
        this.checkCircle = checkCircle;
    }

    public Drawable getImgCircleCheck() {
        return imgCircleCheck;
    }

    public void setImgCircleCheck(Drawable imgCircleCheck) {
        this.imgCircleCheck = imgCircleCheck;
    }
}

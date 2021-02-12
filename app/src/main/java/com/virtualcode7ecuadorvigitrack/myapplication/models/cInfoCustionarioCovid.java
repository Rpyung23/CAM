package com.virtualcode7ecuadorvigitrack.myapplication.models;

public class cInfoCustionarioCovid
{
    private String mStringBeneficiario;
    private String mStringTipo;
    private String mStringEstado;
    private int mIntbeneficiarioContador;
    private boolean isrenovado;

    private boolean color_verde ;
    private boolean color_yellow;
    private boolean color_red;


    public cInfoCustionarioCovid()
    {
        this.color_red = false;
        this.color_verde = false;
        this.color_yellow = false;
        this.isrenovado = false;
    }

    public boolean isIsrenovado() {
        return isrenovado;
    }

    public void setIsrenovado(boolean isrenovado) {
        this.isrenovado = isrenovado;
    }

    public int getmIntbeneficiarioContador() {
        return mIntbeneficiarioContador;
    }

    public void setmIntbeneficiarioContador(int mIntbeneficiarioContador) {
        this.mIntbeneficiarioContador = mIntbeneficiarioContador;
    }

    public String getmStringBeneficiario() {
        return mStringBeneficiario;
    }

    public void setmStringBeneficiario(String mStringBeneficiario) {
        this.mStringBeneficiario = mStringBeneficiario;
    }

    public String getmStringTipo() {
        return mStringTipo;
    }

    public void setmStringTipo(String mStringTipo) {
        this.mStringTipo = mStringTipo;
    }

    public String getmStringEstado() {
        return mStringEstado;
    }

    public void setmStringEstado(String mStringEstado) {
        this.mStringEstado = mStringEstado;
    }

    public boolean isColor_verde() {
        return color_verde;
    }

    public void setColor_verde(boolean color_verde) {
        this.color_verde = color_verde;
    }

    public boolean isColor_yellow() {
        return color_yellow;
    }

    public void setColor_yellow(boolean color_yellow) {
        this.color_yellow = color_yellow;
    }

    public boolean isColor_red() {
        return color_red;
    }

    public void setColor_red(boolean color_red) {
        this.color_red = color_red;
    }
}

package com.virtualcode7ecuadorvigitrack.myapplication.models;

public class cListInvitadosActuales
{
    private String mNombresCompletos;
    private String mFechaInicial;
    private String mFechaFinal;
    private int statusListInvitadosNow;

    public cListInvitadosActuales() {
        this.mNombresCompletos = "";
        this.mFechaInicial = "";
        this.mFechaFinal = "";
        this.statusListInvitadosNow = 0;
    }

    public String getmNombresCompletos() {
        return mNombresCompletos;
    }

    public void setmNombresCompletos(String mNombresCompletos) {
        this.mNombresCompletos = mNombresCompletos;
    }

    public String getmFechaInicial() {
        return mFechaInicial;
    }

    public void setmFechaInicial(String mFechaInicial) {
        this.mFechaInicial = mFechaInicial;
    }

    public String getmFechaFinal() {
        return mFechaFinal;
    }

    public void setmFechaFinal(String mFechaFinal) {
        this.mFechaFinal = mFechaFinal;
    }

    public int getStatusListInvitadosNow() {
        return statusListInvitadosNow;
    }

    public void setStatusListInvitadosNow(int statusListInvitadosNow) {
        this.statusListInvitadosNow = statusListInvitadosNow;
    }
}

package com.virtualcode7ecuadorvigitrack.myapplication.models;

public class cPaquete
{
    private int idPaquete;
    private boolean active;

    private double price_paquete;
    private String title_paquete;
    private String subtitle_paquete;
    private String tiempo_price_paquete;


    public cPaquete()
    {
        this.active =  false;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getPrice_paquete() {
        return price_paquete;
    }

    public void setPrice_paquete(double price_paquete) {
        this.price_paquete = price_paquete;
    }

    public String getTitle_paquete() {
        return title_paquete;
    }

    public void setTitle_paquete(String title_paquete) {
        this.title_paquete = title_paquete;
    }

    public String getSubtitle_paquete() {
        return subtitle_paquete;
    }

    public void setSubtitle_paquete(String subtitle_paquete) {
        this.subtitle_paquete = subtitle_paquete;
    }

    public String getTiempo_price_paquete() {
        return tiempo_price_paquete;
    }

    public void setTiempo_price_paquete(String tiempo_price_paquete) {
        this.tiempo_price_paquete = tiempo_price_paquete;
    }
}

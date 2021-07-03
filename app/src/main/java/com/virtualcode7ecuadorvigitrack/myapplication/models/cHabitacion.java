package com.virtualcode7ecuadorvigitrack.myapplication.models;

public class cHabitacion
{
    private boolean active;
    private int id_habitacion;
    private double price_habitacion;
    private String title_habitacion;
    private String subtitle_habitacion;
    private String tiempo_price_reserva;

    public cHabitacion()
    {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(int id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public double getPrice_habitacion() {
        return price_habitacion;
    }

    public void setPrice_habitacion(double price_habitacion) {
        this.price_habitacion = price_habitacion;
    }

    public String getTitle_habitacion() {
        return title_habitacion;
    }

    public void setTitle_habitacion(String title_habitacion) {
        this.title_habitacion = title_habitacion;
    }

    public String getSubtitle_habitacion() {
        return subtitle_habitacion;
    }

    public void setSubtitle_habitacion(String subtitle_habitacion) {
        this.subtitle_habitacion = subtitle_habitacion;
    }

    public String getTiempo_price_reserva() {
        return tiempo_price_reserva;
    }

    public void setTiempo_price_reserva(String tiempo_price_reserva) {
        this.tiempo_price_reserva = tiempo_price_reserva;
    }
}

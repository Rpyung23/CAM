package com.virtualcode7ecuadorvigitrack.myapplication.models;

import java.io.Serializable;

public class cReservaciones implements Serializable
{
    private String tipo_habitacion_name;
    private String fecha_desde;
    private String fecha_hasta;
    private int num_habitaciones;
    private String tipo_reserva_name;
    private String numNoches_name;
    private double price_reservacion;
    private int type_reservacion;


    public cReservaciones() {
    }

    public String getTipo_habitacion_name() {
        return tipo_habitacion_name;
    }

    public void setTipo_habitacion_name(String tipo_habitacion_name) {
        this.tipo_habitacion_name = tipo_habitacion_name;
    }

    public String getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(String fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public String getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(String fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }

    public int getNum_habitaciones() {
        return num_habitaciones;
    }

    public void setNum_habitaciones(int num_habitaciones) {
        this.num_habitaciones = num_habitaciones;
    }

    public String getTipo_reserva() {
        return tipo_reserva_name;
    }

    public void setTipo_reserva(String tipo_reserva) {
        this.tipo_reserva_name = tipo_reserva;
    }

    public String getNumNoches_name() {
        return numNoches_name;
    }

    public void setNumNoches_name(String numNoches_name) {
        this.numNoches_name = numNoches_name;
    }

    public double getPrice_reservacion() {
        return price_reservacion;
    }

    public void setPrice_reservacion(double price_reservacion) {
        this.price_reservacion = price_reservacion;
    }

    public int getType_reservacion() {
        return type_reservacion;
    }

    public void setType_reservacion(int type_reservacion) {
        this.type_reservacion = type_reservacion;
    }
}

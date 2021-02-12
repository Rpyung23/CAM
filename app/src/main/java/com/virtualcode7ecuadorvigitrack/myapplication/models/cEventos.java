package com.virtualcode7ecuadorvigitrack.myapplication.models;

import java.io.Serializable;
import java.util.ArrayList;

public class cEventos implements Serializable
{
    private int id_evento;
    private String fecha;
    private String titulo;
    private String direccion;
    private String acerca_de;
    private String uri_foto;
    private String fecha_fin;
    private ArrayList<String> mItinerarioStringArrayList = new ArrayList<>();

    public cEventos()
    {
        this.acerca_de ="";
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public String getFecha() {
        return fecha;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getAcerca_de() {
        return acerca_de;
    }

    public void setAcerca_de(String acerca_de) {
        this.acerca_de = acerca_de;
    }

    public String getUri_foto() {
        return uri_foto;
    }

    public void setUri_foto(String uri_foto) {
        this.uri_foto = uri_foto;
    }

    public ArrayList<String> getmItinerarioStringArrayList() {
        return mItinerarioStringArrayList;
    }

    public void setmItinerarioStringArrayList(ArrayList<String> mItinerarioStringArrayList) {
        this.mItinerarioStringArrayList = mItinerarioStringArrayList;
    }
}

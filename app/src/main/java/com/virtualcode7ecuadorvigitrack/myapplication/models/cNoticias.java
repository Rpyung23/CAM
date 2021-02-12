package com.virtualcode7ecuadorvigitrack.myapplication.models;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class cNoticias implements Serializable
{
    private int id_noticias;
    private String titulo;
    private String fecha;
    private String mUriPicturePrincipalNoticia;
    private ArrayList<String> mUriArrayListGaleriaNoticia;
    private String TextoNoticia;

    public  cNoticias()
    {
        this.TextoNoticia = "";
    }

    public int getId_noticias() {
        return id_noticias;
    }

    public void setId_noticias(int id_noticias) {
        this.id_noticias = id_noticias;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getmUriPicturePrincipalNoticia() {
        return mUriPicturePrincipalNoticia;
    }

    public void setmUriPicturePrincipalNoticia(String mUriPicturePrincipalNoticia) {
        this.mUriPicturePrincipalNoticia = mUriPicturePrincipalNoticia;
    }

    public ArrayList<String> getmUriArrayListGaleriaNoticia() {
        return mUriArrayListGaleriaNoticia;
    }

    public void setmUriArrayListGaleriaNoticia(ArrayList<String> mUriArrayListGaleriaNoticia) {
        this.mUriArrayListGaleriaNoticia = mUriArrayListGaleriaNoticia;
    }

    public String getTextoNoticia() {
        return TextoNoticia;
    }

    public void setTextoNoticia(String textoNoticia) {
        TextoNoticia = textoNoticia;
    }
}

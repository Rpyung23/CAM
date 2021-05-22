package com.virtualcode7ecuadorvigitrack.myapplication.models;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class cNoticias implements Serializable
{
    private int id_noticias;
    private String titulo;
    private String descriptionCorta;
    private String fecha;
    private String mUriPicturePrincipalNoticia;
    private String mUriPictureContenidoNoticia;
    /*private ArrayList<String> mUriArrayListGaleriaNoticia;*/
    private String TextoNoticia;
    private int numPage;

    public  cNoticias()
    {
        this.TextoNoticia = "";
        this.numPage = 0;
    }

    public int getNumPage() {
        return numPage;
    }

    public void setNumPage(int numPage) {
        this.numPage = numPage;
    }

    public int getId_noticias() {
        return id_noticias;
    }

    public String getDescriptionCorta() {
        return descriptionCorta;
    }

    public void setDescriptionCorta(String descriptionCorta) {
        this.descriptionCorta = descriptionCorta;
    }

    public String getmUriPictureContenidoNoticia() {
        return mUriPictureContenidoNoticia;
    }

    public void setmUriPictureContenidoNoticia(String mUriPictureContenidoNoticia) {
        this.mUriPictureContenidoNoticia = mUriPictureContenidoNoticia;
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

    /*public ArrayList<String> getmUriArrayListGaleriaNoticia() {
        return mUriArrayListGaleriaNoticia;
    }

    public void setmUriArrayListGaleriaNoticia(ArrayList<String> mUriArrayListGaleriaNoticia) {
        this.mUriArrayListGaleriaNoticia = mUriArrayListGaleriaNoticia;
    }*/

    public String getTextoNoticia() {
        return TextoNoticia;
    }

    public void setTextoNoticia(String textoNoticia) {
        TextoNoticia = textoNoticia;
    }
}

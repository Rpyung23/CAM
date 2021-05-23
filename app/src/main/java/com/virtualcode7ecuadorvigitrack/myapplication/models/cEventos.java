package com.virtualcode7ecuadorvigitrack.myapplication.models;

import java.io.Serializable;
import java.util.ArrayList;

public class cEventos implements Serializable
{
    private int id_evento;
    private String fecha;
    private String titulo;
    private String direccion;
    private String contenido;
    private String uri_foto;
    private String fecha_fin;
    private String descripcion;

    private String uri_foto_imagen_principal;/**Al consultar evento en especifico**/

    private int numPage;

    public cEventos()
    {
        this.id_evento = 0;
        this.fecha = "";
        this.titulo = "";
        this.direccion = "";
        this.contenido = "";
        this.uri_foto = "";
        this.fecha_fin = "";
        this.numPage = 1;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUri_foto_imagen_principal() {
        return uri_foto_imagen_principal;
    }

    public void setUri_foto_imagen_principal(String uri_foto_imagen_principal) {
        this.uri_foto_imagen_principal = uri_foto_imagen_principal;
    }

    public int getNumPage() {
        return numPage;
    }

    public void setNumPage(int numPage) {
        this.numPage = numPage;
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getUri_foto() {
        return uri_foto;
    }

    public void setUri_foto(String uri_foto) {
        this.uri_foto = uri_foto;
    }


}

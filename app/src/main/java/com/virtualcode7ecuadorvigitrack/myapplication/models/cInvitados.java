package com.virtualcode7ecuadorvigitrack.myapplication.models;

public class cInvitados
{
    private String url_photo_dni;
    private String nombres_invitado;
    private String curp;
    private int edad;


    public cInvitados()
    {
        this.edad = 0;
    }

    public String getUrl_photo_dni() {
        return url_photo_dni;
    }

    public void setUrl_photo_dni(String url_photo_dni) {
        this.url_photo_dni = url_photo_dni;
    }

    public String getNombres_invitado() {
        return nombres_invitado;
    }

    public void setNombres_invitado(String nombres_invitado) {
        this.nombres_invitado = nombres_invitado;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}

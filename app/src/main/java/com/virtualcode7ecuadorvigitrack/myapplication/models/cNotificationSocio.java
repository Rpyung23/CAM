package com.virtualcode7ecuadorvigitrack.myapplication.models;

import java.io.Serializable;

public class cNotificationSocio implements Serializable
{
    private int id_notificacion;
    private String notificacion_titulo;
    private String fecha;/**notificacion_fecha_envio**/
    private String mensaje;/**notificacion_contenido**/
    private boolean leido; /**is el notificacion_estado =  **/

    public cNotificationSocio()
    {
        leido = false;
    }

    public int getId_notificacion() {
        return id_notificacion;
    }

    public void setId_notificacion(int id_notificacion) {
        this.id_notificacion = id_notificacion;
    }

    public String getNotificacion_titulo() {
        return notificacion_titulo;
    }

    public void setNotificacion_titulo(String notificacion_titulo) {
        this.notificacion_titulo = notificacion_titulo;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

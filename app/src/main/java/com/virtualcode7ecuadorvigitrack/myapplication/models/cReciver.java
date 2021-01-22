package com.virtualcode7ecuadorvigitrack.myapplication.models;

import java.io.Serializable;

public class cReciver implements Serializable
{
    private String num_recivo;/**no_de_recibo**/
    private String texto_main;/**recibo_concepto**/
    private String fecha;/**recibo_fecha**/
    private String dinero_total;/**recibo_monto**/

    private int id_recibo;
    private String recibo_facturado;
    private String recibo_pdf;
    private String recibo_factura;

    public cReciver() {
    }

    public String getNum_recivo() {
        return num_recivo;
    }

    public void setNum_recivo(String num_recivo) {
        this.num_recivo = num_recivo;
    }

    public String getTexto_main() {
        return texto_main;
    }

    public void setTexto_main(String texto_main) {
        this.texto_main = texto_main;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDinero_total() {
        return dinero_total;
    }

    public void setDinero_total(String dinero_total) {
        this.dinero_total = dinero_total;
    }


    public int getId_recibo() {
        return id_recibo;
    }

    public void setId_recibo(int id_recibo) {
        this.id_recibo = id_recibo;
    }

    public String getRecibo_facturado() {
        return recibo_facturado;
    }

    public void setRecibo_facturado(String recibo_facturado) {
        this.recibo_facturado = recibo_facturado;
    }

    public String getRecibo_pdf() {
        return recibo_pdf;
    }

    public void setRecibo_pdf(String recibo_pdf) {
        this.recibo_pdf = recibo_pdf;
    }

    public String getRecibo_factura() {
        return recibo_factura;
    }

    public void setRecibo_factura(String recibo_factura) {
        this.recibo_factura = recibo_factura;
    }
}

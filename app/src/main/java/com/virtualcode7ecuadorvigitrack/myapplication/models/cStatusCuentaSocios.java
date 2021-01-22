package com.virtualcode7ecuadorvigitrack.myapplication.models;

import com.android.volley.toolbox.StringRequest;

public class cStatusCuentaSocios
{
    private String titulo_card_casillero;/**movimientoConcepto**/
    private String fecha_pago;/**movimientoFechaPago**/
    private String precio_total;/**movimientoImporte**/


    private int idMovimiento;
    //private String movimientoConcepto;
    private String movientoEstado;
    private String movimientoLeyenda;
    private int idMembresia;
    private String movimientoURLPago;


    public cStatusCuentaSocios(){}

    public String getTitulo_card_casillero() {
        return titulo_card_casillero;
    }

    public void setTitulo_card_casillero(String titulo_card_casillero) {
        this.titulo_card_casillero = titulo_card_casillero;
    }

    public String getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(String fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public String getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(String precio_total) {
        this.precio_total = precio_total;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getMovientoEstado() {
        return movientoEstado;
    }

    public void setMovientoEstado(String movientoEstado) {
        this.movientoEstado = movientoEstado;
    }

    public String getMovimientoLeyenda() {
        return movimientoLeyenda;
    }

    public void setMovimientoLeyenda(String movimientoLeyenda) {
        this.movimientoLeyenda = movimientoLeyenda;
    }

    public int getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(int idMembresia) {
        this.idMembresia = idMembresia;
    }

    public String getMovimientoURLPago() {
        return movimientoURLPago;
    }

    public void setMovimientoURLPago(String movimientoURLPago) {
        this.movimientoURLPago = movimientoURLPago;
    }
}

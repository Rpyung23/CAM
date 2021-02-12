package com.virtualcode7ecuadorvigitrack.myapplication.models;

import java.io.Serializable;

public class cMembresiaSocio implements Serializable
{
    private int idMembresia;
    private String membresiaToken;
    private String membresiaTitular;
    private String membresiaPassword;
    private String membresiaEmail;
    private String membresiaNo;
    private String membresiaEstado;
    private String membresiaFechaIngreso;
    private String membresiaConexion;
    private String membresiaTokenNotification;
    private String membresiaTipo;
    private String membresiaTokenAplicacion;
    private String membresiaCelular;
    private String membresiaSaldo;
    private int membresiaCuestionarioResultado;


    public cMembresiaSocio(){}

    public int getMembresiaCuestionarioResultado() {
        return membresiaCuestionarioResultado;
    }

    public void setMembresiaCuestionarioResultado(int membresiaCuestionarioResultado) {
        this.membresiaCuestionarioResultado = membresiaCuestionarioResultado;
    }

    public int getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(int idMembresia) {
        this.idMembresia = idMembresia;
    }

    public String getMembresiaToken() {
        return membresiaToken;
    }

    public void setMembresiaToken(String membresiaToken) {
        this.membresiaToken = membresiaToken;
    }

    public String getMembresiaTitular() {
        return membresiaTitular;
    }

    public void setMembresiaTitular(String membresiaTitular) {
        this.membresiaTitular = membresiaTitular;
    }

    public String getMembresiaPassword() {
        return membresiaPassword;
    }

    public void setMembresiaPassword(String membresiaPassword) {
        this.membresiaPassword = membresiaPassword;
    }

    public String getMembresiaEmail() {
        return membresiaEmail;
    }

    public void setMembresiaEmail(String membresiaEmail) {
        this.membresiaEmail = membresiaEmail;
    }

    public String getMembresiaNo() {
        return membresiaNo;
    }

    public void setMembresiaNo(String membresiaNo) {
        this.membresiaNo = membresiaNo;
    }

    public String getMembresiaEstado() {
        return membresiaEstado;
    }

    public void setMembresiaEstado(String membresiaEstado) {
        this.membresiaEstado = membresiaEstado;
    }

    public String getMembresiaFechaIngreso() {
        return membresiaFechaIngreso;
    }

    public void setMembresiaFechaIngreso(String membresiaFechaIngreso) {
        this.membresiaFechaIngreso = membresiaFechaIngreso;
    }

    public String getMembresiaConexion() {
        return membresiaConexion;
    }

    public void setMembresiaConexion(String membresiaConexion) {
        this.membresiaConexion = membresiaConexion;
    }

    public String getMembresiaTokenNotification() {
        return membresiaTokenNotification;
    }

    public void setMembresiaTokenNotification(String membresiaTokenNotification) {
        this.membresiaTokenNotification = membresiaTokenNotification;
    }

    public String getMembresiaTipo() {
        return membresiaTipo;
    }

    public void setMembresiaTipo(String membresiaTipo) {
        this.membresiaTipo = membresiaTipo;
    }

    public String getMembresiaTokenAplicacion() {
        return membresiaTokenAplicacion;
    }

    public void setMembresiaTokenAplicacion(String membresiaTokenAplicacion) {
        this.membresiaTokenAplicacion = membresiaTokenAplicacion;
    }

    public String getMembresiaCelular() {
        return membresiaCelular;
    }

    public void setMembresiaCelular(String membresiaCelular) {
        this.membresiaCelular = membresiaCelular;
    }

    public String getMembresiaSaldo() {
        return membresiaSaldo;
    }

    public void setMembresiaSaldo(String membresiaSaldo) {
        this.membresiaSaldo = membresiaSaldo;
    }
}


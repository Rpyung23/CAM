package com.virtualcode7ecuadorvigitrack.myapplication.models;

import com.android.volley.toolbox.StringRequest;

import java.io.Serializable;

public class cSocio implements Serializable {
    private String num_membresia;/**num de menbresia**/
    private String nombre_socio;
    private String token;
    private int id_token_socio;


    public cSocio(){}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId_token_socio() {
        return id_token_socio;
    }

    public void setId_token_socio(int id_token_socio) {
        this.id_token_socio = id_token_socio;
    }

    public String getNum_membresia() {
        return num_membresia;
    }

    public void setNum_membresia(String num_membresia) {
        this.num_membresia = num_membresia;
    }

    public String getNombre_socio() {
        return nombre_socio;
    }

    public void setNombre_socio(String nombre_socio) {
        this.nombre_socio = nombre_socio;
    }
}

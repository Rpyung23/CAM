package com.virtualcode7ecuadorvigitrack.myapplication.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

public class cStringMesDia
{
    public static String mes(String fecha)
    {
        String mes = "";
        String mesString ="Enero";

        String[] mesAr = fecha.split("/");
        mes = mesAr[1];

        if (mes.equals("1") || mes.equals("01"))
        {
            mesString = "Enero";
        }else if (mes.equals("2") || mes.equals("02"))
            {
                mesString = "Febrero";
            }else if (mes.equals("3") || mes.equals("03"))
            {
                mesString = "Marzo";
            }else if (mes.equals("4") || mes.equals("04"))
            {
                mesString = "Abril";
            }else if (mes.equals("5") || mes.equals("05"))
            {
                mesString = "Mayo";
            }else if (mes.equals("6") || mes.equals("06"))
            {
                mesString = "Junio";
            }else if (mes.equals("7") || mes.equals("07"))
            {
                mesString = "Julio";
            }else if (mes.equals("8") || mes.equals("08"))
            {
                mesString = "Agosto";
            }else if (mes.equals("9") || mes.equals("09"))
            {
                mesString = "Septiembre";
            }else if (mes.equals("10"))
            {
                mesString = "Octubre";
            }else if (mes.equals("11"))
            {
                mesString = "Noviembre";
            }else if (mes.equals("12"))
            {
                mesString = "Diciembre";
            }

        return mesString;
    }

    public static String dia(String fecha)
    {
        String mes = "";
        //SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
       // mes = mSimpleDateFormat.format(fecha);
        String[] mesAr = fecha.split("/");

        mes = mesAr[0];
        return mes;
    }
}

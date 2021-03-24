package com.virtualcode7ecuadorvigitrack.myapplication.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class cStringMesDia
{
    public static String mes(String fecha)
    {
        Log.e("DateFechaOriginal",fecha);

        Date fechaDate = null;
        String fechaParseString = "05/05/05";

        try {

            if(fecha.contains("/"))
            {
                fechaDate = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
                fechaParseString = new SimpleDateFormat("dd/MM/yyyy").format(fechaDate);

            }else
                {

                fechaDate = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
                fechaParseString = new SimpleDateFormat("dd/MM/yyyy").format(fechaDate);
            }

        } catch (ParseException e)
        {

            Log.e("DateFecha",e.getMessage());
        }

        Log.e("DateFecha",fechaParseString);

        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //String fechaTexto = formatter.format(fechaDate);

        String mes = "";
        String mesString ="Enero";


        String[] mesAr = fechaParseString.split("/");
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

        Log.e("DateFechaOriginal",fecha);

        Date fechaDate = null;
        String fechaParseString = "05/05/05";

        try {

            if(fecha.contains("/"))
            {
                fechaDate = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
                fechaParseString = new SimpleDateFormat("dd/MM/yyyy").format(fechaDate);

            }else
            {

                fechaDate = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
                fechaParseString = new SimpleDateFormat("dd/MM/yyyy").format(fechaDate);
            }

        } catch (ParseException e)
        {

            Log.e("DateFecha",e.getMessage());
        }

        Log.e("DateFecha",fechaParseString);


        String mes = "";
        //SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
       // mes = mSimpleDateFormat.format(fecha);
        String[] mesAr = fechaParseString.split("/");

        mes = mesAr[0];
        return mes;
    }


    public static String year(String fecha)
    {

        Log.e("DateFechaOriginal",fecha);

        Date fechaDate = null;
        String fechaParseString = "05/05/05";

        try {

            if(fecha.contains("/"))
            {
                fechaDate = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
                fechaParseString = new SimpleDateFormat("dd/MM/yyyy").format(fechaDate);

            }else
            {

                fechaDate = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
                fechaParseString = new SimpleDateFormat("dd/MM/yyyy").format(fechaDate);
            }

        } catch (ParseException e)
        {

            Log.e("DateFecha",e.getMessage());
        }

        Log.e("DateFecha",fechaParseString);


        String year = "";
        //SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        // mes = mSimpleDateFormat.format(fecha);
        String[] mesAr = fechaParseString.split("/");

        year = mesAr[2];
        return year;
    }


}

package com.virtualcode7ecuadorvigitrack.myapplication.sqlite;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.virtualcode7ecuadorvigitrack.myapplication.application.cApplication;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cEventos;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;

import java.util.ArrayList;
import java.util.List;

public class cSQLEventos
{

    private cSQLiteOpenHelper mSqLiteOpenHelper;

    public cSQLEventos()
    {
        this.mSqLiteOpenHelper = cApplication.getmApplicationInstance()
                .getmSqLiteOpenHelper();
    }

    public SQLiteDatabase readSQliteDatabase()
    {
        return  mSqLiteOpenHelper.getReadableDatabase();
    }


    public SQLiteDatabase writeSQliteDatabase()
    {
        return  mSqLiteOpenHelper.getWritableDatabase();
    }


    public void InsertEventos(int page,List<cEventos> mEventosList)
    {
        deleteEventos(page);

        /**
         * eventos (id INTEGER PRIMARY KEY, titulo TEXT, fecha_publicacion_inicio TEXT" +
         *                     ",fecha_publicacion_fin TEXT,contenido TEXT,descripcion_corta TEXT,
         *                     url_imagen_miniatura TEXT" +
         *                     ",url_imagen_principal TEXT,direccion TEXT,page INTEGER)
         *
         * **/


        for (int i=0;i<mEventosList.size();i++)
        {
            String mStrinInsertEvento = "insert into eventos(id,titulo,fecha_publicacion_inicio," +
                    "fecha_publicacion_fin,url_imagen_miniatura,direccion,page) " +
                    "values("+mEventosList.get(i).getId_evento()+",'"+mEventosList.get(i).getTitulo()+"'" +
                    ",'"+mEventosList.get(i).getFecha()+"','"+mEventosList.get(i).getFecha_fin()+"'" +
                    ",'"+mEventosList.get(i).getUri_foto()+"','"+mEventosList.get(i).getDireccion()+"'" +
                    ","+mEventosList.get(i).getNumPage()+")";

            Log.e("insert_evento",mStrinInsertEvento);

            if(!checkEvento(mEventosList.get(i).getId_evento()))
            {
                writeSQliteDatabase().execSQL(mStrinInsertEvento);
            }else{
                updateEvento(mEventosList.get(i));
            }
        }
    }

    private void updateEvento(cEventos oEvento)
    {
        try{
            String mStringUpdateEvento =  "update eventos set titulo='"+oEvento.getTitulo()+"'" +
                    ",fecha_publicacion_inicio='"+oEvento.getFecha()+"'" +
                    ",fecha_publicacion_fin='"+oEvento.getFecha_fin()+"'" +
                    ",url_imagen_miniatura='"+oEvento.getUri_foto()+"'" +
                    ",direccion='"+oEvento.getDireccion()+"' " +
                    "where id = "+oEvento.getId_evento();
            writeSQliteDatabase().execSQL(mStringUpdateEvento);
        }catch (SQLException mSqlException){
            Log.e("updateEvento",mSqlException.getMessage());
        }
    }

    public boolean deleteEventos(int page)
    {
        String mStringQueryDelete = "delete from eventos where page = "+page;
        Log.e("delete",mStringQueryDelete);
        writeSQliteDatabase().execSQL(mStringQueryDelete);

        return true;
    }

    public List<cEventos> readEventos(int page)
    {

        String mStringReadNoticiasPage = "select id,titulo,fecha_publicacion_inicio," +
                "fecha_publicacion_fin,url_imagen_miniatura,direccion " +
                "from eventos where page = "+page;

        Cursor mCursor = readSQliteDatabase().rawQuery(mStringReadNoticiasPage,null);

        List<cEventos> mEventosList = new ArrayList<>();

        while (mCursor.moveToNext())
        {
            cEventos mEventos = new cEventos();

            mEventos.setId_evento(mCursor.getInt(0));
            mEventos.setTitulo(mCursor.getString(1));
            mEventos.setFecha(mCursor.getString(2));
            mEventos.setFecha_fin(mCursor.getString(3));
            mEventos.setUri_foto(mCursor.getString(4));
            mEventos.setDireccion(mCursor.getString(5));

            mEventosList.add(mEventos);
        }
        return mEventosList;
    }

    public cEventos readEvento(int id)
    {

        /**
         * eventos (id INTEGER PRIMARY KEY, titulo TEXT, fecha_publicacion_inicio TEXT" +
         *                     ",fecha_publicacion_fin TEXT,contenido TEXT,descripcion_corta TEXT,
         *                     url_imagen_miniatura TEXT" +
         *                     ",url_imagen_principal TEXT,direccion TEXT,page INTEGER)
         *
         * **/


        cEventos mEventos = new cEventos();

        String mStringReadEventosPage = "select contenido,url_imagen_principal from eventos where id = "+id;

        Cursor mCursor = readSQliteDatabase().rawQuery(mStringReadEventosPage,null);

        while (mCursor.moveToNext())
        {

            mEventos.setUri_foto_imagen_principal(mCursor.getString(1));/*imagen contenido principal**/
            mEventos.setContenido(mCursor.getString(0));

            return mEventos;
        }

        return null;
    }

    private boolean checkEvento(int id)
    {
        int num = 0;

        String mStringCheckEvento = "select count(id) from eventos where id = "+id;

        Cursor mCursor = readSQliteDatabase().rawQuery(mStringCheckEvento,null);

        while (mCursor.moveToNext())
        {
            num = mCursor.getInt(0);
        }

        return num>0 ? true : false;
    }

    public boolean updateEventoContenido(cEventos mEventos)
    {

        /**
         * eventos (id INTEGER PRIMARY KEY, titulo TEXT, fecha_publicacion_inicio TEXT" +
         *                     ",fecha_publicacion_fin TEXT,contenido TEXT,descripcion_corta TEXT,
         *                     url_imagen_miniatura TEXT" +
         *                     ",url_imagen_principal TEXT,direccion TEXT,page INTEGER)
         *
         * **/


        String mStringUpdate = "update eventos set contenido = '"+mEventos.getContenido()+"'" +
                ",url_imagen_principal = '"+mEventos.getUri_foto_imagen_principal()+"' " +
                "where id = "+mEventos.getId_evento();


        try {
            writeSQliteDatabase().execSQL(mStringUpdate);
            return true;
        }catch (SQLException e)
        {
            Log.e("SQLEvento","update "+e.getMessage());
            return  false;
        }

    }


}

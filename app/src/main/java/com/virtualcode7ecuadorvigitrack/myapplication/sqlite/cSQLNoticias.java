package com.virtualcode7ecuadorvigitrack.myapplication.sqlite;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.virtualcode7ecuadorvigitrack.myapplication.application.cApplication;
import com.virtualcode7ecuadorvigitrack.myapplication.models.cNoticias;

import java.util.ArrayList;
import java.util.List;

public class cSQLNoticias
{

    private cSQLiteOpenHelper mSqLiteOpenHelper;

    public cSQLNoticias()
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


    public void InsertNoticias(int page,List<cNoticias> mNoticiasList)
    {
        deleteNoticias(page);

        /**
         * id INTEGER PRIMARY KEY, titulo TEXT, fecha_publicacion TEXT" +
         *                     ",contenido TEXT,descripcion_corta TEXT,url_imagen_miniatura TEXT" +
         *                     ",url_imagen_principal TEXT,page INTEGER
         *
         * **/


        for (int i=0;i<mNoticiasList.size();i++)
        {
            String mStrinInsertNoticia =
                    "insert into noticias(id,titulo,fecha_publicacion,contenido,descripcion_corta,url_imagen_miniatura,page) " +
                            "values("+mNoticiasList.get(i).getId_noticias()+",'"+mNoticiasList.get(i).getTitulo()+"'" +
                            ",'"+mNoticiasList.get(i).getFecha()+"','"+mNoticiasList.get(i).getTextoNoticia()+"'" +
                            ",'"+mNoticiasList.get(i).getDescriptionCorta()+"'" +
                            ",'"+mNoticiasList.get(i).getmUriPicturePrincipalNoticia()+"',"+mNoticiasList.get(i).getNumPage()+")";

            Log.e("insert_noticia",mStrinInsertNoticia);

            if(!checkNoticia(mNoticiasList.get(i).getId_noticias()))
            {
                writeSQliteDatabase().execSQL(mStrinInsertNoticia);
            }else{
                updateNoticia(mNoticiasList.get(i));
            }
        }
    }

    private void updateNoticia(cNoticias oN)
    {
        String mStrinUpdateNoticia = "update noticias set titulo='"+oN.getTitulo()+"'" +
                ",fecha_publicacion='"+oN.getFecha()+"',descripcion_corta='"+oN.getDescriptionCorta()+"'" +
                ",url_imagen_miniatura='"+oN.getmUriPicturePrincipalNoticia()+"'" +
                ",page="+oN.getNumPage()+" where = id = "+oN.getId_noticias();

        try {
            writeSQliteDatabase().execSQL(mStrinUpdateNoticia);
        }catch (SQLException mSqlException){
            Log.e("updateNoticia",mSqlException.getMessage());
        }

    }

    public boolean deleteNoticias(int page)
    {
        String mStringQueryDelete = "delete from noticias where page = "+page;
        Log.e("delete",mStringQueryDelete);
        writeSQliteDatabase().execSQL(mStringQueryDelete);
        //writeSQliteDatabase().delete("noticias","page=?",new String[]{String.valueOf(page)});
        return true;
    }

    public List<cNoticias> readNoticias(int page){

        String mStringReadNoticiasPage = "select id,titulo,fecha_publicacion,descripcion_corta,url_imagen_miniatura,page from noticias where page = "+page;

        Cursor mCursor = readSQliteDatabase().rawQuery(mStringReadNoticiasPage,null);

        List<cNoticias> mNoticiasList = new ArrayList<>();

        while (mCursor.moveToNext())
        {
            cNoticias mNoticias = new cNoticias();
            mNoticias.setFecha(mCursor.getString(2));
            mNoticias.setId_noticias(mCursor.getInt(0));
            mNoticias.setmUriPicturePrincipalNoticia(mCursor.getString(4));/*imagen miniatura**/
            mNoticias.setTitulo(mCursor.getString(1));
            mNoticias.setDescriptionCorta(mCursor.getString(3));

            mNoticiasList.add(mNoticias);
        }
        return mNoticiasList;
    }

    public cNoticias readNoticia(int id)
    {
        cNoticias mNoticias = new cNoticias();

        String mStringReadNoticiasPage = "select contenido,url_imagen_principal from noticias where id = "+id;

        Cursor mCursor = readSQliteDatabase().rawQuery(mStringReadNoticiasPage,null);

        while (mCursor.moveToNext())
        {

            mNoticias.setmUriPictureContenidoNoticia(mCursor.getString(1));/*imagen contenido principal**/
            mNoticias.setTextoNoticia(mCursor.getString(0));

            return mNoticias;
        }

        return null;
    }

    private boolean checkNoticia(int id)
    {
        int num = 0;

        String mStringCheckNoticias = "select count(id) from noticias where id = "+id;

        Cursor mCursor = readSQliteDatabase().rawQuery(mStringCheckNoticias,null);

        while (mCursor.moveToNext())
        {
            num = mCursor.getInt(0);
        }

        return num>0 ? true : false;
    }

    public boolean updateNoticiaContenido(cNoticias mNoticias)
    {
        String mStringUpdate = "update noticias set contenido = '"+mNoticias.getTextoNoticia()+"'" +
                ",url_imagen_principal = '"+mNoticias.getmUriPictureContenidoNoticia()+"' where id = "+mNoticias.getId_noticias();
        writeSQliteDatabase().execSQL(mStringUpdate);

        return true;
    }


}

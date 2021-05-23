package com.virtualcode7ecuadorvigitrack.myapplication.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class cSQLiteOpenHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cam.db";

    private static final String SQL_CREATE_ENTRIES_NOTICIA =
            "CREATE TABLE IF NOT EXISTS noticias (id INTEGER PRIMARY KEY, titulo TEXT, fecha_publicacion TEXT" +
                    ",contenido TEXT,descripcion_corta TEXT,url_imagen_miniatura TEXT" +
                    ",url_imagen_principal TEXT,page INTEGER)";


    private static final String SQL_CREATE_ENTRIES_EVENTO =
            "CREATE TABLE IF NOT EXISTS eventos (id INTEGER PRIMARY KEY, titulo TEXT, fecha_publicacion_inicio TEXT" +
                    ",fecha_publicacion_fin TEXT,contenido TEXT,descripcion_corta TEXT,url_imagen_miniatura TEXT" +
                    ",url_imagen_principal TEXT,direccion TEXT,page INTEGER)";

    private static final String SQL_DELETE_ENTRIES_NOTICIA =
            "DROP TABLE IF EXISTS noticias";

    public cSQLiteOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES_NOTICIA);
        db.execSQL(SQL_CREATE_ENTRIES_EVENTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_CREATE_ENTRIES_NOTICIA);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }
}

package com.example.crudsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {
    //constructor
    public DatabaseHandler( Context context) {
        super(context, "registro.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE persona(idPersona TEXT PRIMARY KEY, nombre TEXT, apellido TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS persona");
    }

    //metodos CRUD

    //CREATE
    public Boolean insertData(String idPersona, String nombre, String apellido){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idPersona", idPersona);
        contentValues.put("nombre", nombre);
        contentValues.put("apellido", apellido);
        long result = db.insert("persona",null, contentValues);
        if(result== -1){
            return false;
        }else{
            return true;
        }
    }

    //obtener los datos
    //READ
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM persona", null);
        return cursor;
    }

}





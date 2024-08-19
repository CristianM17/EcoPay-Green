package com.example.login.DB;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBconexion extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbreciclaje";
    private static final int DB_VERSION = 6;

    public DBconexion(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(DBmanager.TABLA_USER_CREATE);
            sqLiteDatabase.execSQL(DBmanager.TABLA_CATEGORIA_CREATE);
            sqLiteDatabase.execSQL(DBmanager.TABLA_CONSEJOS_CREATE);
            sqLiteDatabase.execSQL(DBmanager.TABLA_REGISTRO_CREATE);
            Log.d("insercion", "Tablas creadas correctamente");
        } catch (SQLException e) {
            Log.e("insercion", "Error al crear las tablas: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Elimina las tablas existentes
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBmanager.TABLA_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBmanager.TABLA_CATEGORIA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBmanager.TABLA_CONSEJOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBmanager.TABLA_REGISTRO);

        // Vuelve a crear las tablas
        onCreate(sqLiteDatabase);
    }
}

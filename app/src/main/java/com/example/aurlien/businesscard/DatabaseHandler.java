package com.example.aurlien.businesscard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aurélien on 10/04/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper{
    public static final String BC_ID = "_id";
    public static final String BC_NOM = "nom";
    public static final String BC_TEL = "telephone";
    public static final String BC_EMAIL = "email";

    public static final String BC_TABLE_NAME = "Businesscard";
    public static final String BC_TABLE_CREATE =
            "CREATE TABLE " + BC_TABLE_NAME + " (" +
                    BC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BC_NOM + " TEXT, " +
                    BC_TEL + " TEXT, " +
                    BC_EMAIL + " TEXT);";

    public static final String METIER_TABLE_DROP = "DROP TABLE IF EXISTS " + BC_TABLE_NAME + ";";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BC_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(METIER_TABLE_DROP);
        onCreate(db);
    }
}

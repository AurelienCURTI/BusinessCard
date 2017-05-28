package com.example.aurlien.businesscard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Aurélien on 10/04/2017.
 */

public abstract class DAOBase {
    // Si je décide de la mettre à jour, il faudra changer cet attribut
    protected final static int VERSION = 5;
    // Le nom du fichier qui représente ma base
    protected final static String NOM = "database.db";

    protected SQLiteDatabase mDb = null;
    protected DatabaseHandler mHandler = null;

    public DAOBase(Context pContext) {
        this.mHandler = mHandler.getInstance(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open() {
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    public void close() {
        mDb.close();
    }

}

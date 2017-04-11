package com.example.aurlien.businesscard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Aurélien on 10/04/2017.
 */

public abstract class DAOBase {
    // Si je décide de la mettre à jour, il faudra changer cet attribut
    protected final static int VERSION = 3;
    // Le nom du fichier qui représente ma base
    protected final static String NOM = "database.db";

    protected SQLiteDatabase mDb = null;
    protected DatabaseHandler mHandler = null;

    public DAOBase(Context pContext) {
        this.mHandler = new DatabaseHandler(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    public void close() {
        mDb.close();
    }


}

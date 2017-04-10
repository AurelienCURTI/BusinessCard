package com.example.aurlien.businesscard;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aurélien on 10/04/2017.
 */

public class BusinessCardDAO extends DAOBase{
    public static final String TABLE_NAME = "businesscard";
    public static final String ID = "id";
    public static final String NOM = "nom";
    public static final String TEL = "telephone";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOM + " TEXT, " + TEL + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public void ajouter(BusinessCard card) {
        ContentValues value = new ContentValues();
        value.put(BusinessCardDAO.NOM, card.getNom());
        value.put(BusinessCardDAO.TEL, card.getTelephone());
        mDb.insert(BusinessCardDAO.TABLE_NAME, null, value);
    }

    public void supprimer(long id) {
        mDb.delete(TABLE_NAME, ID + " = ?", new String[] {String.valueOf(id)});
    }

    public void modifier(BusinessCard card) {
        ContentValues value = new ContentValues();
        value.put(NOM, card.getNom());
        value.put(TEL, card.getTelephone());
        mDb.update(TABLE_NAME, value, ID  + " = ?", new String[] {String.valueOf(card.getId())});
    }

    public BusinessCard selectionner(long id) {
        Cursor c = mDb.rawQuery("select " + NOM + "," + TEL + " from " + TABLE_NAME + " where "+ID+ " = ?", new String[]{String.valueOf(id)});
        if(c.getCount() == 1){
            c.moveToFirst();
            long id_res = c.getLong(0);
            String nom_res = c.getString(1);
            String tel_res = c.getString(2);
            return new BusinessCard(id_res, nom_res, tel_res);
        }
        else {
            return new BusinessCard(null, null, null);
        }
    }

}

package com.example.aurlien.businesscard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Aurélien on 10/04/2017.
 */

public class BusinessCardDAO extends DAOBase{

    public static final String TABLE_NAME = "Businesscard";
    public static final String ID = "_id";
    public static final String NOM = "nom";
    public static final String TEL = "telephone";

    public BusinessCardDAO(Context pContext) {
        super(pContext);
    }

    public void ajouter(BusinessCard card){
        ContentValues value = new ContentValues();
        value.put(BusinessCardDAO.NOM, card.getNom());
        value.put(BusinessCardDAO.TEL, card.getTelephone());
        long insertID = mDb.insert(BusinessCardDAO.TABLE_NAME, null, value);
        if(insertID == -1) {
            Log.e("BusinessCardDAO", "Erreur lors de l'insertion de " + card.toString() + " dans la base");
        }
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

            String nom_res = c.getString(0);
            String tel_res = c.getString(1);
            c.close();
            return new BusinessCard(nom_res, tel_res);
        }
        else {
            c.close();
            return new BusinessCard(null, null);
        }
    }

    public ArrayList<BusinessCard> recupererCartesBdd(){
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);
        ArrayList<BusinessCard> cards = new ArrayList<BusinessCard>();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            BusinessCard card  = new BusinessCard(c.getString(1), c.getString(2));
            card.setId(c.getInt(0));
            Log.d("BCARDDAO", Integer.toString(c.getInt(0)));
            cards.add(card);
            c.moveToNext();
        }
        c.close();
        return cards;
    }
}

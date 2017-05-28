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
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";

    public BusinessCardDAO(Context pContext) {
        super(pContext);
    }

    public void ajouter(BusinessCard card){
        if(isNew(card)){
            ContentValues value = new ContentValues();
            value.put(NOM, card.getNom());
            value.put(TEL, card.getTelephone());
            value.put(EMAIL, card.getEmail());
            value.put(ADDRESS, card.getAddress());
            value.put(LONGITUDE, card.getLongitude());
            value.put(LATITUDE, card.getLatitude());
            long insertID = mDb.insert(BusinessCardDAO.TABLE_NAME, null, value);
            if(insertID == -1) {
                Log.e("BusinessCardDAO", "Erreur lors de l'insertion de " + card.toString() + " dans la base");
            }
        }
        else {
            modifier(card);
        }
    }

    public void supprimer(long id) {
        mDb.delete(TABLE_NAME, ID + " = ?", new String[] {String.valueOf(id)});
    }

    public void modifier(BusinessCard card) {
        ContentValues value = new ContentValues();
        value.put(NOM, card.getNom());
        value.put(TEL, card.getTelephone());
        value.put(EMAIL, card.getEmail());
        value.put(ADDRESS, card.getAddress());
        value.put(LONGITUDE, card.getLongitude());
        value.put(LATITUDE, card.getLatitude());
        mDb.update(TABLE_NAME, value, ID  + " = ?", new String[] {String.valueOf(card.getId())});
    }

    public BusinessCard selectionner(long id) {
        Cursor c = mDb.rawQuery("select " + NOM + "," + TEL + " from " + TABLE_NAME + " where "+ID+ " = ?", new String[]{String.valueOf(id)});
        try {
            if (c.getCount() == 1) {
                c.moveToFirst();

                String nom_res = c.getString(0);
                String tel_res = c.getString(1);
                String email_res = c.getString(2);
                String address_res = c.getString(3);
                String longitude_res = c.getString(4);
                String latitude_res = c.getString(5);
                return new BusinessCard(nom_res, tel_res, email_res, address_res, longitude_res, latitude_res);
            }
            return new BusinessCard(null, null, null, null, null, null);
        }
        finally {
            c.close();
        }
    }

    public boolean isNew(BusinessCard card){
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where "+NOM+ " = ? AND " + TEL + " = ?", new String[]{String.valueOf(card.getNom()), String.valueOf(card.getTelephone())});
        try {
            if (c.getCount() == 1) {
                c.moveToFirst();
                card.setId(c.getInt(0));
                return false;
            }
            return true;
        }
        finally {
            c.close();
        }
    }

    public ArrayList<BusinessCard> recupererCartesBdd(){
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);
        ArrayList<BusinessCard> cards = new ArrayList<BusinessCard>();
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                BusinessCard card = new BusinessCard(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6));
                card.setId(c.getInt(0));
                cards.add(card);
                c.moveToNext();
            }
            return cards;
        }
        finally {
            c.close();
        }
    }
}

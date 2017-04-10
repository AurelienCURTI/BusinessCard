package com.example.aurlien.businesscard;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Michael on 04/04/2017.
 */

public class BusinessCard {
    long id;
    String nom;
    String telephone;

    public BusinessCard(long id, String nom, String telephone){
        this.id = id;
        this.nom = nom;
        this.telephone = telephone;
    }

    public BusinessCard(String nom, String telephone){
        this.nom = nom;
        this.telephone = telephone;
    }

    public long getId(){ return this.id;}

    public String getNom(){
        return this.nom;
    }

    public String getTelephone(){
        return this.telephone;
    }

    public String toString(){return "ID : " + this.id + " NOM : " + this.nom + " TELEPHONE : " + this.telephone;}

}
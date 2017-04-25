package com.example.aurlien.businesscard;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Michael on 04/04/2017.
 */

public class BusinessCard {
    private int id;
    private String nom;
    private String telephone;
    private String email;

    public BusinessCard(String nom, String telephone){
        super();
        this.nom = nom;
        this.telephone = telephone;
    }

    public BusinessCard(String nom, String telephone, String email){
        super();
        this.nom = nom;
        this.telephone = telephone;
        this.email = email;
    }

    public long getId(){ return this.id;}

    public String getNom(){
        return this.nom;
    }

    public String getTelephone(){
        return this.telephone;
    }

    public String getEmail(){
        return this.email;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setTelephone(String telephone){
        this.telephone = telephone;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String toString(){return "ID : " + this.id + " NOM : " + this.nom + " TELEPHONE : " + this.telephone + " EMAIL : " + this.email;}

    public String serializeJSON(){return"{'ID':"+this.id+",'NOM':"+this.nom+",'TELEPHONE':"+this.telephone+",'EMAIL':"+this.email+"}";}

}

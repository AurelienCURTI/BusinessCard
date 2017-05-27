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
    private String address;

    public BusinessCard(String nom, String telephone){
        super();
        this.nom = nom;
        this.telephone = telephone;
        this.email = "NC";
        this.address = "NC";
    }

    public BusinessCard(String nom, String telephone, String email){
        super();
        this.nom = nom;
        this.telephone = telephone;
        if(email == null || email.equals("")){
            this.email = "NC";
        }
        else {
            this.email = email;
        }
        this.address = "NC";
    }

    public BusinessCard(String nom, String telephone, String email, String address){
        super();
        this.nom = nom;
        this.telephone = telephone;
        if(email == null || email.equals("")){
            this.email = "NC";
        }
        else {
            this.email = email;
        }
        if(address == null || address.equals("")){
            this.address = "NC";
        }
        else {
            this.address = email;
        }
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

    public String getAddress(){
        return this.address;
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

    public void setAddress(String address){
        this.address = address;
    }

    public String toString(){return "ID : " + this.id + " NOM : " + this.nom + " TELEPHONE : " + this.telephone + " EMAIL : " + this.email + " ADDRESS : " + this.address;}

    public String serializeJSON(){return"{'ID':"+this.id+",'NOM':"+this.nom+",'TELEPHONE':"+this.telephone+",'EMAIL':"+this.email+",'ADDRESS':"+this.address+"}";}

}

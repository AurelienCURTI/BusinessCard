package com.example.aurlien.businesscard;

/**
 * Created by Michael on 04/04/2017.
 */

public class BusinessCard {
    private int id;
    private String nom;
    private String telephone;
    private String email;
    private String address;
    private String longitude;
    private String latitude;

    public BusinessCard(String nom, String telephone){
        super();
        this.nom = nom;
        this.telephone = telephone;
        this.email = "NC";
        this.address = "NC";
    }

    public BusinessCard(String nom, String telephone, String email, String address, String longitude, String latitude){
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
            this.address = address;
        }
        this.longitude = longitude;
        this.latitude = latitude;
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

    public String getLongitude(){return this.longitude;}

    public String getLatitude(){return this.latitude;}

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

    public void setLongitude(String longitude){this.longitude = longitude;}

    public void setLatitude(String latitude){this.latitude = latitude;}

    public String toString(){return "ID : " + this.id + " NOM : " + this.nom + " TELEPHONE : " + this.telephone + " EMAIL : " + this.email + " ADDRESS : " + this.address;}

}

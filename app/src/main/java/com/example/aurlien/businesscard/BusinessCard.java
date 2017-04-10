package com.example.aurlien.businesscard;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Michael on 04/04/2017.
 */

public class BusinessCard {

    String name;
    String number;

    public BusinessCard(String name, String number){
        this.name = name;
        this.number = number;
    }

    public String getName(){
        return name;
    }

    public String getNumber(){
        return number;
    }

}

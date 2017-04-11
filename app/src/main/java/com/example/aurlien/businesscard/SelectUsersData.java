package com.example.aurlien.businesscard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static com.example.aurlien.businesscard.MainActivity.PICK_CONTACT_REQUEST;

/**
 * Created by Aurélien on 04/04/2017.
 */

public class SelectUsersData extends AppCompatActivity {
    private BusinessCardDAO bcardDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_users_data);
        Button create_card = (Button) findViewById(R.id.gen_bus_card);
        bcardDao = new BusinessCardDAO(this);
        bcardDao.open();
        final Intent intent = getIntent();
        final TextView nom_val = (TextView) findViewById(R.id.name_selected_contact);
        final TextView numero_val = (TextView) findViewById(R.id.phone_selected_contact);

        if (intent != null) {
            nom_val.setText(intent.getStringExtra("K_NOM"));
            numero_val.setText(intent.getStringExtra("K_NUMERO"));

        }

        create_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessCard card = new BusinessCard(nom_val.getText().toString(), numero_val.getText().toString());
                ArrayList<BusinessCard> list;
                try {
                    bcardDao.supprimer(0);
                    bcardDao.ajouter(card);
                    list = bcardDao.recupererCartesBdd();
                    for(BusinessCard cardload : list){
                        Log.d("SelectActivity", cardload.toString());
                    }

                    /*FileOutputStream outputStream;
                    // write file in data/data/com.example.aurlien.businesscard/files
                    outputStream = openFileOutput(card.getNom() + "_card", Context.MODE_PRIVATE);
                    outputStream.write((card.getNom() + "\n").getBytes());
                    outputStream.write((card.getTelephone() + "\n").getBytes());
                    outputStream.close();*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // Log.d("SelectActivity", "Bonjour");

    }
}

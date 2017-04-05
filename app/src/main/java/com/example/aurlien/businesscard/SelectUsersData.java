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

import static com.example.aurlien.businesscard.MainActivity.PICK_CONTACT_REQUEST;

/**
 * Created by Aurélien on 04/04/2017.
 */

public class SelectUsersData extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_users_data);
        Button create_card = (Button) findViewById(R.id.gen_bus_card);
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
                try {
                    FileOutputStream outputStream;
                    // write file in data/data/com.example.aurlien.businesscard/files
                    outputStream = openFileOutput(card.getName() + "_card", Context.MODE_PRIVATE);
                    outputStream.write((card.getName() + "\n").getBytes());
                    outputStream.write((card.getNumber() + "\n").getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // Log.d("SelectActivity", "Bonjour");

    }
}

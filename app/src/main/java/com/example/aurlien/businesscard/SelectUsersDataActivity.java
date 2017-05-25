package com.example.aurlien.businesscard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aurélien on 04/04/2017.
 */

public class SelectUsersDataActivity extends AppCompatActivity {
    private BusinessCardDAO bcardDao;
    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_users_data);
        Button create_card = (Button) findViewById(R.id.gen_bus_card);
        Button afficher_card = (Button) findViewById(R.id.aff_bus_card);
        final CheckBox checkBox_email = (CheckBox) findViewById(R.id.checkBox_email);
        final CheckBox checkBox_address = (CheckBox) findViewById(R.id.checkBox_address);
        bcardDao = new BusinessCardDAO(this);
        bcardDao.open();
        final Intent intent = getIntent();
        final TextView nom_val = (TextView) findViewById(R.id.name_selected_contact);
        final TextView numero_val = (TextView) findViewById(R.id.phone_selected_contact);
        final TextView email_val = (TextView) findViewById(R.id.email_selected_contact);
        final TextView address_val = (TextView) findViewById(R.id.address_selected_contact);

        if (intent != null) {
            nom_val.setText(intent.getStringExtra("K_NOM"));
            numero_val.setText(intent.getStringExtra("K_NUMERO"));
            email_val.setText(intent.getStringExtra("K_EMAIL"));
            address_val.setText(intent.getStringExtra("K_ADDRESS"));
        }

        create_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessCard card = new BusinessCard(nom_val.getText().toString(), numero_val.getText().toString());
                if (checkBox_email.isChecked()){
                    card.setEmail(email_val.getText().toString());
                }
                if (checkBox_address.isChecked()){
                    card.setAddress(address_val.getText().toString());
                    Log.d("TAG", address_val.getText().toString());
                }
                //BusinessCard card = new BusinessCard(nom_val.getText().toString(), numero_val.getText().toString(), email_val.getText().toString());
                ArrayList<BusinessCard> list;
                try {
                    bcardDao.ajouter(card);
                    Intent intent = new Intent(SelectUsersDataActivity.this, ListCardsActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        afficher_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectUsersDataActivity.this, CarteVisiteActivity.class);
                //On passe ces données à l'autre activité
                intent.putExtra("K_NOM", nom_val.getText().toString());
                intent.putExtra("K_NUMERO", numero_val.getText().toString());
                if (checkBox_email.isChecked()){
                    intent.putExtra("K_EMAIL", email_val.getText().toString());
                }
                if (checkBox_address.isChecked()){
                    intent.putExtra("K_ADDRESS", address_val.getText().toString());
                }
                startActivity(intent);
            }
        });
    }
}

package com.example.aurlien.businesscard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aurélien on 04/04/2017.
 */

public class SelectUsersDataActivity extends AppCompatActivity {
    private BusinessCardDAO bcardDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_users_data);
        Button create_card = (Button) findViewById(R.id.gen_bus_card);
        Button afficher_card = (Button) findViewById(R.id.aff_bus_card);
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
                startActivity(intent);
            }
        });
    }
}

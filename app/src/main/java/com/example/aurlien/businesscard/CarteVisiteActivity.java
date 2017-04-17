package com.example.aurlien.businesscard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Michael on 11/04/2017.
 */

public class CarteVisiteActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.card);
        TextView nom_val = (TextView) findViewById(R.id.name);
        TextView numero_val = (TextView) findViewById(R.id.phone);
        TextView email_val = (TextView) findViewById(R.id.email);
        Intent intent = getIntent();
            nom_val.setText(intent.getStringExtra("K_NOM"));
            numero_val.setText(intent.getStringExtra("K_NUMERO"));
            email_val.setText(intent.getStringExtra("K_EMAIL"));

    }
}
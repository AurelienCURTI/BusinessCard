package com.example.aurlien.businesscard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Aurélien on 04/04/2017.
 */

public class SelectUsersData extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_users_data);

        Intent intent = getIntent();
        TextView nom_val = (TextView) findViewById(R.id.name_selected_contact);
        TextView numero_val = (TextView) findViewById(R.id.phone_selected_contact);

        if (intent != null) {
            nom_val.setText(intent.getStringExtra("K_NOM"));
            numero_val.setText(intent.getStringExtra("K_NUMERO"));
        }
        // Log.d("SelectActivity", "Bonjour");
    }
}

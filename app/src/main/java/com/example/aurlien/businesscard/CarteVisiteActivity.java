package com.example.aurlien.businesscard;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.aurlien.businesscard.MainActivity.PICK_CONTACT_REQUEST;

/**
 * Created by Michael on 11/04/2017.
 */

public class CarteVisiteActivity extends AppCompatActivity {
    BusinessCard card;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.card);
        Button send_sms_card = (Button) findViewById(R.id.btn_send_sms);
        TextView nom_val = (TextView) findViewById(R.id.name);
        TextView numero_val = (TextView) findViewById(R.id.phone);
        TextView email_val = (TextView) findViewById(R.id.email);
        TextView address_val = (TextView) findViewById(R.id.address);
        final Intent intent = getIntent();

        nom_val.setText(intent.getStringExtra("K_NOM"));
        numero_val.setText(intent.getStringExtra("K_NUMERO"));
        email_val.setText(intent.getStringExtra("K_EMAIL"));
        address_val.setText(intent.getStringExtra("K_ADDRESS"));
        card = new BusinessCard(intent.getStringExtra("K_NOM"), intent.getStringExtra("K_NUMERO"), intent.getStringExtra("K_EMAIL"), intent.getStringExtra("K_ADDRESS"));
        send_sms_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Test du type de la requete
        if (requestCode == PICK_CONTACT_REQUEST) {
            // On verifie que la requete est correcte
            if (resultCode == RESULT_OK) {
                // Uri qui pointe sur le contact selectionné
                Uri contactUri = data.getData();
                // On definis ce que l'on souhaite récupéré via notre requete
                String[] projection = {
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                };

                // Interrogation de la base de données de contact du téléphone
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrouver le tableau
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phoneNumber = cursor.getString(column);

                // Get the default instance of SmsManager
                SmsManager smsManager = SmsManager.getDefault();
                String smsBody = "{nom:" + card.getNom() +",numero:" + card.getTelephone() + ",email:" + card.getEmail() + ",address:" + card.getAddress() + "}";
                // Send a text based SMS
                smsManager.sendTextMessage(phoneNumber, null, smsBody, null, null);
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "Carte envoyé par SMS", duration);
                toast.show();

            }
        }
    }
}
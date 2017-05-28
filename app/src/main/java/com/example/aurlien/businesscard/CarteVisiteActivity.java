package com.example.aurlien.businesscard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;


import static com.example.aurlien.businesscard.MainActivity.PICK_CONTACT_REQUEST;

/**
 * Created by Michael on 11/04/2017.
 */

public class CarteVisiteActivity extends AppCompatActivity {
    BusinessCard card;
    private BusinessCardDAO bcardDao;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.card);
        Button send_sms_card = (Button) findViewById(R.id.btn_send_sms);
        Button localiser = (Button) findViewById(R.id.google_maps_access);
        Button deletecard = (Button) findViewById(R.id.deletecard);
        Button back = (Button) findViewById(R.id.back);
        Button save = (Button) findViewById(R.id.save);
        final TextView nom_val = (TextView) findViewById(R.id.name);
        final TextView numero_val = (TextView) findViewById(R.id.phone);
        final TextView email_val = (TextView) findViewById(R.id.email);
        final TextView address_val = (TextView) findViewById(R.id.address);
        bcardDao = new BusinessCardDAO(this);
        final Intent intent = getIntent();
        nom_val.setText(intent.getStringExtra("K_NOM"));
        numero_val.setText(intent.getStringExtra("K_NUMERO"));
        email_val.setText(intent.getStringExtra("K_EMAIL"));
        address_val.setText(intent.getStringExtra("K_ADDRESS"));
        card = new BusinessCard(intent.getStringExtra("K_NOM"), intent.getStringExtra("K_NUMERO"), intent.getStringExtra("K_EMAIL"), intent.getStringExtra("K_ADDRESS"));
        card.setId((int)intent.getLongExtra("K_ID", 0));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarteVisiteActivity.this, ListCardsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card.setNom(nom_val.getText().toString());
                card.setTelephone(numero_val.getText().toString());
                card.setEmail(email_val.getText().toString());
                card.setAddress(address_val.getText().toString());
                bcardDao.open();
                bcardDao.modifier(card);
                int duration = Toast.LENGTH_LONG;
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, "Modifications sauvegardés", duration);
                toast.show();
                Intent intent = new Intent(CarteVisiteActivity.this, ListCardsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        deletecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CarteVisiteActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Confirmation");
                builder.setMessage("Cette carte sera supprimé, souhaitez-vous continuer ?");
                builder.setPositiveButton("OUI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bcardDao.open();
                                bcardDao.supprimer(intent.getLongExtra("K_ID", 0));
                                int duration = Toast.LENGTH_LONG;
                                Context context = getApplicationContext();
                                Toast toast = Toast.makeText(context, "Carte supprimé", duration);
                                toast.show();
                                Intent intent = new Intent(CarteVisiteActivity.this, ListCardsActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        send_sms_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
            }
        });
        localiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CarteVisiteActivity.this, MapsActivity.class);
                startActivity(intent);
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
                String smsBody;
                //Creation de l'objet JSON
                JSONObject object;
                try{
                    object = new JSONObject();
                    object.put("nom", card.getNom());
                    object.put("numero", card.getTelephone());
                    object.put("email", card.getEmail());
                    object.put("adresse", card.getAddress());
                    smsBody = object.toString();
                    smsBody = smsBody.replace("{", "(");
                    smsBody = smsBody.replace("}", ")");
                    smsManager.sendTextMessage(phoneNumber, null, smsBody, null, null);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "Carte envoyé par SMS", duration);
                toast.show();
            }
        }
    }
}
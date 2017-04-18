package com.example.aurlien.businesscard;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity {
    private android.widget.Button contact_list;
    private static final String TAG = "MyActivity";
    static final int PICK_CONTACT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contact_list = (Button)findViewById(R.id.contacts_access);
        contact_list.setOnClickListener(new View.OnClickListener() {
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
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                };

                String[] projectionEmail = {
                        ContactsContract.CommonDataKinds.Email.ADDRESS
                };

                // Interrogation de la base de données de contact du téléphone
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                //int id = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                /*
                Cursor cursorEmail = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        projectionEmail,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{"1"}, null); //TODO : Recuperer l'id correspondant au contact actuel (ici 1 pour le 1er contact)
                cursorEmail.moveToFirst();
                */
                // Retrouver le nom
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String nom = cursor.getString(column);

                // Retrouver le tableau
                column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String numero = cursor.getString(column);

                //int columnEmail = cursorEmail.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS);
                //String email = cursorEmail.getString(columnEmail);

                Intent intent = new Intent(MainActivity.this, SelectUsersDataActivity.class);
                //On passe ces données à l'autre activité
                intent.putExtra("K_NOM", nom);
                intent.putExtra("K_NUMERO", numero);
                //intent.putExtra("K_EMAIL", email);

                startActivity(intent);
            }
        }
    }




}

package com.example.aurlien.businesscard;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {


    private android.widget.Button contact_list;
    private android.widget.Button cards_list;
    private static final String TAG = "MyActivity";
    static final int PICK_CONTACT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contact_list = (Button)findViewById(R.id.contacts_access);
        cards_list = (Button)findViewById(R.id.cards_access);
        contact_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
            }
        });
        cards_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListCardsActivity.class);
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
                String email = "";
                Uri contactUri = data.getData();
                // On definis ce que l'on souhaite récupéré via notre requete
                String[] projection = {
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                };

                String[] projectionEmail = {
                        ContactsContract.Data.DATA1
                };
                Cursor emailCur = getContentResolver().query(
                        ContactsContract.Data.CONTENT_URI,
                        null,
                        ContactsContract.Data.HAS_PHONE_NUMBER + "!=0 AND (" + ContactsContract.Data.MIMETYPE + "=? OR " + ContactsContract.Data.MIMETYPE + "=?)",
                        new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE},
                        ContactsContract.Data.CONTACT_ID);

                // Interrogation de la base de données de contact du téléphone
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrouver le nom
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String nom = cursor.getString(column);

                // Retrouver le tableau
                column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String numero = cursor.getString(column);

                while(emailCur.moveToNext()) {
                    //long id = emailCur.getLong(emailCur.getColumnIndex(ContactsContract.Data.CONTACT_ID));
                    String name = emailCur.getString(emailCur.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                    String data1 = emailCur.getString(emailCur.getColumnIndex(ContactsContract.Data.DATA1));
                    if(name.equals(nom)){
                        email = data1;
                    }
                }

                Intent intent = new Intent(MainActivity.this, SelectUsersDataActivity.class);
                //On passe ces données à l'autre activité
                intent.putExtra("K_NOM", nom);
                intent.putExtra("K_NUMERO", numero);
                intent.putExtra("K_EMAIL", email);

                startActivity(intent);
            }
        }
    }
}

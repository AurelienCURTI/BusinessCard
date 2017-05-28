package com.example.aurlien.businesscard;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {


    private android.widget.Button contact_list;
    private android.widget.Button cards_list;
    private android.widget.Button google_maps_list;
    private static final String TAG = "MyActivity";
    static final int PICK_CONTACT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contact_list = (Button)findViewById(R.id.contacts_access);
        cards_list = (Button)findViewById(R.id.cards_access);
        google_maps_list = (Button) findViewById(R.id.google_maps_access);
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
                String address = "";
                Uri contactUri = data.getData();
                // On definis ce que l'on souhaite récupéré via notre requete
                String[] projection = {
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS
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

                Cursor addresseCur = getContentResolver().query(
                        ContactsContract.Data.CONTENT_URI,
                        null,
                        ContactsContract.Data.HAS_PHONE_NUMBER + "!=0 AND (" + ContactsContract.Data.MIMETYPE + "=? OR " + ContactsContract.Data.MIMETYPE + "=?)",
                        new String[]{ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE},
                        ContactsContract.Data.CONTACT_ID);

                // Interrogation de la base de données de contact du téléphone
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                try {
                    cursor.moveToFirst();

                    // Retrouver le nom
                    int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    String nom = cursor.getString(column);

                    // Retrouver le tableau
                    column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String numero = cursor.getString(column);

                    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Criteria criteria = new Criteria();
                    String bestProvider = locationManager.getBestProvider(criteria, false);
                    Location location = locationManager.getLastKnownLocation(bestProvider);
                    String longitude = null;
                    String latitude = null;
                    if(location != null) {
                        longitude = String.valueOf(location.getLongitude());
                        latitude = String.valueOf(location.getLatitude());
                    }
                    if (longitude == null){
                        longitude = "122.0840";
                    }
                    if (latitude == null){
                        latitude = "37.4220";
                    }


                    while (emailCur.moveToNext()) {
                        //long id = emailCur.getLong(emailCur.getColumnIndex(ContactsContract.Data.CONTACT_ID));
                        String name = emailCur.getString(emailCur.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                        String dataEmail = emailCur.getString(emailCur.getColumnIndex(ContactsContract.Data.DATA1));
                        if (name.equals(nom)) {
                            email = dataEmail;
                        }
                    }
                    while (addresseCur.moveToNext()) {
                        //long id = emailCur.getLong(emailCur.getColumnIndex(ContactsContract.Data.CONTACT_ID));
                        String name = addresseCur.getString(addresseCur.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                        String dataAddress = addresseCur.getString(addresseCur.getColumnIndex(ContactsContract.Data.DATA1));
                        if (name.equals(nom)) {
                            address = dataAddress;
                        }
                    }

                    Intent intent = new Intent(MainActivity.this, SelectUsersDataActivity.class);
                    //On passe ces données à l'autre activité
                    intent.putExtra("K_NOM", nom);
                    intent.putExtra("K_NUMERO", numero);
                    intent.putExtra("K_EMAIL", email);
                    intent.putExtra("K_ADDRESS", address);
                    intent.putExtra("K_LONGITUDE", longitude);
                    intent.putExtra("K_LATITUDE", latitude);

                    startActivity(intent);
                }
                finally {
                    cursor.close();
                    emailCur.close();
                    addresseCur.close();
                }
            }
        }
    }
}

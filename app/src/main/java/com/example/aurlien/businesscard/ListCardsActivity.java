package com.example.aurlien.businesscard;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListCardsActivity extends ListActivity {
    private BusinessCardDAO bcardDao;
    ArrayAdapter<String> listAdapter;
    ArrayList<BusinessCard> lbcards;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cards);
        Button refresh = (Button) findViewById(R.id.refreshlist);
        Button back = (Button) findViewById(R.id.back);
        bcardDao = new BusinessCardDAO(this);
        bcardDao.open();
        listAdapter = new ArrayAdapter<String>(ListCardsActivity.this, android.R.layout.simple_list_item_1);
        lbcards = bcardDao.recupererCartesBdd();
        int cpt = 1;
        for(BusinessCard card : lbcards){
            listAdapter.add(cpt + ". Carte de : " + card.getNom());
            cpt++;
        }
        getListView().setAdapter(listAdapter);
        ListView lv = getListView();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListCardsActivity.this, ListCardsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListCardsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                long id = lbcards.get(position).getId();
                String nom = lbcards.get(position).getNom();
                String telephone = lbcards.get(position).getTelephone();
                String email = lbcards.get(position).getEmail();
                String address = lbcards.get(position).getAddress();
                String longitude = lbcards.get(0).getLongitude();
                String latitude = lbcards.get(position).getLatitude();
                Intent intent = new Intent(ListCardsActivity.this, CarteVisiteActivity.class);
                //On passe ces données à l'autre activité
                intent.putExtra("K_ID", id);
                intent.putExtra("K_NOM", nom);
                intent.putExtra("K_NUMERO", telephone);
                intent.putExtra("K_EMAIL", email);
                intent.putExtra("K_ADDRESS", address);
                intent.putExtra("K_LONGITUDE", longitude);
                intent.putExtra("K_LATITUDE", latitude);
                startActivity(intent);
                finish();
            }
        });
    }
}

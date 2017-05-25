package com.example.aurlien.businesscard;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
        bcardDao = new BusinessCardDAO(this);
        bcardDao.open();
        listAdapter = new ArrayAdapter<String>(ListCardsActivity.this, android.R.layout.simple_list_item_1);
        lbcards = bcardDao.recupererCartesBdd();
        for(BusinessCard card : lbcards){
            listAdapter.add(card.toString());
        }
        getListView().setAdapter(listAdapter);
        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String nom = lbcards.get(position).getNom();
                String telephone = lbcards.get(position).getTelephone();
                String email = lbcards.get(position).getEmail();
                String address = lbcards.get(position).getAddress();
                Intent intent = new Intent(ListCardsActivity.this, CarteVisiteActivity.class);
                //On passe ces données à l'autre activité
                intent.putExtra("K_NOM", nom);
                intent.putExtra("K_NUMERO", telephone);
                intent.putExtra("K_EMAIL", email);
                intent.putExtra("K_ADDRESS", address);
                startActivity(intent);
            }
        });
    }
}

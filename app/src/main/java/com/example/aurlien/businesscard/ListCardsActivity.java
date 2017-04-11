package com.example.aurlien.businesscard;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class ListCardsActivity extends ListActivity {
    private BusinessCardDAO bcardDao;
    ArrayAdapter<String> listAdapter;
    ArrayList<BusinessCard> lbcards;

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
    }
}

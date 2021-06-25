package com.citizensvoice.thevoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

public class Home extends AppCompatActivity {

    RecyclerView recyclerView, recyclerView1, recyclerView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.rv);
        recyclerView1 = findViewById(R.id.recyclerView);
        recyclerView2 = findViewById(R.id.rv2);
        getItems();
        getItemsa();
        getItemsb();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }
    private void getItems() {
        String[] names = {"Eko Atlantic", "Lagos Light Rail", "World Trade Centre", "Abuja Millennium Tower", "1400 MW Gas Turbine"};
        int[] ids = {R.drawable.abt_a, R.drawable.ea_a, R.drawable.gtps_a,
                R.drawable.lftz_a, R.drawable.llr_a, R.drawable.wtc_a};
        String[] prices = {"1500 votes", "1600 votes", "4000 votes", "1700 votes", "1200 votes"};

        CustomAdapter customAdapter = new CustomAdapter(Home.this, ids, names, prices);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL,
                false));
        recyclerView.setAdapter(customAdapter);
    }

    private void getItemsa() {
        String[] names = {"Eko Atlantic", "Lagos Light Rail", "World Trade Centre", "Abuja Millennium Tower", "1400 MW Gas Turbine"};
        int[] ids = {R.drawable.abt_b, R.drawable.ea_b, R.drawable.gtps_b,
                R.drawable.lftz_b, R.drawable.llr_b, R.drawable.wtc_b};
        String[] prices = {"1500 votes", "1600 votes", "4000 votes", "1700 votes", "1200 votes"};

        CustomAdapter customAdapter = new CustomAdapter(Home.this, ids, names, prices);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL,
                false));
        recyclerView1.setAdapter(customAdapter);
    }

    private void getItemsb() {
        String[] names = {"Eko Atlantic", "Lagos Light Rail", "World Trade Centre", "Abuja Millennium Tower", "1400 MW Gas Turbine"};
        int[] ids = {R.drawable.abt_b, R.drawable.ea_b, R.drawable.gtps_b,
                R.drawable.lftz_b, R.drawable.llr_b, R.drawable.wtc_b};
        String[] prices = {"1500 votes", "1600 votes", "4000 votes", "1700 votes", "1200 votes"};

        CustomAdapter customAdapter = new CustomAdapter(Home.this, ids, names, prices);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL,
                false));
        recyclerView2.setAdapter(customAdapter);
    }
}
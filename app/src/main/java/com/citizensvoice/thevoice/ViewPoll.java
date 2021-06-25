package com.citizensvoice.thevoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPoll extends AppCompatActivity {

    ImageView img;
    TextView likes, Title;
    Button submit;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_poll);

        img = findViewById(R.id.imageView2);
        likes = findViewById(R.id.textView17);
        Title = findViewById(R.id.textView9);
        submit = findViewById(R.id.button2);
        recyclerView = findViewById(R.id.rv);

        final String name = getIntent().getStringExtra("food_name");
        final String price = getIntent().getStringExtra("food_price");
        final int ids = getIntent().getIntExtra("food_pic", R.drawable.abt_a);

        Title.setText(name);
        likes.setText(price);
        img.setImageResource(ids);

        submit();
    }

    public void submit(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewPoll.this, MainActivity.class));
            }
        });

    }
    private void getItems() {
        String[] names = {"Eko Atlantic", "Lagos Light Rail", "World Trade Centre", "Abuja Millennium Tower", "1400 MW Gas Turbine"};
        int[] ids = {R.drawable.abt_a, R.drawable.ea_a, R.drawable.gtps_a,
                R.drawable.lftz_a, R.drawable.llr_a, R.drawable.wtc_a};
        String[] prices = {"1500", "1600", "4000", "1700", "1200"};

        CustomAdapter customAdapter = new CustomAdapter(ViewPoll.this, ids, names, prices);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewPoll.this, LinearLayoutManager.HORIZONTAL,
                false));
        recyclerView.setAdapter(customAdapter);
    }
}
package com.hamz.sistemsarpras;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ProjectorActivity extends AppCompatActivity{

    private Toolbar toolbar;
    Context context;
    CardView cardList, cardRequest, cardBorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projector);

        context = this;
        initComponent();

        cardList = (CardView) findViewById(R.id.cardList);
        cardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ListProjectorAdminActivity.class));
            }
        });

        cardRequest = (CardView) findViewById(R.id.cardRequest);
        cardRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, RequestBorrowedAdminActivity.class));
            }
        });

        cardBorrow = (CardView) findViewById(R.id.cardBorrow);
        cardBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, BorrowedAdminActivity.class));
            }
        });

    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Projektor");
    }


}

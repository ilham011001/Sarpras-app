package com.hamz.sistemsarpras;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivityAdmin extends AppCompatActivity {

    Context context;
    CardView cvInformation;
    TextView tvMyName;
    LinearLayout menu1, menu2, menu3, menu6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        context = this;
        initComponent();

        menu1 = (LinearLayout) findViewById(R.id.menu1);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ReportActivityAdmin.class));
            }
        });

        menu2 = (LinearLayout) findViewById(R.id.menu2);
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SearchActivity.class));
            }
        });

        menu3 = (LinearLayout) findViewById(R.id.menu3);
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ProjectorActivity.class));
            }
        });

        menu6 = (LinearLayout) findViewById(R.id.menu6);
        menu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SettingActivity.class));
            }
        });

    }

    private void initComponent() {

        tvMyName = (TextView) findViewById(R.id.tvMyName);
        tvMyName.setText(new PrefManager(context).getNameUser());
    }
}

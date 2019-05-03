package com.hamz.sistemsarpras;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetIPActivity extends AppCompatActivity {

    Context context;
    EditText etIp;
    PrefManager prefManager;
    Button btnSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ip);

        context = this;
        prefManager = new PrefManager(this);

        if (!prefManager.getIpAddress().equals("empty")) {
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }

        etIp = (EditText) findViewById(R.id.etIp);
        btnSet = (Button) findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.setIpAddress(etIp.getText().toString());
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        });

    }
}

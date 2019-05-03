package com.hamz.sistemsarpras;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    TextView tvIP, tvProject;
    PrefManager prefManager;
    Context context;
    CardView cardIp, cardFolder;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        prefManager = new PrefManager(this);
        context = this;

        tvIP = (TextView) findViewById(R.id.tvIP);
        tvProject = (TextView) findViewById(R.id.tvProject);

        cardIp = (CardView) findViewById(R.id.cardIp);
        cardFolder = (CardView) findViewById(R.id.cardFolder);


        cardIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.description, null);
                final EditText text = (EditText) dialogView.findViewById(R.id.etDesc);
                text.setText(prefManager.getIpAddress());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Your IP Adrress!");
                builder.setView(dialogView);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        prefManager.setIpAddress(text.getText().toString());
                    }
                });
                builder.show();
            }
        });

        cardFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.description, null);
                final EditText text = (EditText) dialogView.findViewById(R.id.etDesc);
                text.setText(prefManager.getFolderProject());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Your Folder Project!");
                builder.setView(dialogView);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        prefManager.setFolderProject(text.getText().toString());
                    }
                });
                builder.show();
            }
        });

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.setIsLoggedIn(false);
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
            }
        });
    }


}

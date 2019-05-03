package com.hamz.sistemsarpras;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hamz.sistemsarpras.api.ApiClient;
import com.hamz.sistemsarpras.api.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    PrefManager prefManager;
    private EditText etUser, etPass;
    private Button btnLogin;
    Context context;

    ApiInterface maApiInterface;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefManager = new PrefManager(this);
        if (prefManager.getIsLoggedIn() == true) {
            launchMain();
        }
        context = this;

        maApiInterface = new ApiClient(context).getClient().create(ApiInterface.class);
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");

        etUser = (EditText) findViewById(R.id.etUser);
        etPass = (EditText) findViewById(R.id.etPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    prefManager.setIsLoggedIn(true);
                    launchMain();
                }
            }
        });

    }

    private boolean validate() {
        if (etUser.getText().toString().trim().equals(""))
            etUser.requestFocus();
        else if (etPass.getText().toString().trim().equals(""))
            etPass.requestFocus();
        else {
            dialog.show();
            maApiInterface.login(etUser.getText().toString(), etPass.getText().toString()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getString("error").equals("false")) {
                                prefManager.setIsLoggedIn(true);
                                prefManager.setIdUser(jsonObject.getInt("id_user"));
                                prefManager.setNameUser(jsonObject.getString("name_user"));
                                prefManager.setLevelUser(jsonObject.getInt("level_user"));
                                launchMain();
                            } else {
                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        return false;
    }

    private void launchMain() {
        if (prefManager.getLevelUser() == 1)
            startActivity(new Intent(LoginActivity.this, MainActivityAdmin.class));
        else if (prefManager.getLevelUser() == 2)
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

}

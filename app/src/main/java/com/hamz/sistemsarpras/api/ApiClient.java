package com.hamz.sistemsarpras.api;

import android.content.Context;

import com.hamz.sistemsarpras.PrefManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hamz on 02/03/2018.
 */

public class ApiClient {

    PrefManager prefManager;

    public ApiClient(Context context) {
        prefManager = new PrefManager(context);
    }

    public  Retrofit retrofit = null ;
    public  Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://"+prefManager.getIpAddress()+"/"+prefManager.getFolderProject()+"/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}

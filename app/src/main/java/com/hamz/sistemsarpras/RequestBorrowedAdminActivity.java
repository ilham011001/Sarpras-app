package com.hamz.sistemsarpras;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.hamz.sistemsarpras.adapter.RequestBorrowAdapter;
import com.hamz.sistemsarpras.api.ApiClient;
import com.hamz.sistemsarpras.api.ApiInterface;
import com.hamz.sistemsarpras.model.GetAdmin;
import com.hamz.sistemsarpras.model.RequestBorrow;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestBorrowedAdminActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Context context;
    Toolbar toolbar;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<RequestBorrow> requestBorrows;
    RequestBorrowAdapter adapter;

    ApiInterface mApiInterface;

    public static RequestBorrowedAdminActivity requestBorrowedAdminActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_borrowed_admin);

        context = this;
        initComponent();
        mApiInterface = new ApiClient(context).getClient().create(ApiInterface.class);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeLayout);

        requestBorrowedAdminActivity = this;

        requestBorrows = new ArrayList<>();
        adapter = new RequestBorrowAdapter(context, requestBorrows);

        recyclerView = (RecyclerView)findViewById(R.id.rvMain);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                showData();
            }
        });


    }

    private void showData() {
        swipeRefreshLayout.setRefreshing(true);

        mApiInterface.getRequestBorrow(String.valueOf(new PrefManager(context).getIdUser()))
        .enqueue(new Callback<GetAdmin>() {
            @Override
            public void onResponse(Call<GetAdmin> call, Response<GetAdmin> response) {
                if (response.isSuccessful()) {
                    requestBorrows.clear();
                    requestBorrows = response.body().getListRequestBorrow();
                    adapter = new RequestBorrowAdapter(context, requestBorrows);
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                }else {
                    Toast.makeText(context, "Error while fetct Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetAdmin> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onRefresh() {
        showData();
    }
}

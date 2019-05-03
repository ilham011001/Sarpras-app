package com.hamz.sistemsarpras;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.hamz.sistemsarpras.adapter.ReportAdapterAdmin;
import com.hamz.sistemsarpras.api.ApiClient;
import com.hamz.sistemsarpras.api.ApiInterface;
import com.hamz.sistemsarpras.model.GetReport;
import com.hamz.sistemsarpras.model.Report;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivityAdmin extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Context context;
    Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ReportAdapterAdmin adapter;
    ArrayList<Report> reports;
    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_admin);

        context = this;
        initComponent();

        mApiInterface = new ApiClient(context).getClient().create(ApiInterface.class);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                fecthData();
            }
        });

        reports = new ArrayList<>();
        adapter = new ReportAdapterAdmin(context, reports);
        recyclerView = (RecyclerView) findViewById(R.id.rvMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    private void fecthData() {
        swipeRefreshLayout.setRefreshing(true);

        mApiInterface.getReportAll("2").enqueue(new Callback<GetReport>() {
            @Override
            public void onResponse(Call<GetReport> call, Response<GetReport> response) {
                if (response.isSuccessful()) {
                    reports = response.body().getListReport();
                    adapter = new ReportAdapterAdmin(context, reports);
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(context, "gagal", Toast.LENGTH_LONG).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<GetReport> call, Throwable t) {
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
        fecthData();
    }
}

package com.hamz.sistemsarpras;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.hamz.sistemsarpras.adapter.ReportAdapter;
import com.hamz.sistemsarpras.api.ApiClient;
import com.hamz.sistemsarpras.api.ApiInterface;
import com.hamz.sistemsarpras.model.GetReport;
import com.hamz.sistemsarpras.model.Report;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListReportActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Context context;

    PrefManager prefManager;

    ApiInterface mApiInterface;
    ProgressDialog dialog;

    SwipeRefreshLayout swipeRefreshLayout;
    ReportAdapter adapter;
    ArrayList<Report> reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_report);

        context = this;
        initComponent();
        mApiInterface = new ApiClient(context).getClient().create(ApiInterface.class);

        prefManager = new PrefManager(context);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //refreshRecyclerview();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperRefreshLayout);

        reports = new ArrayList<>();
        adapter = new ReportAdapter(context, reports);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                showData();
            }
        });
    }

    public void refreshRecyclerview() {
        dialog = ProgressDialog.show(context, null, "Loading...", true, false);
        mApiInterface.getReport(String.valueOf(prefManager.getIdUser())).enqueue(new Callback<GetReport>() {
            @Override
            public void onResponse(Call<GetReport> call, Response<GetReport> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    ArrayList<Report> reports = response.body().getListReport();
                    recyclerView.setAdapter(new ReportAdapter(context, reports));
                }else {
                    Toast.makeText(getApplicationContext(), "loading gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetReport> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onRefresh() {
        showData();
    }

    private void showData() {
        swipeRefreshLayout.setRefreshing(true);

        mApiInterface.getReport(String.valueOf(prefManager.getIdUser())).enqueue(new Callback<GetReport>() {
            @Override
            public void onResponse(Call<GetReport> call, Response<GetReport> response) {
                if (response.isSuccessful()) {
                    reports = response.body().getListReport();
                    adapter = new ReportAdapter(context, reports);
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(getApplicationContext(), "Error : response is false", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<GetReport> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}

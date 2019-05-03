package com.hamz.sistemsarpras;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.hamz.sistemsarpras.adapter.ProjectorUserAdapter;
import com.hamz.sistemsarpras.api.ApiClient;
import com.hamz.sistemsarpras.api.ApiInterface;
import com.hamz.sistemsarpras.model.GetAdmin;
import com.hamz.sistemsarpras.model.ProjectorUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProjectorActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static ListProjectorActivity listProjectorActivity;

    private Toolbar toolbar;
    Context context;
    ArrayList<ProjectorUser> projectors;
    ProjectorUserAdapter adapter;

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    ApiInterface mApiInterface;

    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_projector);

        context =this;
        listProjectorActivity = this;
        initCompoenent();
        mApiInterface = new ApiClient(context).getClient().create(ApiInterface.class);
        prefManager = new PrefManager(context);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);

        projectors = new ArrayList<>();
        adapter = new ProjectorUserAdapter(context, projectors);

        recyclerView = (RecyclerView) findViewById(R.id.rvMain);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

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

        mApiInterface.getProjectorUser("2").enqueue(new Callback<GetAdmin>() {
            @Override
            public void onResponse(Call<GetAdmin> call, Response<GetAdmin> response) {
                if (response.isSuccessful()){
                    projectors.clear();
                    projectors = response.body().getListProjectorUser();
                    adapter = new ProjectorUserAdapter(context, projectors);
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                }
                else
                    Toast.makeText(context, "gagal", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<GetAdmin> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initCompoenent() {
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

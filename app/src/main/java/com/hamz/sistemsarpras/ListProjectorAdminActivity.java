package com.hamz.sistemsarpras;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.hamz.sistemsarpras.adapter.ProjectorAdapter;
import com.hamz.sistemsarpras.api.ApiClient;
import com.hamz.sistemsarpras.api.ApiInterface;
import com.hamz.sistemsarpras.model.GetAdmin;
import com.hamz.sistemsarpras.model.Projector;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProjectorAdminActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    Context context;
    ArrayList<Projector> projectors;
    ProjectorAdapter adapter;

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    ApiInterface mApiInterface;

    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_projector_admin);

        context =this;
        initCompoenent();
        mApiInterface = new ApiClient(context).getClient().create(ApiInterface.class);
        prefManager = new PrefManager(context);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);

        projectors = new ArrayList<>();
        adapter = new ProjectorAdapter(context, projectors);

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

        mApiInterface.getProjector(prefManager.getIdUser().toString()).enqueue(new Callback<GetAdmin>() {
            @Override
            public void onResponse(Call<GetAdmin> call, Response<GetAdmin> response) {
                if (response.isSuccessful()) {
                    projectors.clear();
                    projectors = response.body().getListProjector();
                    adapter = new ProjectorAdapter(context, projectors);
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(context, "Error while fetch data", Toast.LENGTH_LONG).show();
                }
                swipeRefreshLayout.setRefreshing(false);
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

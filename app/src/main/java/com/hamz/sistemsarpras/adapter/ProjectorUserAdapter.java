package com.hamz.sistemsarpras.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hamz.sistemsarpras.ListProjectorActivity;
import com.hamz.sistemsarpras.PrefManager;
import com.hamz.sistemsarpras.R;
import com.hamz.sistemsarpras.api.ApiClient;
import com.hamz.sistemsarpras.api.ApiInterface;
import com.hamz.sistemsarpras.model.ProjectorUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hamz on 07/03/2018.
 */

public class ProjectorUserAdapter extends RecyclerView.Adapter<ProjectorUserAdapter.MyViewHolder> {

    Context context;
    ArrayList<ProjectorUser> projectors;
    PrefManager prefManager;
    ApiInterface mApiInterface;

    public ProjectorUserAdapter(Context context, ArrayList<ProjectorUser> projectors) {
        this.context = context;
        this.projectors = projectors;
        prefManager = new PrefManager(context);
        mApiInterface = new ApiClient(context).getClient().create(ApiInterface.class);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvKet, tvBorrow, tvTake;
        private ImageView thumbnail;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvKet  = (TextView) itemView.findViewById(R.id.tvCreated);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);

            tvBorrow = (TextView) itemView.findViewById(R.id.tvBorrowed);
            tvTake = (TextView) itemView.findViewById(R.id.tvTake);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.projector, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvName.setText(projectors.get(position).getName_projector());
        holder.tvKet.setText(projectors.get(position).getCreate());
        Glide.with(context)
                .load("http://"+ prefManager.getIpAddress() +"/"+prefManager.getFolderProject()+"/"+ projectors.get(position).getImage_projector())
                .placeholder(R.drawable.projector40blue)
                .into(holder.thumbnail);

        holder.tvBorrow.setText(projectors.get(position).getName_borrow());
        if (!projectors.get(position).getName_borrow().equals("")) {
            holder.tvTake.setEnabled(false);
        }
        holder.tvTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Informant!");
                builder.setMessage("You will take this Projector");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mApiInterface.postRequest(String.valueOf(projectors.get(position).getId_projector()),
                                String.valueOf(prefManager.getIdUser()))
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()){
                                            try {
                                                JSONObject jsonObject = new JSONObject(response.body().string());
                                                if (!jsonObject.getString("error").equals("true")) {
                                                    Toast.makeText(context, "Oke", Toast.LENGTH_LONG).show();
                                                    ListProjectorActivity.listProjectorActivity.onRefresh();
                                                }else{
                                                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                        else {
                                            Toast.makeText(context, "gagal", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectors.size();
    }


}

package com.hamz.sistemsarpras.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hamz.sistemsarpras.PrefManager;
import com.hamz.sistemsarpras.R;
import com.hamz.sistemsarpras.RequestBorrowedAdminActivity;
import com.hamz.sistemsarpras.api.ApiClient;
import com.hamz.sistemsarpras.api.ApiInterface;
import com.hamz.sistemsarpras.model.RequestBorrow;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hamz on 08/03/2018.
 */

public class RequestBorrowAdapter extends RecyclerView.Adapter<RequestBorrowAdapter.MyViewHolder> {

    Context context;
    ArrayList<RequestBorrow> requestBorrows;
    PrefManager prefManager;
    ApiInterface mApiInterface;
    ProgressDialog dialog;

    public RequestBorrowAdapter(Context context, ArrayList<RequestBorrow> requestBorrows) {
        this.context = context;
        this.requestBorrows = requestBorrows;
        prefManager = new PrefManager(context);
        mApiInterface = new ApiClient(context).getClient().create(ApiInterface.class);
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProjector, imgUser;
        TextView tvUser, tvDesc;
        Button btnApprove, btnDecline;
        public MyViewHolder(View itemView) {
            super(itemView);
            imgProjector = (ImageView) itemView.findViewById(R.id.thumbnail);
            imgUser = (ImageView) itemView.findViewById(R.id.thumbnailUser);
            tvUser = (TextView) itemView.findViewById(R.id.tvUser);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            btnApprove = (Button) itemView.findViewById(R.id.btnApprove);
            btnDecline = (Button) itemView.findViewById(R.id.btnDecline);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_borrowed, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvUser.setText(requestBorrows.get(position).getName_user());
        holder.tvDesc.setText(requestBorrows.get(position).getDescription_borrow());

        Glide.with(context)
                .load("http://"+ prefManager.getIpAddress() +"/"+prefManager.getFolderProject()+"/"+requestBorrows.get(position).getImage_projector())
                .placeholder(R.drawable.user100)
                .into(holder.imgProjector);

        Glide.with(context)
                .load("http://"+ prefManager.getIpAddress() +"/"+prefManager.getFolderProject()+"/"+requestBorrows.get(position).getImage_user())
                .placeholder(R.drawable.user100)
                .into(holder.imgUser);

        holder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Attention");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("Accept!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        mApiInterface.postApprove(String.valueOf(requestBorrows.get(position).getId_request()))
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response.body().string());
                                                if (jsonObject.getString("error").equals("true")) {
                                                    Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                                                    RequestBorrowedAdminActivity.requestBorrowedAdminActivity.onRefresh();
                                                }else {
                                                    Toast.makeText(context, "Upadte is failed", Toast.LENGTH_LONG).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }else {
                                            Toast.makeText(context, "Response is failed", Toast.LENGTH_LONG).show();
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
        return requestBorrows.size();
    }

}

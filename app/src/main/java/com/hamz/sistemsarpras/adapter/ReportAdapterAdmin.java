package com.hamz.sistemsarpras.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamz.sistemsarpras.PrefManager;
import com.hamz.sistemsarpras.R;
import com.hamz.sistemsarpras.model.Report;

import java.util.ArrayList;

/**
 * Created by Hamz on 04/03/2018.
 */

public class ReportAdapterAdmin extends RecyclerView.Adapter<ReportAdapterAdmin.MyViewHolder> {

    Context context;
    ArrayList<Report> reports;
    PrefManager prefManager;

    public ReportAdapterAdmin(Context context, ArrayList<Report> reports) {
        this.context = context;
        this.reports = reports;
        prefManager = new PrefManager(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_admin, parent, false);
        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvDesc, tvUser;
        ImageView thumbnail;
        public MyViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            tvDesc = (TextView) v.findViewById(R.id.tvDesc);
            tvUser = (TextView) v.findViewById(R.id.tvUser);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
        }
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvTitle.setText(reports.get(position).getTitle());
        holder.tvDesc.setText(reports.get(position).getDescription());
        holder.tvUser.setText(reports.get(position).getName());
        Glide.with(context)
                .load("http://"+ prefManager.getIpAddress() +"/"+prefManager.getFolderProject()+"/"+reports.get(position).getImage())
                .placeholder(R.drawable.sent24white)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

}

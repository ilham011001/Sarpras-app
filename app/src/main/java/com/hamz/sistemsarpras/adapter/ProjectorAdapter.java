package com.hamz.sistemsarpras.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamz.sistemsarpras.PrefManager;
import com.hamz.sistemsarpras.R;
import com.hamz.sistemsarpras.model.Projector;

import java.util.ArrayList;

/**
 * Created by Hamz on 07/03/2018.
 */

public class ProjectorAdapter extends RecyclerView.Adapter<ProjectorAdapter.MyViewHolder> {

    Context context;
    ArrayList<Projector> projectors;
    PrefManager prefManager;

    public ProjectorAdapter(Context context, ArrayList<Projector> projectors) {
        this.context = context;
        this.projectors = projectors;
        prefManager = new PrefManager(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvKet;
        private ImageView thumbnail;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvKet  = (TextView) itemView.findViewById(R.id.tvCreated);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.projector_admin, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvName.setText(projectors.get(position).getName_projector());
        holder.tvKet.setText(projectors.get(position).getCreate());
        Glide.with(context)
                .load("http://"+ prefManager.getIpAddress() +"/"+prefManager.getFolderProject()+"/"+ projectors.get(position).getImage_projector())
                .placeholder(R.drawable.projector40blue)
                .into(holder.thumbnail);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context, DetailProjectorAdminActivity.class));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return projectors.size();
    }


}

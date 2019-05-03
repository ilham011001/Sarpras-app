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
import com.hamz.sistemsarpras.model.Borrowed;

import java.util.ArrayList;

/**
 * Created by Hamz on 08/03/2018.
 */

public class BorrowedAdapter extends RecyclerView.Adapter<BorrowedAdapter.MyViewHolder> {

    Context context;
    ArrayList<Borrowed> borroweds;
    PrefManager prefManager;

    public BorrowedAdapter(Context context, ArrayList<Borrowed> borroweds) {
        this.context = context;
        this.borroweds = borroweds;
        prefManager = new PrefManager(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProjector, imgUser;
        TextView tvName, tvDate;
        public MyViewHolder(View itemView) {
            super(itemView);
            imgProjector = (ImageView) itemView.findViewById(R.id.thumbnail);
            imgUser = (ImageView) itemView.findViewById(R.id.thumbnailUser);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.borrowed, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvName.setText(borroweds.get(position).getName_user());
        holder.tvDate.setText(borroweds.get(position).getDate_borrow());

        Glide.with(context)
                .load("http://"+ prefManager.getIpAddress() +"/"+prefManager.getFolderProject()+"/"+borroweds.get(position).getImage_projector())
                .placeholder(R.drawable.user100)
                .into(holder.imgProjector);

        Glide.with(context)
                .load("http://"+ prefManager.getIpAddress() +"/"+prefManager.getFolderProject()+"/"+borroweds.get(position).getImage_user())
                .placeholder(R.drawable.user100)
                .into(holder.imgUser);
    }

    @Override
    public int getItemCount() {
        return borroweds.size();
    }

}

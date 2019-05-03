package com.hamz.sistemsarpras.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hamz.sistemsarpras.PrefManager;
import com.hamz.sistemsarpras.R;
import com.hamz.sistemsarpras.model.Report;

import java.util.ArrayList;

/**
 * Created by Hamz on 04/11/2017.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Report> reports;
    PrefManager prefManager;

    public ReportAdapter(Context context, ArrayList<Report> reports){
        this.context = context;
        this.reports = reports;
        prefManager = new PrefManager(context);
    }

    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.report, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle, tvDesc, textViewOptions;
        private ImageView ivResult;
        private CardView cardView;
        public ViewHolder(View v){
            super(v);
            tvTitle = (TextView)v.findViewById(R.id.tvTitle);
            tvDesc   = (TextView)v.findViewById(R.id.tvDescription);
            cardView = (CardView)v.findViewById(R.id.cardView);
            ivResult = (ImageView)v.findViewById(R.id.ivResult);
            textViewOptions = (TextView)v.findViewById(R.id.textViewOptions);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvTitle.setText(reports.get(position).getTitle().toString());
        holder.tvDesc.setText(reports.get(position).getDescription().toString());
        Glide.with(context)
                .load("http://"+ prefManager.getIpAddress() +"/"+prefManager.getFolderProject()+"/"+reports.get(position).getImage())
                .placeholder(R.drawable.sent24white)
                .into(holder.ivResult);
        holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.textViewOptions);
                popup.inflate(R.menu.menu_popup);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuShow:
                                Toast.makeText(context, "show", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menuDelete:
                                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    private void showDialog() {
        final CharSequence[] items = {"Take Photo", "Choose from Library"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                switch (position) {
                    case 0:
                        Toast.makeText(context, "show", Toast.LENGTH_SHORT);
                        break;
                    case 1:
                        Toast.makeText(context, "sooo", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }).show();

    }

    @Override
    public int getItemCount() {
        return reports.size();
    }




}

package com.hamz.sistemsarpras.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamz.sistemsarpras.PrefManager;
import com.hamz.sistemsarpras.R;
import com.hamz.sistemsarpras.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hamz on 04/03/2018.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<Contact> contactList;
    private List<Contact> contactListFiltered;
    private ContactAdapterListener listener;
    PrefManager prefManager;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPhone;
        public ImageView thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public ContactsAdapter(Context context, List<Contact> contactList, ContactAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        prefManager = new PrefManager(context);
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contact contact = contactListFiltered.get(position);
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhone());

        Glide.with(context)
                .load("http://"+ prefManager.getIpAddress() +"/"+prefManager.getFolderProject()+"/"+contact.getImage())
                .placeholder(R.drawable.user100)
                .into(holder.thumbnail);
    }


    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<Contact> filteredList = new ArrayList<>();
                    for (Contact row: contactList) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contactListFiltered = (ArrayList<Contact>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactAdapterListener {
        void onContactSelected(Contact contact);
    }
}

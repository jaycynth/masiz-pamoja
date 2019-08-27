package com.julie.masizpamoja.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.julie.masizpamoja.R;
import com.julie.masizpamoja.models.ContactList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.julie.masizpamoja.api.ApiEndpoints.BLOG_URL;

public class AllContactListAdapter extends RecyclerView.Adapter<AllContactListAdapter.AllContactListViewHolder> {
    private List<ContactList> allContactList;
    private Context context;

    public AllContactListAdapter(List<ContactList> allContactList, Context context) {
        this.allContactList = allContactList;
        this.context = context;
    }

    @NonNull
    @Override
    public AllContactListAdapter.AllContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.all_contactlists_layout, parent, false);
        return new AllContactListAdapter.AllContactListViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AllContactListAdapter.AllContactListViewHolder holder, int position) {

        ContactList contactList = allContactList.get(position);
        holder.contactName.setText(contactList.getName());
        holder.contactEmail.setText(contactList.getEmail());
        holder.contactPhone.setText(contactList.getPhone());



    }

    @Override
    public int getItemCount() {
        return allContactList.size();
    }

    public class AllContactListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.contact_name)
        TextView contactName;

        @BindView(R.id.contact_email)
        TextView contactEmail;

        @BindView(R.id.contact_phone)
        TextView contactPhone;



        public AllContactListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}

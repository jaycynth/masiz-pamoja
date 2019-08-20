package com.julie.masizpamoja.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.julie.masizpamoja.R;

public class AllBlogsAdapter extends RecyclerView.Adapter<AllBlogsAdapter.AllBlogsViewHolder> {


    @NonNull
    @Override
    public AllBlogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.all_blogs_layout, parent, false);
        return new AllBlogsViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AllBlogsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AllBlogsViewHolder extends RecyclerView.ViewHolder {


        public AllBlogsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

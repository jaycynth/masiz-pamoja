package com.julie.masizpamoja.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.julie.masizpamoja.R;
import com.julie.masizpamoja.models.NeedHelp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeedHelpAdapter extends RecyclerView.Adapter<NeedHelpAdapter.NeedHelpVieHolder> {

    private List<NeedHelp> needHelpList;
    private Context context;


    public NeedHelpAdapter(List<NeedHelp> needHelpList, Context context) {
        this.needHelpList = needHelpList;
        this.context = context;
    }

    @NonNull
    @Override
    public NeedHelpAdapter.NeedHelpVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_need_help, parent, false);
        return new NeedHelpAdapter.NeedHelpVieHolder(listItem);    }

    @Override
    public void onBindViewHolder(@NonNull NeedHelpAdapter.NeedHelpVieHolder holder, int position) {
        NeedHelp needHelp = needHelpList.get(position);
        holder.name.setText(needHelp.getName());

    }

    @Override
    public int getItemCount() {
        return needHelpList.size();
    }

    public class NeedHelpVieHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        public NeedHelpVieHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

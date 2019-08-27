package com.julie.masizpamoja.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.julie.masizpamoja.R;
import com.julie.masizpamoja.models.HelpDesk;
import com.julie.masizpamoja.models.NeedHelp;
import com.julie.masizpamoja.views.activities.HelpDeskDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeedHelpAdapter extends RecyclerView.Adapter<NeedHelpAdapter.NeedHelpVieHolder> {

    private List<HelpDesk> needHelpList;
    private Context context;


    public NeedHelpAdapter(List<HelpDesk> needHelpList, Context context) {
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
        HelpDesk needHelp = needHelpList.get(position);
        holder.name.setText(needHelp.getTitle());

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

            itemView.setOnClickListener(v->{
                goToView(getAdapterPosition());
            });
        }
    }

    private void goToView(int adapterPosition) {


        HelpDesk helpDesk = needHelpList.get(adapterPosition);
        Intent needHelp = new Intent(context, HelpDeskDetails.class);
        needHelp.putExtra("helpDeskTitle", helpDesk.getTitle());
        needHelp.putExtra("helpDeskDetails", helpDesk.getInfo());
        context.startActivity(needHelp);


    }
}

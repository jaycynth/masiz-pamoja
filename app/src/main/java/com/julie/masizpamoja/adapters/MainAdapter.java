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

import com.julie.masizpamoja.R;
import com.julie.masizpamoja.models.MainAction;
import com.julie.masizpamoja.views.activities.Blogs;
import com.julie.masizpamoja.views.activities.NeedHelp;
import com.julie.masizpamoja.views.activities.UpcomingEvents;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private List<MainAction> actionList = new ArrayList<>();
    private Context context;

    public MainAdapter(List<MainAction> actionList, Context context) {
        this.actionList = actionList;
        this.context = context;
    }


    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_main_actions, parent, false);
        return new MainViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        MainAction mainAction = actionList.get(position);
        holder.actionName.setText(mainAction.getName());


    }

    @Override
    public int getItemCount() {
        return actionList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.action_image)
        ImageView actionImage;

        @BindView(R.id.action_name)
        TextView actionName;


        MainViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                goToView(getLayoutPosition());


            });
        }
    }

    private void goToView(int adapterPosition) {

        MainAction mainAction = actionList.get(adapterPosition);
        if (mainAction.getName().equalsIgnoreCase("Blogs")) {
            Intent restaurantDetailIntent = new Intent(context, Blogs.class);
            context.startActivity(restaurantDetailIntent);
        }else if(mainAction.getName().equalsIgnoreCase("Upcoming Events")){
            Intent restaurantDetailIntent = new Intent(context, UpcomingEvents.class);
            context.startActivity(restaurantDetailIntent);
        }else if(mainAction.getName().equalsIgnoreCase("Need Help")){
            Intent restaurantDetailIntent = new Intent(context, NeedHelp.class);
            context.startActivity(restaurantDetailIntent);
        }


    }
}

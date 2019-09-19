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
import com.julie.masizpamoja.models.MainAction;
import com.julie.masizpamoja.views.activities.Blogs;
import com.julie.masizpamoja.views.activities.ChatRoomActivity;
import com.julie.masizpamoja.views.activities.ContactsActivity;
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

        Glide.with(context).load(mainAction.getImage()).into(holder.actionImage);


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
        if (mainAction.getName().equalsIgnoreCase("Blog")) {
            Intent mainActionDetail = new Intent(context, Blogs.class);
            context.startActivity(mainActionDetail);
        } else if (mainAction.getName().equalsIgnoreCase("Upcoming Events")) {
            Intent mainActionDetail = new Intent(context, UpcomingEvents.class);
            context.startActivity(mainActionDetail);
        }
        else if (mainAction.getName().equalsIgnoreCase("Need Help")) {
//            DialogFragment dialogFragment = NeedHelpFragment.newInstance();
//            FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
//            dialogFragment.show(manager, "tag");
            Intent mainActionDetail = new Intent(context, ContactsActivity.class);
            context.startActivity(mainActionDetail);

        }else if (mainAction.getName().equalsIgnoreCase("More Info")) {
            Intent mainActionDetail = new Intent(context, NeedHelp.class);
            context.startActivity(mainActionDetail);
        } else if (mainAction.getName().equalsIgnoreCase("Chat Room")) {
            Intent mainActionDetail = new Intent(context, ChatRoomActivity.class);
            context.startActivity(mainActionDetail);
        }
    }


}

package com.julie.masizpamoja.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.julie.masizpamoja.R;
import com.julie.masizpamoja.models.Event;
import com.julie.masizpamoja.views.activities.EventDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.julie.masizpamoja.api.ApiEndpoints.EVENT_URL;

;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private List<Event> eventList;
    private Context context;


    public EventsAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.all_events_layout, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.eventDate.setText(event.getDate());
        holder.eventTitle.setText(event.getName());
        Glide.with(context).load(EVENT_URL + event.getCover()).into(holder.eventImage);

        String text =  event.getDescription();
        if (text.length()>100) {
            text=text.substring(0,100)+"...";
            holder.DetailTv.setText(Html.fromHtml(text+"<font color='red'> <u>View More</u></font>"));

        }else{
            holder.DetailTv.setText(event.getDescription());

        }

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.event_image)
        ImageView eventImage;

        @BindView(R.id.event_title)
        TextView eventTitle;

        @BindView(R.id.tvReviewDescription)
        TextView DetailTv;


        @BindView(R.id.event_date)
        TextView eventDate;


        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                goToView(getAdapterPosition());
            });


        }
    }

    private void goToView(int adapterPosition) {


        Event event = eventList.get(adapterPosition);

        Intent blogDetails = new Intent(context, EventDetails.class);
       // blogDetails.putExtra("eventsImage", event.getImage());
        blogDetails.putExtra("eventsTitle", event.getName());
        blogDetails.putExtra("eventsDate", event.getDate());
        blogDetails.putExtra("eventsDescription", event.getDescription());
        blogDetails.putExtra("eventsLongitude", event.getLongitude());
        blogDetails.putExtra("eventsLatitude", event.getLatitude());
        blogDetails.putExtra("eventsImage",event.getCover());

        context.startActivity(blogDetails);

    }
}

package com.julie.masizpamoja.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.julie.masizpamoja.R;
import com.julie.masizpamoja.adapters.EventsAdapter;
import com.julie.masizpamoja.adapters.RandomBlogAdapter;
import com.julie.masizpamoja.models.AllEvents;
import com.julie.masizpamoja.models.Event;
import com.julie.masizpamoja.utils.EventsData;
import com.julie.masizpamoja.utils.SharedPreferencesManager;
import com.julie.masizpamoja.viewmodels.EventsViewModel;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpcomingEvents extends AppCompatActivity {

    //private static RecyclerView.LayoutManager layoutManager;
    final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);


    private List<Event> eventList = new ArrayList<>();

    EventsAdapter eventsAdapter;

    @BindView(R.id.eventsRv)
    RecyclerView eventsRv;

    EventsViewModel eventsViewModel;
    String accessToken;

    @BindView(R.id.holder_layout)
    LinearLayout holderLayout;

    @BindView(R.id.spin_kit)
    ProgressBar circularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upcoming Events");

        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);

        accessToken = SharedPreferencesManager.getInstance(this).getToken();

        startProgressBar();
        eventsViewModel.allEvents("Bearer "+ accessToken);


        eventsViewModel.getAllEventsResponse().observe(this, allEventsState -> {
            if (allEventsState.getAllEvents() != null) {
                handleAllEvents(allEventsState.getAllEvents());
            }

            if (allEventsState.getMessage() != null) {
                handleErrorMessage(allEventsState.getMessage());
            }
            if (allEventsState.getErrorThrowable() != null) {
                handleErrorThrowable(allEventsState.getErrorThrowable());
            }
        });



    }

    private void startProgressBar() {
        circularProgressBar.setVisibility(View.VISIBLE);
        holderLayout.setAlpha(0.0f);
    }

    private void stopProgressBar() {
        circularProgressBar.setVisibility(View.GONE);
        holderLayout.setAlpha(1);
    }


    private void handleAllEvents(AllEvents allEvents) {
        stopProgressBar();
        boolean status = allEvents.getStatus();
        if (status) {

            eventList = allEvents.getEvents();
            if (!eventList.isEmpty()) {
                initView(eventList);
            }
        }

    }

    private void handleErrorMessage(String message) {
        stopProgressBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    private void handleErrorThrowable(Throwable errorThrowable) {
        stopProgressBar();
        if (errorThrowable instanceof IOException) {
            Toast.makeText(this, "You are currently Offline", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("UpcomingEvents", "conversion error " + errorThrowable.getMessage());
        }

    }

    private void initView(List<Event> eventList) {
        eventsAdapter = new EventsAdapter(eventList, this);
        eventsRv.setLayoutManager(layoutManager);
        eventsRv.setHasFixedSize(true);
        eventsRv.setAdapter(eventsAdapter);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        eventsRv.addOnScrollListener(new CenterScrollListener());

        eventsRv.setNestedScrollingEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }

}

package com.julie.masizpamoja.views.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.appbar.AppBarLayout;
import com.julie.masizpamoja.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.julie.masizpamoja.api.ApiEndpoints.EVENT_URL;


public class EventDetails extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.cover_image)
    ImageView coverImage;

    @BindView(R.id.event_title)
    TextView mEventTitle;

    @BindView(R.id.event_tickets)
    TextView eventTickets;

    @BindView(R.id.event_date)
    TextView mEventDate;

    @BindView(R.id.details_of_event)
    TextView detailsOfEvent;

    private GoogleMap mMap;

    String eventImage, eventTitle, eventDate, eventDescription, lat, lon;

    @BindView(R.id.scrollView)
    ScrollView nestedScrollView;

    @BindView(R.id.transparent_image)
    ImageView transparentImage;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handleAppBarArrowColor();


        ButterKnife.bind(this);


        Intent details = getIntent();
        eventDate = details.getStringExtra("eventsDate");
        eventImage = details.getStringExtra("eventsImage");
        eventTitle = details.getStringExtra("eventsTitle");
        eventDescription = details.getStringExtra("eventsDescription");
        lat = details.getStringExtra("eventsLatitude");
        lon = details.getStringExtra("eventsLongitude");


        Glide.with(EventDetails.this).load(EVENT_URL + eventImage).into(coverImage);
        mEventDate.setText(eventDate);
        mEventTitle.setText(eventTitle);
        eventTickets.setText("Sold Out");
        detailsOfEvent.setText(eventDescription);
        getSupportActionBar().setTitle(eventTitle);



        //loading map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*
         *Map scrolling fix
         * the idea is to prevent the scroll view from scrolling any time we touch and hold
         * the transparent image like in the case of zooming
         *
         */
        transparentImage.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    nestedScrollView.requestDisallowInterceptTouchEvent(true);
                    return false;
                case MotionEvent.ACTION_UP:
                    nestedScrollView.requestDisallowInterceptTouchEvent(false);
                    return true;
                default:
                    return true;
            }
        });

    }

    private void handleAppBarArrowColor() {
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, offset) -> {


            Drawable upArrow = ResourcesCompat.getDrawable(EventDetails.this.getResources(), R.drawable.ic_arrow_back_black_24dp, null);
            if (offset < -200) {
                upArrow.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
                EventDetails.this.getSupportActionBar().setHomeAsUpIndicator(upArrow);

            } else {

                upArrow.setColorFilter(Color.parseColor("#953B9D"), PorterDuff.Mode.SRC_ATOP);
                EventDetails.this.getSupportActionBar().setHomeAsUpIndicator(upArrow);
                EventDetails.this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMap.clear();
    }
}

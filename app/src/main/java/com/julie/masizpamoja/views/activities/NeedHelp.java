package com.julie.masizpamoja.views.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.julie.masizpamoja.R;
import com.julie.masizpamoja.adapters.NeedHelpAdapter;
import com.julie.masizpamoja.models.EntryHelpDesk;
import com.julie.masizpamoja.models.HelpDesk;
import com.julie.masizpamoja.utils.SharedPreferencesManager;
import com.julie.masizpamoja.viewmodels.HelpDeskViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeedHelp extends AppCompatActivity {

    private static RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.need_help_recyclerView)
    RecyclerView need_help_recyclerview;

    NeedHelpAdapter needHelpAdapter;

    HelpDeskViewModel helpDeskViewModel;

    private List<HelpDesk> needHelpList = new ArrayList<>();

    String accessToken;

    @BindView(R.id.holder_layout)
    LinearLayout holderLayout;

    @BindView(R.id.spin_kit)
    ProgressBar circularProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_help);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("More Info");

        helpDeskViewModel = ViewModelProviders.of(this).get(HelpDeskViewModel.class);

        accessToken = SharedPreferencesManager.getInstance(this).getToken();

        startProgressBar();
        helpDeskViewModel.helpDesk("Bearer "+ accessToken);

        helpDeskViewModel.getHelpDeskResponse().observe(this, helpDeskState -> {
            if(helpDeskState.getHelpDesk() != null){
                handleHelpDesk(helpDeskState.getHelpDesk());
            }

            if(helpDeskState.getErrorThrowable() != null){
                handleErrorThrowable(helpDeskState.getErrorThrowable());
            }

            if(helpDeskState.getMessage() != null){
                handleError(helpDeskState.getMessage());
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


    private void handleHelpDesk(EntryHelpDesk helpDesk) {
        stopProgressBar();
        boolean status = helpDesk.getStatus();
        if(status){
            needHelpList = helpDesk.getHelpDesk();

            if(!needHelpList.isEmpty()){
                initView(needHelpList);
            }
        }

    }

    private void handleErrorThrowable(Throwable errorThrowable) {
        stopProgressBar();
        if (errorThrowable instanceof IOException) {
            Toast.makeText(this, "You are currently Offline", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("UpcomingEvents", "conversion error " + errorThrowable.getMessage());
        }

    }

    private void handleError(String message) {
        stopProgressBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    private void initView(List<HelpDesk> needHelpList) {
        needHelpAdapter = new NeedHelpAdapter(needHelpList, this);
        layoutManager = new LinearLayoutManager(this);
        need_help_recyclerview.setLayoutManager(layoutManager);
        need_help_recyclerview.setItemAnimator(new DefaultItemAnimator());
        need_help_recyclerview.setAdapter(needHelpAdapter);
        need_help_recyclerview.setNestedScrollingEnabled(false);
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

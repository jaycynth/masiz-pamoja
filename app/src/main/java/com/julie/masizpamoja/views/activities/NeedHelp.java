package com.julie.masizpamoja.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.julie.masizpamoja.R;
import com.julie.masizpamoja.adapters.NeedHelpAdapter;
import com.julie.masizpamoja.adapters.RandomBlogAdapter;
import com.julie.masizpamoja.utils.NeedHelpData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeedHelp extends AppCompatActivity {

    private static RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.need_help_recyclerView)
    RecyclerView need_help_recyclerview;

    NeedHelpAdapter needHelpAdapter;

    private List<com.julie.masizpamoja.models.NeedHelp> needHelpList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_help);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Need Help");

        needHelpList = NeedHelpData.needHelpDatas();
        initView(needHelpList);


    }

    private void initView(List<com.julie.masizpamoja.models.NeedHelp> needHelpList) {
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

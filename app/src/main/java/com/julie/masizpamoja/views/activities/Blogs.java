package com.julie.masizpamoja.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.julie.masizpamoja.R;
import com.julie.masizpamoja.adapters.AllBlogsAdapter;
import com.julie.masizpamoja.models.AllBlogs;
import com.julie.masizpamoja.models.Blog;
import com.julie.masizpamoja.utils.SharedPreferencesManager;
import com.julie.masizpamoja.viewmodels.BlogsViewModel;


import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Blogs extends AppCompatActivity {

    private static RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.allBlogsRv)
    RecyclerView allBlogsRv;
    
    AllBlogsAdapter allBlogsAdapter;

    BlogsViewModel blogsViewModel;
    String accessToken;

    private List<Blog> allBlogsList;

    @BindView(R.id.holder_layout)
    RelativeLayout holderLayout;

    @BindView(R.id.spin_kit)
    ProgressBar circularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Blogs");

        accessToken = SharedPreferencesManager.getInstance(this).getToken();

        blogsViewModel = ViewModelProviders.of(this).get(BlogsViewModel.class);

        startProgressBar();
        blogsViewModel.allBlogs("Bearer " + accessToken);

        blogsViewModel.getAllBlogsResponse().observe(this, allBlogsState -> {

            if (allBlogsState.getAllBlogs() != null) {

                handleAllBlogs(allBlogsState.getAllBlogs());
            }

            if (allBlogsState.getErrorThrowable() != null) {
                handleErrorThrowable(allBlogsState.getErrorThrowable());
            }

            if (allBlogsState.getMessage() != null) {
                handleError(allBlogsState.getMessage());
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

    private void handleError(String message) {
        stopProgressBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



    private void handleErrorThrowable(Throwable errorThrowable) {
        stopProgressBar();
        if (errorThrowable instanceof IOException) {
            Toast.makeText(this, "network failure", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }

    private void handleAllBlogs(AllBlogs allAllBlogs) {
       stopProgressBar();
        boolean status= allAllBlogs.getStatus();
        if(status){
            allBlogsList = allAllBlogs.getBlogs();
            if(!allBlogsList.isEmpty()){
                initView(allBlogsList);
            }
        }

    }

    private void initView(List<Blog> allBlogsList) {
        allBlogsAdapter = new AllBlogsAdapter(allBlogsList, this);
        allBlogsRv.setAdapter(allBlogsAdapter);
        layoutManager = new LinearLayoutManager(this);
        allBlogsRv.setLayoutManager(layoutManager);
        allBlogsRv.setNestedScrollingEnabled(false);
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

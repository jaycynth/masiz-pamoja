package com.julie.masizpamoja.views.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.R;
import com.julie.masizpamoja.adapters.MainAdapter;
import com.julie.masizpamoja.adapters.RandomBlogAdapter;
import com.julie.masizpamoja.models.Blog;
import com.julie.masizpamoja.models.LatestBlogs;
import com.julie.masizpamoja.models.MainAction;
import com.julie.masizpamoja.utils.MainActionData;
import com.julie.masizpamoja.utils.SharedPreferencesManager;
import com.julie.masizpamoja.viewmodels.HomeViewModel;
import com.julie.masizpamoja.views.activities.AboutUs;
import com.julie.masizpamoja.views.activities.OurActivities;


import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Home extends Fragment {

    private static RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.random_blogs_recyclerView)
    RecyclerView random_blogs_rcyclerview;


    @BindView(R.id.main_actions_recyclerView)
    RecyclerView main_action_recyclerview;


    @BindView(R.id.who_we_are)
    MaterialCardView whoWeAre;

    @BindView(R.id.what_do_we_do)
    MaterialCardView whatDoWeDo;

    private HomeViewModel homeViewModel;

    private String accessToken;

    @BindView(R.id.fab)
    RelativeLayout fab;
    @BindView(R.id.fab_text)
    TextView fabText;

    @BindView(R.id.fab1)
    FloatingActionButton mainFab;

    @BindView(R.id.container_layout)
    LinearLayout containerLayout;

    @BindView(R.id.holder_layout)
    CoordinatorLayout holderLayout;

    @BindView(R.id.spin_kit)
    ProgressBar circularProgressBar;


    private boolean isFABOpen = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        if (getActivity() != null) {
            getActivity().setTitle("Home");
        }

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        accessToken = SharedPreferencesManager.getInstance(getActivity()).getToken();

        startProgressBar();
        homeViewModel.latestBlogs("Bearer " + accessToken);




        homeViewModel.getLatestBlogsResponse().observe(this, latestBlogsState -> {
            if (latestBlogsState.getAllLatestBlogs() != null) {
                handleLatestBlogs(latestBlogsState.getAllLatestBlogs());
            }

            if (latestBlogsState.getErrorThrowable() != null) {

                handleError(latestBlogsState.getErrorThrowable());
            }

            if (latestBlogsState.getMessage() != null) {
                handleNetworkResponse(latestBlogsState.getMessage());
            }
        });

        List<MainAction> actionList = MainActionData.getMainAction();
        initViewMainActions(actionList);


        whoWeAre.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AboutUs.class));
        });

        whatDoWeDo.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), OurActivities.class));
        });

        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {

                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        fab.setOnClickListener(v -> {
            shareLink();

        });

        return view;
    }

    private void showFABMenu() {
        isFABOpen = true;
        fabText.setVisibility(View.VISIBLE);
        containerLayout.setAlpha(0.4f);
        fab.animate().translationY(-getResources().getDimension(R.dimen.standard_55));

    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabText.setVisibility(View.GONE);
        containerLayout.setAlpha(1);
        fab.animate().translationY(0);

    }

    private void shareLink() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    private void startProgressBar() {
        circularProgressBar.setVisibility(View.VISIBLE);
        holderLayout.setAlpha(0.0f);
    }

    private void stopProgressBar() {
        circularProgressBar.setVisibility(View.GONE);
        holderLayout.setAlpha(1);
    }

    private void handleNetworkResponse(String message) {
        stopProgressBar();

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void handleError(Throwable errorThrowable) {
        stopProgressBar();

        if (errorThrowable instanceof IOException) {
            Toast.makeText(getActivity(), "network failure", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleLatestBlogs(LatestBlogs allLatestBlogs) {
        stopProgressBar();
        boolean status = allLatestBlogs.getStatus();
        if (status) {
            List<Blog> randomBlogsList = allLatestBlogs.getBlogs();
            if (!randomBlogsList.isEmpty()) {
                initViewRandomBlogs(randomBlogsList);
            }
        }
    }

    private void initViewRandomBlogs(List<Blog> randomBlogsList) {
        RandomBlogAdapter randomBlogAdapter = new RandomBlogAdapter(randomBlogsList, getActivity());
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        random_blogs_rcyclerview.setLayoutManager(layoutManager);
        random_blogs_rcyclerview.setAdapter(randomBlogAdapter);
        random_blogs_rcyclerview.setNestedScrollingEnabled(false);
    }

    private void initViewMainActions(List<MainAction> actionList) {
        MainAdapter mainAdapter = new MainAdapter(actionList, getActivity());
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        main_action_recyclerview.setLayoutManager(layoutManager);
        main_action_recyclerview.setAdapter(mainAdapter);
        main_action_recyclerview.setNestedScrollingEnabled(false);
    }

}

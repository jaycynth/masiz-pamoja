package com.julie.masizpamoja.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.julie.masizpamoja.api.ApiEndpoints.BLOG_URL;


public class BlogDetails extends AppCompatActivity {

    @BindView(R.id.cover_image)
    ImageView coverImage;

    @BindView(R.id.blog_title)
    TextView blogTitle;

    @BindView(R.id.blog_body)
    TextView blogBody;

    @BindView(R.id.writers_name)
            TextView writersName;

    String ci, bt, bb,wn;

    boolean isMenuEnabled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        handleAppBarArrowColor();



        ButterKnife.bind(this);


        Intent blogDetails = getIntent();
        ci = blogDetails.getStringExtra("blogsImage");
        bt = blogDetails.getStringExtra("blogsTitle");
        bb = blogDetails.getStringExtra("blogsBody");
        wn = blogDetails.getStringExtra("blogsWriterName");


        getSupportActionBar().setTitle(bt);


        Glide.with(this).load(BLOG_URL + ci).into(coverImage);
        blogTitle.setText(bt);
        blogBody.setText(bb);
        writersName.setText(String.format("BY : %s", wn));
    }

    private void handleAppBarArrowColor() {

        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, offset) -> {


            Drawable upArrow = ResourcesCompat.getDrawable(BlogDetails.this.getResources(), R.drawable.ic_arrow_back_black_24dp, null);
            if (offset < -200) {
                upArrow.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
                BlogDetails.this.getSupportActionBar().setHomeAsUpIndicator(upArrow);

            } else {

                upArrow.setColorFilter(Color.parseColor("#953B9D"), PorterDuff.Mode.SRC_ATOP);
                BlogDetails.this.getSupportActionBar().setHomeAsUpIndicator(upArrow);
                BlogDetails.this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.blog_detail_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
        }


//        if (id == R.id.info) {
//            shareLink();
//            isMenuEnabled = true;
//            invalidateOptionsMenu();
//        }
        return super.onOptionsItemSelected(item);
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

}

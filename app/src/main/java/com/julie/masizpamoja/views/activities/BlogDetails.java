package com.julie.masizpamoja.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.julie.masizpamoja.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);
        ButterKnife.bind(this);


        Intent blogDetails = getIntent();
        ci = blogDetails.getStringExtra("blogsImage");
        bt = blogDetails.getStringExtra("blogsTitle");
        bb = blogDetails.getStringExtra("blogsBody");
        wn = blogDetails.getStringExtra("blogsWriterName");




        Glide.with(this).load(ci).into(coverImage);
        blogTitle.setText(bt);
        blogBody.setText(bb);
        writersName.setText("BY : " + wn);
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

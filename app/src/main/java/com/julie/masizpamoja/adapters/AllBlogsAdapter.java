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
import com.julie.masizpamoja.models.Blog;
import com.julie.masizpamoja.views.activities.BlogDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllBlogsAdapter extends RecyclerView.Adapter<AllBlogsAdapter.AllBlogsViewHolder> {

    private List<Blog> allBlogs;
    private Context context;

    public AllBlogsAdapter(List<Blog> allBlogs, Context context) {
        this.allBlogs = allBlogs;
        this.context = context;
    }

    @NonNull
    @Override
    public AllBlogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.all_blogs_layout, parent, false);
        return new AllBlogsViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AllBlogsViewHolder holder, int position) {

        Blog blog = allBlogs.get(position);
        holder.blogTitle.setText(blog.getTitle());
        Glide.with(context).load(blog.getImage()).into(holder.blogImage);


    }

    @Override
    public int getItemCount() {
        return allBlogs.size();
    }

    public class AllBlogsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.blog_image)
        ImageView blogImage;

        @BindView(R.id.blog_title)
        TextView blogTitle;


        public AllBlogsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                goToView(getAdapterPosition());


            });
        }
    }

    private void goToView(int adapterPosition) {

        Blog blogs = allBlogs.get(adapterPosition);

        Intent restaurantDetailIntent = new Intent(context, BlogDetails.class);
        restaurantDetailIntent.putExtra("blogsTitle",blogs.getTitle());
        restaurantDetailIntent.putExtra("blogsBody", blogs.getBody());
        restaurantDetailIntent.putExtra("blogsImage",blogs.getImage());
        restaurantDetailIntent.putExtra("blogsWriterName",blogs.getUser().getName());

        context.startActivity(restaurantDetailIntent);
    }
}

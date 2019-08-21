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
import com.julie.masizpamoja.models.RandomBlogs;
import com.julie.masizpamoja.views.activities.BlogDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RandomBlogAdapter extends RecyclerView.Adapter<RandomBlogAdapter.RandomBlogViewHolder> {

    private List<Blog> randomBlogs = new ArrayList<>();
    private Context context;

    public RandomBlogAdapter(List<Blog> randomBlogs, Context context) {
        this.randomBlogs = randomBlogs;
        this.context = context;
    }

    @NonNull
    @Override
    public RandomBlogAdapter.RandomBlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_random_blogs, parent, false);
        return new RandomBlogAdapter.RandomBlogViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RandomBlogAdapter.RandomBlogViewHolder holder, int position) {

        Blog randomBlog = randomBlogs.get(position);
        holder.title.setText(randomBlog.getTitle());
        holder.userName.setText(randomBlog.getUser().getName());
        Glide.with(context).load(randomBlog.getImage()).into(holder.coverPage);


    }

    @Override
    public int getItemCount() {
        return randomBlogs.size();
    }

    public class RandomBlogViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.username)
        TextView userName;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.cover_page)
        ImageView coverPage;


        public RandomBlogViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v->{
                goToView(getLayoutPosition());


            });
        }
    }

    private void goToView(int adapterPosition) {
        Blog blogs = randomBlogs.get(adapterPosition);

        Intent restaurantDetailIntent = new Intent(context, BlogDetails.class);
        restaurantDetailIntent.putExtra("blogsTitle",blogs.getTitle());
        restaurantDetailIntent.putExtra("blogsBody", blogs.getBody());
        restaurantDetailIntent.putExtra("blogsImage",blogs.getImage());
        restaurantDetailIntent.putExtra("blogsWriterName",blogs.getUser().getName());

        context.startActivity(restaurantDetailIntent);
    }
}

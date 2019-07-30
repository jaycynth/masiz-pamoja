package com.julie.masizpamoja.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.julie.masizpamoja.R;
import com.julie.masizpamoja.models.RandomBlogs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RandomBlogAdapter extends RecyclerView.Adapter<RandomBlogAdapter.RandomBlogViewHolder> {

    private List<RandomBlogs> randomBlogs = new ArrayList<>();
    private Context context;

    public RandomBlogAdapter(List<RandomBlogs> randomBlogs, Context context) {
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

        RandomBlogs randomBlog = randomBlogs.get(position);
        holder.title.setText(randomBlog.getTitle());
        holder.userName.setText(randomBlog.getUsername());

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


        public RandomBlogViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

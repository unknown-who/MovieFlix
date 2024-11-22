package com.example.movieflix.Adapter;

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
import com.example.movieflix.Activity.DetailActivity;
import com.example.movieflix.Domain.ListFilm;
import com.example.movieflix.R;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder> {
    private final ListFilm items;
    private Context context;

    public FilmListAdapter(ListFilm items) {
        this.items = items;
    }

    @NonNull
    @Override
    public FilmListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_film, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmListAdapter.ViewHolder holder, int position) {
        // Bind the data to the views
        holder.titleTxt.setText(items.getData().get(position).getTitle());
        holder.scoreTxt.setText(String.valueOf(items.getData().get(position).getImdbRating()));

        // Load the image using Glide
        Glide.with(holder.itemView.getContext())
                .load(items.getData().get(position).getPoster())
                .into(holder.pic);

        // Set the click listener for the item view
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("id", items.getData().get(position).getId());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.getData().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, scoreTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleText);
            scoreTxt = itemView.findViewById(R.id.scoreText);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}

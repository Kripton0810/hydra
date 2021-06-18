package com.kripton.hydra;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainDataAdapter extends RecyclerView.Adapter<MainDataAdapter.ViewHolder> {
    //Init var
    Activity activity;
    ArrayList<MainDataModel> list;

    //Creating const

    public MainDataAdapter(Activity activity, ArrayList<MainDataModel> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MainDataAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MainDataAdapter.ViewHolder holder, int position) {
        MainDataModel data = list.get(position);
        //Shimmer
        Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
                .setBaseColor(Color.parseColor("#F3F3F3"))
                .setBaseAlpha(1)
                .setHighlightColor(Color.parseColor("#E7E7E7"))
                .setHighlightAlpha(1)
                .setDropoff(50)
                .build();

        //Init shimmer
        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);
        Glide.with(activity).load(data.getImage())
                .placeholder(shimmerDrawable)
                .into(holder.image);
        holder.titel.setText(data.getTitel());
        holder.desc.setText(data.getDesc());
        holder.time.setText(data.getTime());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView titel, desc,time;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.news_image);
            titel = itemView.findViewById(R.id.news_headline);
            desc = itemView.findViewById(R.id.news_desc);
            time = itemView.findViewById(R.id.time);
        }
    }
}

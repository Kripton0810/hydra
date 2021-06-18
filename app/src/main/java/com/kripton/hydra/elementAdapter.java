package com.kripton.hydra;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class elementAdapter extends RecyclerView.Adapter<elementAdapter.ViewHolder> {
    List<elementModel> list;

    public elementAdapter(List<elementModel> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public elementAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull elementAdapter.ViewHolder holder, int position) {
        String titel = list.get(position).getName();
        String desc = list.get(position).getDesc();
        int image = list.get(position).getImage();
        holder.setData(image, titel, desc);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titel,desc;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.elementImage);
            titel = itemView.findViewById(R.id.elementName);
            desc = itemView.findViewById(R.id.eside_effect);
        }
        public void setData(int image,String name,String descc)
        {
            imageView.setImageResource(image);
            titel.setText(name);
            desc.setText(descc);
        }
    }
}

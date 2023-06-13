package com.example.chack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private ArrayList<post> arrayList;
    private Context context;

    public PostAdapter(ArrayList<post> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_post, parent, false);
        PostViewHolder holder= new PostViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Glide.with(holder.itemView).load(arrayList.get(position).getLocationImg()).into(holder.locationImg);
        Glide.with(holder.itemView).load(arrayList.get(position).getUserImg()).into(holder.userImg);
        holder.userName.setText(arrayList.get(position).getUserName());
        holder.tags.setText(arrayList.get(position).getTag());
        holder.mainPost.setText(arrayList.get(position).getMainText());
        holder.address.setText(arrayList.get(position).getAddress());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size():0);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView userImg;
        TextView userName;
        TextView tags;
        TextView mainPost;
        ImageView locationImg;
        TextView address;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            this.userName = itemView.findViewById(R.id.userNickName);
            this.tags = itemView.findViewById(R.id.tags);
            this.mainPost=itemView.findViewById(R.id.mainPost);
            this.locationImg=itemView.findViewById(R.id.locationImg);
            this.userImg = itemView.findViewById(R.id.userImg);
            this.address=itemView.findViewById(R.id.address);
        }
    }
}

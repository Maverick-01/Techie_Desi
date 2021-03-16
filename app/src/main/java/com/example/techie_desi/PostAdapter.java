package com.example.techie_desi;

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
import com.example.techie_desi.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;

import java.util.List;



public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Item> items;

    public PostAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.post_item,parent,false);
        return  new PostViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull PostViewHolder holder, int position){

            final Item item = items.get(position);
            holder.postTitle.setText(item.getTitle());

            Document document = Jsoup.parse(item.getContent());
            holder.postDescription.setText(document.text());
            Elements elements = document.select("img");

            if (elements.size()>0)
            Glide.with(context).load(elements.get(0).attr("src")).into(holder.postImage);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("url", item.getUrl());
                    context.startActivity(intent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView postTitle;
        TextView postDescription;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.postImage);
            postTitle = itemView.findViewById(R.id.postTitle);
            postDescription= itemView.findViewById(R.id.postDescription);
        }
    }
}

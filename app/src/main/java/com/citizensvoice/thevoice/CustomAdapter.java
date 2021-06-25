package com.citizensvoice.thevoice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.RVViewHolderClass> {

    Context context;
    int flags[];
    String[] items;
    String[] prices;

    public CustomAdapter(Context applicationContext, int[] flags, String[] items, String[] prices){
        this.context = applicationContext;
        this.flags = flags;
        this.items = items;
        this.prices = prices;
    }

    @NonNull
    @Override
    public CustomAdapter.RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomAdapter.RVViewHolderClass(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.RVViewHolderClass holder, final int position) {
        holder.objectImageView.setImageResource(flags[position]);
        holder.title.setText(items[position]);
        holder.likes.setText(prices[position]);
        final int imgUri = flags[position];
        final String Title = items[position];
        final String Likes = prices[position];
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntem = new Intent(v.getContext(), ViewPoll.class);
                viewIntem.putExtra("food_pic", imgUri);
                viewIntem.putExtra("food_name", Title);
                viewIntem.putExtra("food_price", Likes);
                // viewIntem.putExtra("food_rating", imgRating);
                context.startActivity(viewIntem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flags.length;
    }

    public class RVViewHolderClass extends RecyclerView.ViewHolder {
        TextView title, commision, likes, posted;
        ImageView objectImageView;
        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView21);
            commision = itemView.findViewById(R.id.textView19);
            posted = itemView.findViewById(R.id.textView20);
            likes = itemView.findViewById(R.id.textView22);
            objectImageView = itemView.findViewById(R.id.imageView5);
        }
    }

}

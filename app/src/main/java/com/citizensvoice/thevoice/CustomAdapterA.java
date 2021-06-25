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

public class CustomAdapterA extends RecyclerView.Adapter<CustomAdapterA.RVViewHolderClass> {

    Context context;
    int flags[];
    String[] items;

    public CustomAdapterA(Context applicationContext, int[] flags, String[] items){
        this.context = applicationContext;
        this.flags = flags;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomAdapterA.RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomAdapterA.RVViewHolderClass(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterA.RVViewHolderClass holder, final int position) {
        holder.objectImageView.setImageResource(flags[position]);
        holder.title.setText(items[position]);
        final int imgUri = flags[position];
        final String Title = items[position];
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntem = new Intent(v.getContext(), ViewPoll.class);
                viewIntem.putExtra("food_pic", imgUri);
                viewIntem.putExtra("food_name", Title);
                context.startActivity(viewIntem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flags.length;
    }

    public class RVViewHolderClass extends RecyclerView.ViewHolder {
        TextView title, posted;
        ImageView objectImageView;
        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView23);
            posted = itemView.findViewById(R.id.textView24);
            objectImageView = itemView.findViewById(R.id.imageView);
        }
    }

}

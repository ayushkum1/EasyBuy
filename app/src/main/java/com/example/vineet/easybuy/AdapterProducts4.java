package com.example.vineet.easybuy;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.vineet.easybuy.Model.product_modal;

import java.util.List;


public class AdapterProducts4 extends RecyclerView.Adapter<AdapterProducts4.MyViewHolder> {
    private Context context;
    private List<product_modal> postsLists;
    public static String position1;
    String newPosition;
    public AdapterProducts4(Context context, List<product_modal> postsLists) {
        this.context=context;
        this.postsLists=postsLists;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,price,qty;

        public MyViewHolder(View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.name);
            price= itemView.findViewById(R.id.price);
            qty= itemView.findViewById(R.id.qty);
        }
    }

    @NonNull
    @Override
    public AdapterProducts4.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemaddcart, parent, false);
        return new AdapterProducts4.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProducts4.MyViewHolder holder, final int position) {


        holder.name.setText(postsLists.get(position).getName());
        holder.price.setText(postsLists.get(position).getPrice());
        holder.qty.setText(postsLists.get(position).getCount());


    }

    @Override
    public int getItemCount() {
        return postsLists.size();
    }

}

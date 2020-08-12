package com.example.vineet.easybuy;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AdapterProducts3 extends RecyclerView.Adapter<AdapterProducts3.MyViewHolder> {
    Context context;

    ImageView imageView;
    List<String> namelist;
    List<String> pricelist;
    List<Drawable> imagelist;
    private DatabaseHandler db;
    // SessionManager sessionManager = new SessionManager();
    public AdapterProducts3(Context context, List<String> namelist, List<String> pricelist, List<Drawable> imagelist) {

        this.context = context;
        this.namelist = namelist;
        this.pricelist = pricelist;
        this.imagelist = imagelist;


    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,txt;
        ImageView imageView;
        LinearLayout apply_ll,item_ll;
        LinearLayout minus,plus;
        public MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            imageView = view.findViewById(R.id.image);
            item_ll = view.findViewById(R.id.item_ll);

            txt = view.findViewById(R.id.txt);
            minus = view.findViewById(R.id.minus);
            plus = view.findViewById(R.id.plus);


        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_products, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        db = new DatabaseHandler(context);
        holder.name.setText(namelist.get(position));
        holder.price.setText("MRP Rs. "+pricelist.get(position));

        holder.imageView.setImageDrawable(imagelist.get(position));
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.txt.getText().equals("1")){
                    holder.minus.setEnabled(true);

                    String new_count=String.valueOf(Integer.parseInt((String) holder.txt.getText())-1);


                    //update(new_count,jsonObject.getString("id"));

                    holder.txt.setText("0");
                    update(new_count,String.valueOf(position+11));
                    listdata1();

                }
                else if(holder.txt.getText().equals("0")){

                }
                else{

                    String new_count=String.valueOf(Integer.parseInt((String) holder.txt.getText())-1);
                    holder.txt.setText(new_count);
                    holder.minus.setEnabled(true);
                    update(new_count,String.valueOf(position+11));
                    listdata1();



                    //  update(new_count,jsonObject.getString("id"));

                    // holder.txt.setText(new_count);



                    // getdata1();
                    //total_cart();


                }
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String new_count=String.valueOf(Integer.parseInt((String) holder.txt.getText())+1);


                //update(new_count,jsonObject.getString("id"));
                holder.txt.setText(new_count);

                update(new_count,String.valueOf(position+11));
                listdata1();

            }

        });


    }

    @Override
    public int getItemCount() {
        return namelist.size();
    }


    private void update(String count, String item_name) {

        db.updateNewtag(count,item_name);

    }


    private void listdata1() {
        Products.totalamount.clear();

        Cursor cursor = db.getData1();
        if (cursor.moveToFirst()) {
            do {

                double total=(Double.parseDouble(cursor.getString(2))*Double.parseDouble(cursor.getString(3)));

                if(!cursor.getString(3).equals("0")){
                    Products.totalamount.add((int) total);


                }

            } while (cursor.moveToNext());
        }
        Log.e("Total", String.valueOf(Products.totalamount));
        total_cart1();

    }

    private void total_cart1() {

        double total = 0;

        for (int i = 0; i < Products.totalamount.size(); i++) {
            total += Products.totalamount.get(i);
        }
        Log.e("total", String.valueOf(total));

        Products.total_amt.setText("\u20B9"+" "+String.valueOf(total));


    }
}

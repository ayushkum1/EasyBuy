package com.example.vineet.easybuy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetail extends AppCompatActivity {

    ImageView image;
    TextView name,price;
    Button pay;
    SessionManager sessionManager;
    DbController dbController;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        pay = findViewById(R.id.pay);

        String name1 = getIntent().getStringExtra("name");
        String price1 = getIntent().getStringExtra("price");
        int pos = getIntent().getIntExtra("pos",0);
        String adapter = getIntent().getStringExtra("adapter");


        sessionManager  = new SessionManager(this);
        dbController = new DbController(this);
        context = this;

        name.setText(name1);
        price.setText(price1);

        sessionManager.setPreferences("amount",price1);

        if (adapter.equalsIgnoreCase("1")){
            image.setImageDrawable(Products.imagelist1.get(pos));
        }else {
            image.setImageDrawable(Products.imagelist2.get(pos));
        }

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
    }


    public void showPopUp3() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_popup, null);
        alertDialog.setView(view);
        alertDialog.setCancelable(true);
        TextView pme = view.findViewById(R.id.pme);
        TextView paytm = view.findViewById(R.id.paytm);

        final Dialog dialog1 = alertDialog.create();

        dialog1.show();

        pme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String amount = getIntent().getStringExtra("price");

                String amount = sessionManager.getPreferences("amount");
                String wallet_amount = sessionManager.getPreferences("wallet_amount");

                if (wallet_amount.isEmpty()){
                    Toast.makeText(context, "insufficient wallet balance", Toast.LENGTH_SHORT).show();
                }else {
                    float am1 = Float.parseFloat(amount);
                    float am2 = Float.parseFloat(wallet_amount);
                    if (am2<am1){
                        Toast.makeText(context, "insufficient wallet balance", Toast.LENGTH_SHORT).show();
                    }else {
                        float rem = am2-am1;
                        sessionManager.setPreferences("wallet_amount",String.valueOf(Math.round(rem)));

                        //dbController.inserttable1(s1,s2,s3,s4,s5,s6);

                        Toast.makeText(context, "purchase successful", Toast.LENGTH_SHORT).show();
                        finishAffinity();
                        Intent i = new Intent(ProductDetail.this, Menu.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                    }
                }

                dialog1.cancel();
            }
        });

        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.cancel();
                startActivity(new Intent(context,PayTMActivity.class)
                        .putExtra("status","1")
                );




            }
        });

    }

}

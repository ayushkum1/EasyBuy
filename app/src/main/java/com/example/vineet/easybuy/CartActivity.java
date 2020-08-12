package com.example.vineet.easybuy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vineet.easybuy.Model.product_modal;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView1;
    private LinearLayoutManager layoutManager;
    public static ArrayList<product_modal> cartList = new ArrayList<>();
    AdapterProducts4 adapterProducts4;
    private DatabaseHandler db;
    TextView total1;
    SessionManager sessionManager;
    DbController dbController;
    String s1,s2,s3,s4,s5,s6,s7;
    Context context;
    LinearLayout pay;
    String total_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartList.clear();
        db = new DatabaseHandler(getApplicationContext());
        //db.clearDatabase();
        sessionManager  = new SessionManager(this);
        dbController = new DbController(this);
        context = this;
        recyclerView1 = findViewById(R.id.recyclerView1);
        total1 = findViewById(R.id.total);
        pay = findViewById(R.id.pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });

        listdata1();
        listdata2();


        layoutManager=new LinearLayoutManager(CartActivity.this);
        adapterProducts4=new AdapterProducts4(CartActivity.this,cartList);
        adapterProducts4.notifyDataSetChanged();
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(adapterProducts4);


    }


    private void listdata1() {
        Products.totalamount.clear();

        Cursor cursor = db.getData1();
        if (cursor.moveToFirst()) {
            do {

                double total=(Double.parseDouble(cursor.getString(2))*Double.parseDouble(cursor.getString(3)));

                if(!cursor.getString(3).equals("0")){
                    String name=cursor.getString(1);
                    String price=cursor.getString(2);
                    String count=cursor.getString(3);
                    cartList.add(new product_modal(name,price,count));
                }

            } while (cursor.moveToNext());

        }

    }

    private void listdata2() {
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

        total1.setText("\u20B9"+" "+String.valueOf(total));
        sessionManager.setPreferences("amount",String.valueOf(total));



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
                        Log.e("Amount",String.valueOf(am1+" "+am2));
                        //Toast.makeText(context, "purchase successful", Toast.LENGTH_SHORT).show();
                        finishAffinity();
                        Intent i = new Intent(CartActivity.this, PaymentSuccessActivity.class);
                         i.putExtra("type","1");
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

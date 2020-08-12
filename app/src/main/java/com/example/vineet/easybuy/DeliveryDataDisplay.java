package com.example.vineet.easybuy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DeliveryDataDisplay extends AppCompatActivity {

    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    private Button b1;
    SessionManager sessionManager;
    DbController dbController;
    Context context;
    String s1,s2,s3,s4,s5,s6,s7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_data_display);

        sessionManager  = new SessionManager(this);
        dbController = new DbController(this);
        context = this;

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        tv7 = findViewById(R.id.tv7);
        b1 = findViewById(R.id.b1);

        s1 = sessionManager.getPreferences("Drop Location");
        s2 = sessionManager.getPreferences("Pickup Location");
        s3 = sessionManager.getPreferences("Content");
        s4 = sessionManager.getPreferences("Estimated value of content");
        s5 = sessionManager.getPreferences("Instruction");
        s6 = sessionManager.getPreferences("amount");
        s7 = sessionManager.getPreferences("distance");

        Log.e("s1",s1);
        Log.e("s2",s2);
        Log.e("s3",s3);
        Log.e("s4",s4);
        Log.e("s5",s5);
        Log.e("s6",s6);
        Log.e("s7",s7);

        tv1.setText(s1);
        tv2.setText(s2);
        tv3.setText(s3);
        tv4.setText(s4);
        tv5.setText(s5);
        tv6.setText(s6+" INR.");
        tv7.setText(s7+" KM");

        b1.setOnClickListener(new View.OnClickListener() {
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

                        dbController.inserttable1(s1,s2,s3,s4,s5,s6);

                        Toast.makeText(context, "purchase successful", Toast.LENGTH_SHORT).show();

                        finishAffinity();
                        Intent i = new Intent(DeliveryDataDisplay.this, Menu.class);
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

                        dialog1.cancel();
                        startActivity(new Intent(context,PayTMActivity.class)
                        .putExtra("status","2")
                        );

                    }
                }


            }
        });
        
    }
    
}

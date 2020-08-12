package com.example.vineet.easybuy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PayTMActivity extends AppCompatActivity {

    private Button submit;
    TextView amount,paytm_wallet;
    SessionManager sessionManager;
    DbController dbController;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_tm);

        submit = findViewById(R.id.submit);
        amount = findViewById(R.id.amount);
        paytm_wallet = findViewById(R.id.paytm_wallet);

        sessionManager  = new SessionManager(this);
        dbController = new DbController(this);
        context = this;

        String am= sessionManager.getPreferences("amount");
        String paytm_bal= sessionManager.getPreferences("paytm_wallet");

        if (paytm_bal.isEmpty()){
            sessionManager.setPreferences("paytm_wallet","1000000");
            paytm_bal= sessionManager.getPreferences("paytm_wallet");
        }


        amount.setText("Purchase Amount\n Rs."+Math.round(Float.parseFloat(am)));

        paytm_wallet.setText("PayTM wallet balance Rs. "+Math.round(Float.parseFloat(paytm_bal)));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amount = sessionManager.getPreferences("amount");
                String wallet_amount = sessionManager.getPreferences("paytm_wallet");

                if (wallet_amount.isEmpty()){
                    Toast.makeText(context, "insufficient balance", Toast.LENGTH_SHORT).show();
                }else {
                    float am1 = Float.parseFloat(amount);
                    float am2 = Float.parseFloat(wallet_amount);
                    if (am2<am1){
                        Toast.makeText(context, "insufficient balance", Toast.LENGTH_SHORT).show();
                    }else {

                        String status = getIntent().getStringExtra("status");
                        if (status.equals("1")){

                            float rem = am2-am1;
                            sessionManager.setPreferences("paytm_wallet",String.valueOf(Math.round(rem)));

                            paytm_wallet.setText("PayTM wallet balance Rs. "+Math.round(rem));
                            Toast.makeText(context, "purchase successful", Toast.LENGTH_SHORT).show();
                            finishAffinity();
                            Intent i = new Intent(PayTMActivity.this, PaymentSuccessActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("type","2");
                            startActivity(i);
                        }else {
                            String s1 = sessionManager.getPreferences("Drop and Pickup Location");
                            String s2= sessionManager.getPreferences("Content");
                            String s3= sessionManager.getPreferences("Estimated value of content");
                            String s4= sessionManager.getPreferences("Instruction");
                            String s5= sessionManager.getPreferences("Charges");
                            String am= sessionManager.getPreferences("amount");

                            float rem = am2-am1;
                            sessionManager.setPreferences("paytm_wallet",String.valueOf(Math.round(rem)));
                            dbController.inserttable1(s1,s2,s3,s4,s5,am);
                            paytm_wallet.setText("PayTM wallet balance Rs. "+Math.round(rem));
                            Toast.makeText(context, "purchase successful", Toast.LENGTH_SHORT).show();

                            finishAffinity();
                            Intent i = new Intent(PayTMActivity.this, PaymentSuccessActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("type","2");
                            startActivity(i);
                        }

                    }
                }
            }
        });





    }
}

package com.example.vineet.easybuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaymentSuccessActivity extends AppCompatActivity {
    SessionManager sessionManager;
    TextView txt;
    Button btn;
    String paytm_bal,wallet_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        sessionManager  = new SessionManager(this);
        txt=findViewById(R.id.txt);
        btn=findViewById(R.id.btn);

        paytm_bal= sessionManager.getPreferences("paytm_wallet");
        wallet_amount = sessionManager.getPreferences("wallet_amount");
        Log.e("Amount",paytm_bal+"1"+" "+wallet_amount);
        //Toast.makeText(PaymentSuccessActivity.this,String.valueOf(paytm_bal+" "+wallet_amount), Toast.LENGTH_SHORT).show();
        String status = getIntent().getStringExtra("type");
       // String status = "1";
        if(status.equals("1")){

            txt.setText("Payment successful!\nYour remaining Pocket Money EMI Wallet is "+wallet_amount+".");
        }else if(status.equals("2")){

            txt.setText("Payment successful!\nYour remaining Paytm Wallet is "+paytm_bal+".");
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentSuccessActivity.this, Menu.class);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

    }
}

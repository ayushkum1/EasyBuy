package com.example.vineet.easybuy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EasyEMI extends AppCompatActivity {
    public static final int TEXT_REQUEST = 1;
    public static final String EXTRA_MESSAGE = "com.example.vineet.easybuy.extra.MESSAGE";
    private EditText amt;
    SessionManager sessionManager;
    DbController dbController;
    Context context;
    private Button b1;
    private TextView w_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_emi);
        amt = findViewById(R.id.et1);
        b1 = findViewById(R.id.b1);
        w_amount = findViewById(R.id.w_amount);

        sessionManager  = new SessionManager(this);
        dbController = new DbController(this);
        context = this;
        final String wallet_amount = sessionManager.getPreferences("wallet_amount");
        if (wallet_amount.isEmpty()){
            w_amount.setText("Wallet Amount : Rs. 0.0");
        }else {
            w_amount.setText("Wallet Amount : Rs. "+wallet_amount);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteram = amt.getText().toString().trim();
                String wallet_amount = sessionManager.getPreferences("wallet_amount");
                if (!enteram.isEmpty()){
                    if (wallet_amount.isEmpty()){
                        float am2 = Float.parseFloat(enteram);

                        am2 = am2+((am2*10)/100);
                        am2 = Math.round(am2);
                        sessionManager.setPreferences("wallet_amount",String.valueOf(am2));

                        w_amount.setText("Wallet Amount : "+am2);
                    }else {
                        float am = Float.parseFloat(wallet_amount);
                        float am2 = Float.parseFloat(enteram);
                        am2 = am2+((am2*10)/100);
                        float nam = am+am2;
                        nam = Math.round(nam);
                        sessionManager.setPreferences("wallet_amount",String.valueOf(nam));

                        w_amount.setText("Wallet Amount : "+nam);
                    }
                    Intent i = new Intent(EasyEMI.this, Menu.class);
                    i.putExtra("type","1");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    Toast.makeText(context, "Amount added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

   /* public void emicalc(View view) {
        Intent intent = new Intent(this, EMICalculator.class);
        String message = amt.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra("message", String.valueOf(amt));
        startActivityForResult(intent, TEXT_REQUEST);
        startActivity(intent);
    }*/
}

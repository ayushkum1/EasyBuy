package com.example.vineet.easybuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EMICalculator extends AppCompatActivity {
    private TextView mtv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emicalculator);
        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();
        String amount = intent.getStringExtra(EasyEMI.EXTRA_MESSAGE);
        mtv1=findViewById(R.id.textView21);
        double amtt = Double.parseDouble(amount);
        amtt = amtt+amtt*0.1;
        mtv1.setText(String.valueOf(amtt));

    }
}

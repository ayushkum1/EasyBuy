package com.example.vineet.easybuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class loantypeoption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loantypeoption);
        Intent intent = getIntent();
        String messge = intent.getStringExtra(signuppmoney.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.tv20);
        textView.setText(messge);
    }

    public void pocketmoney(View view)
    {
        Intent intent = new Intent(this,EasyEMI.class);
        startActivity(intent);
    }

    public void loan(View view)
    {
        Intent intent = new Intent(this,Pmoney.class);
        startActivity(intent);
    }
}

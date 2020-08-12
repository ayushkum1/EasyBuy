package com.example.vineet.easybuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class verifypmoney extends AppCompatActivity {
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifypmoney);
        Intent intent = getIntent();
        String message = intent.getStringExtra(signuppmoney.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.tv8);
        textView.setText(message);
        btn1 = findViewById(R.id.cont);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verifypmoney.this, loantypeoption.class));
            }
        });
    }
}

package com.example.vineet.easybuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class signuppmoney extends AppCompatActivity {
    Button b1;
    public static final int TEXT_REQUEST = 1;
    public static final String EXTRA_MESSAGE = "com.example.vineet.easybuy.extra.MESSAGE";
    private EditText nam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppmoney);

        nam = findViewById(R.id.et2);

    }

    public void proceed(View view) {
        Intent intent = new Intent(this, verifypmoney.class);
        String message = nam.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra("name", String.valueOf(nam));
        startActivityForResult(intent, TEXT_REQUEST);
    }
}

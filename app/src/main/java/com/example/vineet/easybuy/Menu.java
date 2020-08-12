package com.example.vineet.easybuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Button bt1, bt2, bt3;
    private DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db = new DatabaseHandler(getApplicationContext());
        db.clearDatabase();
    }

    public void delivery(View view)
    {
       Intent intent = new Intent(Menu.this,Delivery.class);
       startActivity(intent);
    }

    public void shopping(View view)
    {
        Intent intent = new Intent(Menu.this,Products.class);
        startActivity(intent);
    }

    public void banking(View view)
    {
        Intent intent = new Intent(Menu.this,signuppmoney.class);
        startActivity(intent);
    }
}

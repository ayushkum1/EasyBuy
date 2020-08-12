package com.example.vineet.easybuy;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vineet.easybuy.Common.Common;
import com.example.vineet.easybuy.Model.Users;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
//    MaterialFancyButton btnSignin;
//    CustomEditText editTextEmail, editTextPass;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button btnSignin;
    EditText editTextEmail, editTextPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.textView);
        TextView tvNA = findViewById(R.id.tvNoAcc);
        Typeface typeface;
        typeface = Typeface.createFromAsset(getAssets(),"fonts/font.ttf");
        tv.setTypeface(typeface);
        btnSignin = findViewById(R.id.btnSignIn);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);

        //editTextEmail.setText("a@gmail.com");
        //editTextPass.setText("12345");

        final ConstraintLayout layout = findViewById(R.id.signInView);
        tvNA.setTypeface(typeface);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        tvNA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUp.class));

            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = editTextEmail.getText().toString();
                final String pass = editTextPass.getText().toString();
                if (!email.isEmpty()){
                    if (!pass.isEmpty()){
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Users user = dataSnapshot.child(email.replace(".","_")).getValue(Users.class);

                                if (user!=null){
                                    if (user.getPassword().equals(pass)){
                                        Common.currentUser = user;
                                        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(MainActivity.this,Menu.class));
                                    }
                                    else {
                                        Snackbar.make(layout,"Check Your Credentials",Snackbar.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Snackbar.make(layout,"Check Your Credentials or Sign up",Snackbar.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }

            }
        });




    }
}

package com.example.vineet.easybuy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vineet.easybuy.Common.Common;
import com.example.vineet.easybuy.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    Button btnSignUp;
    EditText editTextName, editTextEmail, editTextPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        TextView tv = findViewById(R.id.textView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        tv.setTypeface(typeface);
        btnSignUp = findViewById(R.id.btnSignUp);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);
        editTextName = findViewById(R.id.editTextName);
        final ConstraintLayout layout = findViewById(R.id.signUpView);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editTextName.getText().toString();
                final String email = editTextEmail.getText().toString();
                final String pass = editTextPass.getText().toString();

                if (Common.isConnected(getBaseContext())) {
                    final ProgressDialog dialog = new ProgressDialog(SignUp.this);
                    dialog.setMessage("Signing You Up");
                    dialog.show();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(email.replace(".", "_")).exists()) {
                                Snackbar.make(layout, "That's Already Taken. Try Another Email", Snackbar.LENGTH_SHORT).show();
                            } else {
                                Users users = new Users(name, email, pass);
                                reference.child(email.replace(".", "_")).setValue(users);
                                Toast.makeText(SignUp.this, "Registered!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(SignUp.this, MainActivity.class));
                            }
                            dialog.dismiss();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Snackbar.make(layout, "Make Sure, You Have An Active Internet Connection", Snackbar.LENGTH_LONG).setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivityForResult(new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS), 0);
                        }
                    }).show();
                }
            }
        });
    }
}

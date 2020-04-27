package com.example.curddatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    private EditText edtEmail, edtUsername, edtPassword, edtFullName, edtPhone;
    private Button btnFetch, btnSignout, btnUpdate, btnDelete;
    private TextView userTextView;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        userTextView = (TextView)findViewById(R.id.userTextView);
        edtEmail = (EditText)findViewById(R.id.emailEditText);
        edtUsername = (EditText)findViewById(R.id.usernameEditText);
        edtPassword = (EditText)findViewById(R.id.passwordEditText);
        edtFullName = (EditText)findViewById(R.id.fullNameEditText);
        edtPhone = (EditText)findViewById(R.id.contactEditText);

        btnFetch = (Button)findViewById(R.id.fetchButton);
        btnSignout = (Button)findViewById(R.id.signoutButton);
        btnUpdate = (Button)findViewById(R.id.updateButton);
        btnDelete = (Button)findViewById(R.id.deleteButton);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        userTextView.setText("Hello " + user.getEmail());

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference().child(user.getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String email = dataSnapshot.child("email").getValue().toString();
                        String username = dataSnapshot.child("username").getValue().toString();
                        String password = dataSnapshot.child("password").getValue().toString();
                        String fullname = dataSnapshot.child("fullname").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();

                        edtEmail.setText(email);
                        edtUsername.setText(username);
                        edtPassword.setText(password);
                        edtFullName.setText(fullname);
                        edtPhone.setText(phone);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}

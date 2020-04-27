package com.example.curddatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    private EditText edtEmail, edtUsername, edtPassword, edtFullname, edtPhone;
    private Button btnSignin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        edtEmail = (EditText)findViewById(R.id.emailEditText);
        edtUsername = (EditText)findViewById(R.id.usernameEditText);
        edtPassword = (EditText)findViewById(R.id.passwordEditText);
        edtFullname = (EditText)findViewById(R.id.fullNameEditText);
        edtPhone = (EditText)findViewById(R.id.contactEditText);
        btnSignup = (Button)findViewById(R.id.registerButton);
        btnSignin = (Button)findViewById(R.id.signinButton);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = edtEmail.getText().toString();
                final String username = edtUsername.getText().toString();
                final String password = edtPassword.getText().toString();
                final String fullname = edtFullname.getText().toString();
                final String phone = edtPhone.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Email Field is empty");
                    return;
                }
                if (TextUtils.isEmpty(username)) {
                    edtUsername.setError("Username Field is empty");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    edtPassword.setError("Password Field is empty");
                    return;
                }
                if (password.length() < 6) {
                    edtEmail.setError("Password Must contain more than 6 characters");
                    return;
                }
                if (TextUtils.isEmpty(fullname)) {
                    edtFullname.setError("Full Name Field is empty");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    edtPhone.setError("Phone No. Field is empty");
                    return;
                }
                if (phone.length() < 10) {
                    edtEmail.setError("Please enter valid phone number");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "CreateUserWithEmail:onComplete" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                if (task.isSuccessful()) {

                                    String uid = mAuth.getCurrentUser().getUid();
                                    DatabaseReference current_user_db = reference.child(uid);

                                    current_user_db.child("email").setValue(email);
                                    current_user_db.child("username").setValue(username);
                                    current_user_db.child("password").setValue(password);
                                    current_user_db.child("fullname").setValue(fullname);
                                    current_user_db.child("phone").setValue(phone);

                                    Toast.makeText(SignupActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(SignupActivity.this,"Registration Filed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}

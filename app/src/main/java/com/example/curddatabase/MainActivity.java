package com.example.curddatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText edtEmail, edtPassword;
    private Button btnSignin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        edtEmail = (EditText)findViewById(R.id.emailEdit);
        edtPassword = (EditText)findViewById(R.id.passwordEdit);
        btnSignin = (Button)findViewById(R.id.signinButton);
        btnSignup = (Button)findViewById(R.id.signupButton);

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity
                    .this, DashboardActivity.class));
            finish();
        }

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edtEmail.getText().toString();
                final String password = edtPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Email id is required to login");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    edtPassword.setError("Password field is empty");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Email or password is incorrect please check", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });
    }
}

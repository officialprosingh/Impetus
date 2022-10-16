package com.pro.classrewards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button loginback, signin , forgot;
    private EditText l_email, l_password;
    private ProgressBar login_progress;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);

        if (mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        loginback = findViewById(R.id.login_back);
        signin = findViewById(R.id.login_sign);
        forgot = findViewById(R.id.login_recover);
        l_email = findViewById(R.id.login_email);
        l_password = findViewById(R.id.login_password);


        loginback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = l_email.getText().toString().trim();
                String password = l_password.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    l_email.setError("Please Enter a Valid Email");
                    l_email.requestFocus();
                    return;
                }

                if (email.isEmpty()){
                    l_email.setError("Email is Required");
                    l_email.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    l_password.setError("Password is Required");
                    l_password.requestFocus();
                    return;
                }

                if (password.length()<6){
                    l_password.setError("Maximum Length of Password Should be 6");
                    l_password.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else {

                            Toast.makeText(LoginActivity.this, "Authentication failed. Check Your Email or Password",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });


    }
}
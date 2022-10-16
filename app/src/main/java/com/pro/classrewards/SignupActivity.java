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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private Button register_back, signup;
    private EditText login_name, login_email, login_password, login_university, login_number;
    private FirebaseAuth mAuth;
    private ProgressBar signup_progress ;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);




        if (mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(SignupActivity.this, MainActivity.class));
        }


        register_back = findViewById(R.id.register_back);
        signup = findViewById(R.id.register_signup);
        login_name = findViewById(R.id.register_name);
        login_email = findViewById(R.id.register_email);
        login_password = findViewById(R.id.register_password);
        login_university = findViewById(R.id.register_uni);
        signup_progress = (ProgressBar) findViewById(R.id.sign_up_progress);
        login_number = findViewById(R.id.register_number);


        register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Login();
            }
        });

    }

    private void Login(){
        final String email = login_email.getText().toString().trim();
        final String name = login_name.getText().toString().trim();
        final String university = login_university.getText().toString().trim();
        final String password = login_password.getText().toString().trim();
        final String number = login_number.getText().toString().trim();
        final int money = 0;
        String userId = mAuth.getUid();


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            login_email.setError("Please Enter a Valid Email");
            login_email.requestFocus();
            return;
        }

        if (email.isEmpty()){
            login_email.setError("Email is Required");
            login_email.requestFocus();
            return;
        }

        if (name.isEmpty()){
            login_name.setError("Name is Required");
            login_name.requestFocus();
            return;
        }

        if (number.isEmpty()){
            login_number.setError("PID is Required");
            login_number.requestFocus();
            return;
        }

        if (university.isEmpty()){
            login_university.setError("University Name is Required");
            login_university.requestFocus();
            return;
        }

        if (password.isEmpty()){
            login_password.setError("Password is Required");
            login_password.requestFocus();
            return;
        }

        if (password.length()<6){
            login_password.setError("Maximum Length of Password Should be 6");
            login_password.requestFocus();
            return;
        }

        signup_progress.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                signup_progress.setVisibility(View.GONE);
                if (task.isSuccessful()){

                    String userId = mAuth.getUid();


                      databaseReference.child("students").child(userId).child("Full name").setValue(name);
                   databaseReference.child("students").child(userId).child("Email").setValue(email);
                   databaseReference.child("students").child(userId).child("Password").setValue(password);
                    databaseReference.child("students").child(userId).child("University").setValue(university);
                    databaseReference.child("students").child(userId).child("PID").setValue(number);
                    databaseReference.child("students").child(userId).child("Points").setValue(money);



                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "Email is Already Registered ", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }


}
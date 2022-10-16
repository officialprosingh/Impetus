package com.pro.classrewards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WithdrawActivity extends AppCompatActivity {

    private TextView main_rewards;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("students");
    Integer Score;
    private FirebaseAuth mAuth;
    Button back;

    RelativeLayout rl1, rl2, rl3, rl4, rl5;
    private int starbucks = 150;
    private int mc = 150;
    private int amazon = 450;
    private int hm = 450;
    private int addidas = 300;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        mAuth = FirebaseAuth.getInstance();
        back = findViewById(R.id.withdraw_back);
        main_rewards = findViewById(R.id.withdraw_rewards);

        rl1 = findViewById(R.id.withdraw_card1);
        rl2 = findViewById(R.id.withdraw_card2);
        rl3 = findViewById(R.id.withdraw_card3);
        rl4 = findViewById(R.id.withdraw_card4);
        rl5 = findViewById(R.id.withdraw_card5);

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String userId = mAuth.getUid();
                        Score = snapshot.child(userId).child("Points").getValue(Integer.class);

                        if (Score >= starbucks){
                            showUpdateDeleteDialog();
                        } else {
                            Toast.makeText(WithdrawActivity.this,
                                    "Not Enough Points", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WithdrawActivity.this, MainActivity.class));
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userId = mAuth.getUid();
                Score = dataSnapshot.child(userId).child("Points").getValue(Integer.class);
                main_rewards.setText(Integer.toString(Score));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });

    }

    public void showUpdateDeleteDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.confirm_redeem, null);
        dialogBuilder.setView(dialogView);

        final TextView textView = (TextView) dialogView.findViewById(R.id.redeem_name);
        final TextView textView1 = (TextView) dialogView.findViewById(R.id.redeem_points);
        final Button confirm = findViewById(R.id.redeem_btn);
        final TextView textView2 = (TextView) dialogView.findViewById(R.id.redeem_title);

        final String name = "Starbucks";
        final String mpoints = "150";
        final String title = "Redeem Confirmation";

        textView.setText(name);
        textView1.setText(mpoints);
        textView2.setText(title);

        final AlertDialog b = dialogBuilder.create();
        b.show();

    }

}
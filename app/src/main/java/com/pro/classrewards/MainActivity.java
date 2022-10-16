package com.pro.classrewards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.checkerframework.checker.units.qual.A;


public class MainActivity extends AppCompatActivity implements UserAdapter.MyUserAdapter.OnNoteListener {

    private Button logout;
    private FirebaseAuth auth;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private TextView main_rewards;
    Integer Score;
    private TextView dateTimeDisplay;
    private Button withdraw, addclass;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("students");
    private ImageView main_profile;

    RecyclerView recyclerView;
    ArrayList<User> list2;
    DatabaseReference databseclass;
    UserAdapter userAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        main_rewards = findViewById(R.id.rewards);
        withdraw = findViewById(R.id.withdraw);
        dateTimeDisplay = findViewById(R.id.todaydate);
        addclass = findViewById(R.id.addclass);
        main_profile = findViewById(R.id.main_profile);



        recyclerView = findViewById(R.id.mainRecycler);
        String userId = mAuth.getUid();
        databseclass = FirebaseDatabase.getInstance().getReference("students").child(userId).child("Class");
        list2 = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(this, list2, this);
        recyclerView.setAdapter(userAdapter);



        main_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
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


        addclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(new Intent(MainActivity.this, Addclass_Activity.class)));
            }
        });

        String currentDate = new SimpleDateFormat("EEE, MMM d (hh:mm aaa)", Locale.getDefault()).format(new Date());

        dateTimeDisplay.setText(currentDate);



        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WithdrawActivity.class));
            }
        });






        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                    finish();
                }
            }
        };


        mAuth = FirebaseAuth.getInstance();
        if (user == null) {
            finish();
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        }




    }



    public void addMoney(){
        String userId = mAuth.getUid();
        int adding = 10;
        int finalmoney = adding + Score;
        main_rewards.setText(Integer.toString(finalmoney));
        databaseReference.child(userId).child("Points").setValue(finalmoney);


    }

    public void minusMoney(){
        String userId = mAuth.getUid();
        int adding = 10;
        int finalmoney = Score - adding ;
        main_rewards.setText(Integer.toString(finalmoney));
        databaseReference.child(userId).child("Points").setValue(finalmoney);
    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);

        FirebaseUser currentUser = mAuth.getCurrentUser();

        list2.clear();

        databseclass.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    list2.add(user);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onNoteClick(int position) {
        addMoney();
    }
}
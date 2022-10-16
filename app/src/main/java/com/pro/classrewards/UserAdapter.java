package com.pro.classrewards;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyUserAdapter> {

    Context context;
    ArrayList<User> list2;
    private FirebaseAuth mAuth;
    Integer Score;

    private MyUserAdapter.OnNoteListener onNoteListener;




    DatabaseReference databaseReference ;
    DatabaseReference datapoints;

    public UserAdapter(Context context, ArrayList<User> list2, MyUserAdapter.OnNoteListener onNoteListener) {
        this.context = context;
        this.list2 = list2;
        this.onNoteListener = onNoteListener;
    }



    @NonNull
    @Override
    public UserAdapter.MyUserAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.layout_user_course_list, parent, false);

        return new MyUserAdapter(v, onNoteListener);    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyUserAdapter holder, int position) {

        User user = list2.get(position);
        holder.uname.setText(user.getUname());
        holder.unumber.setText(user.getUnumber());
        holder.uteacher.setText(user.getUteacher());




        databaseReference = FirebaseDatabase.getInstance().getReference("Class").child(user.getUnumber());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String checkin = null;
                checkin = snapshot.child("check").getValue().toString();

                if (checkin.equals("yes")){
                    holder.check.setVisibility(View.VISIBLE);
                } else {
                    holder.check.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return list2.size();
    }

    public static class MyUserAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView uname, uteacher, unumber, done;
        Button check;
        OnNoteListener onNoteListener;
        private FirebaseAuth mAuth;
        DatabaseReference datapoints = FirebaseDatabase.getInstance().getReference("students");
        Integer Score;


        public MyUserAdapter(@NonNull View itemView, OnNoteListener onNoteListener ) {
            super(itemView);

            uname = itemView.findViewById(R.id.c_name);
            uteacher = itemView.findViewById(R.id.c_teacher);
            unumber = itemView.findViewById(R.id.c_number);
            check = itemView.findViewById(R.id.c_btn);
            done = itemView.findViewById(R.id.c_done);
            mAuth = FirebaseAuth.getInstance();


            this.onNoteListener = onNoteListener;
            check.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }

        public interface OnNoteListener{
            void onNoteClick(int position);
        }
    }


}

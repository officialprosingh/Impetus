package com.pro.classrewards;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewAdapter> {

    Context context;
    ArrayList<Course> list;

    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    User user;


    public CourseAdapter(Context context, ArrayList<Course> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CourseAdapter.MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.layout_course_list, parent, false);
        return new MyViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.MyViewAdapter holder, int position) {
         Course course = list.get(position);
         holder.cname.setText("Course Name" + " - " +  course.getCname());
         holder.chour.setText(course.getChour() + " " + "Hours");
         holder.clocation.setText("Location" + " - " + course.getClocation());
         holder.cnumber.setText(course.getCnumber());
         holder.cteacher.setText("Instructor" + " - " + course.getCteacher());

         user = new User();

         holder.add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 mAuth = FirebaseAuth.getInstance();
                 String userId = mAuth.getUid();
                 databaseReference = FirebaseDatabase.getInstance().getReference("students").child(userId).child("Class");

                 user.setUname(course.getCname());
                 user.setUteacher(course.getCteacher());
                 user.setUnumber(course.getCnumber());
                 databaseReference.child(course.getCnumber()).setValue(user);
             }
         });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewAdapter extends RecyclerView.ViewHolder{

        TextView chour, cteacher, clocation, cname, cnumber;
        Button add;
        public MyViewAdapter(@NonNull View itemView){
            super(itemView);

            chour = itemView.findViewById(R.id.addclass_hour);
            cteacher = itemView.findViewById(R.id.addclass_teacher);
            clocation = itemView.findViewById(R.id.addclass_location);
            cname = itemView.findViewById(R.id.addclass_name);
            cnumber = itemView.findViewById(R.id.addclass_number);

            add = itemView.findViewById(R.id.addclass_btn);

        }
    }
}

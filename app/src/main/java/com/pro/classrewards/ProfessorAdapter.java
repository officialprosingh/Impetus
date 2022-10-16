package com.pro.classrewards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.MyProfessorAdapter> {

    Context context;
    ArrayList<Course> list;

    DatabaseReference databaseReference ;


    public ProfessorAdapter(Context context, ArrayList<Course> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProfessorAdapter.MyProfessorAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_professor_access, parent, false);
        return new MyProfessorAdapter(v);    }

    @Override
    public void onBindViewHolder(@NonNull ProfessorAdapter.MyProfessorAdapter holder, int position) {

        Course course = list.get(position);
        holder.cname.setText("Course Name" + " - " +  course.getCname());
        holder.chour.setText(course.getChour() + " " + "Hours");
        holder.clocation.setText("Location" + " - " + course.getClocation());
        holder.cnumber.setText(course.getCnumber());
        holder.cteacher.setText("Instructor" + " - " + course.getCteacher());
        holder.check.setText(course.getCheck());

        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Class").child(course.getCnumber());
                databaseReference.child("check").setValue("yes");
            }
        });

        holder.end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Class").child(course.getCnumber());
                databaseReference.child("check").setValue("no");
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyProfessorAdapter extends RecyclerView.ViewHolder{

        TextView chour, cteacher, clocation, cname, cnumber, check;
         Button start, end;

        public MyProfessorAdapter(@NonNull View itemView) {
            super(itemView);

            chour = itemView.findViewById(R.id.professor_hour);
            cteacher = itemView.findViewById(R.id.professor_teacher);
            clocation = itemView.findViewById(R.id.professor_location);
            cname = itemView.findViewById(R.id.professor_name);
            cnumber = itemView.findViewById(R.id.professor_number);
            check = itemView.findViewById(R.id.check);

            start = itemView.findViewById(R.id.startclass_btn);
            end = itemView.findViewById(R.id.endclass_btn);
        }
    }
}

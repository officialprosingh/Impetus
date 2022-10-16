package com.pro.classrewards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.A;

public class Putclass_Activity extends AppCompatActivity {

    private EditText cnumber, cname, cteacher, clocation, chour;
    private Button p_add;

    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    Course course;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putclass);

        mAuth = FirebaseAuth.getInstance();

        cnumber = findViewById(R.id.c_number);
        cname = findViewById(R.id.c_name);
        cteacher = findViewById(R.id.c_teacher);
        clocation = findViewById(R.id.c_location);
        chour = findViewById(R.id.c_hours);
        p_add = findViewById(R.id.put_add);
        String check = "no";

        course = new Course();

        p_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userId = mAuth.getUid();
                final String number = cnumber.getText().toString().trim();
                final String name = cname.getText().toString().trim();
                final String teacher = cteacher.getText().toString().trim();
                final String location = clocation.getText().toString().trim();
                final String hours = chour.getText().toString().trim();


                if (number.isEmpty()){
                    cnumber.setError("Catalog number is Required");
                    cnumber.requestFocus();
                    return;
                }

                if (name.isEmpty()){
                    cname.setError("Course Name is Required");
                    cname.requestFocus();
                    return;
                }

                if (teacher.isEmpty()){
                    cteacher.setError("Instructor Name is Required");
                    cteacher.requestFocus();
                    return;
                }

                if (location.isEmpty()){
                    clocation.setError("Class Location is Required");
                    clocation.requestFocus();
                    return;
                }

                if (hours.isEmpty()){
                    chour.setError("Credit Hours are Required");
                    chour.requestFocus();
                    return;
                }

                course.setCname(name);
                course.setChour(hours);
                course.setClocation(location);
                course.setCnumber(number);
                course.setCteacher(teacher);
                course.setCheck(check);

                databaseReference = FirebaseDatabase.getInstance().getReference().child("Class").child(number);

                databaseReference.setValue(course);

                Toast.makeText(Putclass_Activity.this,
                        "Class Added Successful", Toast.LENGTH_SHORT).show();




            }
        });



    }


}
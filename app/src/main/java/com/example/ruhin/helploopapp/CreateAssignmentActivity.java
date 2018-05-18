package com.example.ruhin.helploopapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * created by jatin
 * version 2
 * this class allows the user to manually enter assignment informations and store it in firebase
 */
public class CreateAssignmentActivity extends AppCompatActivity {
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private EditText assignInfo, assignClass;
    private Button makeAssignment;
    private DatePicker datePicker;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assignment);
         mRef = FirebaseDatabase.getInstance().getReference();
         mAuth = FirebaseAuth.getInstance();
         datePicker = findViewById(R.id.datePicker);
         makeAssignment = findViewById(R.id.confirm_assign_btn);
         assignInfo = findViewById(R.id.assign_info);
         toolbar = findViewById(R.id.create_assign_toolbar);
         setSupportActionBar(toolbar);
         getSupportActionBar().setTitle("Create an assignment");
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setDisplayShowHomeEnabled(true);
         toolbar.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });
         assignClass = findViewById(R.id.assign_class);
         makeAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month = datePicker.getMonth() + 1 +"";
                String day = datePicker.getDayOfMonth() + "";
                String year = datePicker.getYear()+"";
                String formatedDate = month+"/"+day+"/"+year;
                Assignment newAssignment = new Assignment(assignInfo.getText().toString().trim(),assignClass.getText().toString().trim(),formatedDate);
                mRef.child("Users").child(mAuth.getCurrentUser().getUid()).child("Assignments").child(assignInfo.getText().toString().hashCode()+"").setValue(newAssignment).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                             Toast.makeText(CreateAssignmentActivity.this, "New assignment added", Toast.LENGTH_SHORT).show();
                            Intent toMain = new Intent(CreateAssignmentActivity.this, MainActivity.class);
                            startActivity(toMain);
                            finish();
                        }
                    }
                });
            }
        });

    }

}

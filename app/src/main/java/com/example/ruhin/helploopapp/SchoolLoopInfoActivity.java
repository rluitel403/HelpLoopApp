package com.example.ruhin.helploopapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SchoolLoopInfoActivity extends AppCompatActivity {
    private EditText schoolloopName, schoollooppass;
    private Button confirm;
    private DatabaseReference mDataBase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_loop_info);
        final String username = getIntent().getStringExtra("username");
        schoolloopName = findViewById(R.id.schoolloop_username);
        schoollooppass = findViewById(R.id.schoolloop_pass);
        confirm = findViewById(R.id.schoolloop_confirm_btn);
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> userInfo = new HashMap<>();
                userInfo.put("Name",username);
                userInfo.put("SchoolloopName",schoolloopName.getText().toString().trim());
                userInfo.put("SchoolloopPass",schoollooppass.getText().toString().trim());
                mDataBase.child("Users").child(mAuth.getCurrentUser().getUid()).child("SchoolLoopInfo").setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent toMain = new Intent(SchoolLoopInfoActivity.this, MainActivity.class);
                            startActivity(toMain);
                            finish();
                        }
                    }
                });
            }
        });

    }
}

package com.example.ruhin.helploopapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText name, email, pass;
    private Button signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            Intent toMain = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(toMain);
        }
        email = findViewById(R.id.signup_email);
        name = findViewById(R.id.signup_name);
        pass = findViewById(R.id.signup_pass);
        signUpBtn = findViewById(R.id.register_btn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString().trim();
                String userPass = pass.getText().toString().trim();
                final String userName = name.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent toSchoolLoopInfo = new Intent(RegisterActivity.this,SchoolLoopInfoActivity.class);
                            toSchoolLoopInfo.putExtra("username",userName);
                            startActivity(toSchoolLoopInfo);
                            finish();
                            Toast.makeText(RegisterActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

    }

}

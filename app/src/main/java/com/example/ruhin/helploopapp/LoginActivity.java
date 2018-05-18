package com.example.ruhin.helploopapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

/**
 * created by jatin
 * version 2
 * this class allows the user to login and go to the mainpage
 */
public class LoginActivity extends AppCompatActivity {
    private EditText login_email;
    private EditText login_pass;
    private Button login_btn;
    private TextView noAcc;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_email = findViewById(R.id.login_email);
        login_pass = findViewById(R.id.login_pass);
        mAuth = FirebaseAuth.getInstance();
        noAcc = findViewById(R.id.already_member_btn);
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Logging in");
        login_btn = findViewById(R.id.login_btn);
        noAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(toRegister);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dialog.show();
                    mAuth.signInWithEmailAndPassword(login_email.getText().toString().trim(),login_pass.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                dialog.dismiss();
                                Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(toMain);
                                finish();
                            }else{
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });

    }
}

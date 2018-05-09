package com.example.ruhin.helploopapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button signout;
    private FirebaseAuth mAuth;
    private TabLayout tabs;
    private ViewPager viewPager;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HelpLoop");
        tabs = findViewById(R.id.main_tablayout);
        viewPager = findViewById(R.id.main_viewpager);
        FragmentPagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_btn:
                mAuth.signOut();
                Intent toRegister = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(toRegister);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
/*
signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent toRegister = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(toRegister);
                finish();
            }
        });
 */
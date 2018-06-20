package com.example.ruhin.helploopapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

/**
 *
 *author - rubin luitel
 * version : 3
 * This class displays the viewpager which shows the chatfragment and the assignment fragment, it also calls schoolloopdataretrieve to run the background task and it also setsup the notification
 *
 */
public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TabLayout tabs;
    private ViewPager viewPager;
    private DatabaseReference databaseReference;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if(mAuth == null){
            Intent toLogin = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(toLogin);
            finish();
        }
        createNotificiation();
        getSupportActionBar().setTitle("HelpLoop");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        tabs = findViewById(R.id.main_tablayout);
        viewPager = findViewById(R.id.main_viewpager);
        FragmentPagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        DatabaseReference userInfo = databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("SchoolLoopInfo");
        userInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.child("SchoolloopName").getValue(String.class).toString();
                String pass = dataSnapshot.child("SchoolloopPass").getValue(String.class).toString();
                new SchoolloopDataRetrieve(userName,pass).execute("https://www.hhs.fuhsd.org/");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createNotificiation() {
        long t = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        if (t <= calendar.getTimeInMillis()) {
            Intent intent1 = new Intent(MainActivity.this, NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
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

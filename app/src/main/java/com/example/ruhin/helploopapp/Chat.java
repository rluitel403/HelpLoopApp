package com.example.ruhin.helploopapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by rubin
 * version 2
 * this class shows the chat messages for a chat they entered and allows the user to send messages
 */
public class Chat extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private String chatName;
    private EditText input;
    private ImageView send_btn;
    private String name;
    FirebaseRecyclerAdapter<ChatInfo,ChatMessageViewHolder> firebaseRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatName = getIntent().getStringExtra("ChatName");
        send_btn = findViewById(R.id.chat_send_btn);
        toolbar = findViewById(R.id.chat_room_toolbar);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        name = "";
        input = findViewById(R.id.chat_input);
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.chat_recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(chatName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mssg = input.getText().toString();
               databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("SchoolLoopInfo").addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       name = dataSnapshot.child("Name").getValue(String.class);
                       Log.d("my name", "onDataChange: " + name);
                       SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss");
                       String formattedDate = dateFormat.format(new Date()).toString();
                       ChatInfo chatInfo = new ChatInfo(mssg,name, formattedDate);
                       databaseReference.child("Chats").child(chatName).push().setValue(chatInfo);
                       input.setText("");
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
            }
        });
        retrieveChatMssg();

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    private void retrieveChatMssg() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Chats").child(chatName);
        FirebaseRecyclerOptions<ChatInfo> options =
                new FirebaseRecyclerOptions.Builder<ChatInfo>()
                        .setQuery(query, ChatInfo.class)
                        .build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ChatInfo, ChatMessageViewHolder>
                (options) {
            @Override
            protected void onBindViewHolder(@NonNull ChatMessageViewHolder holder, int position, @NonNull ChatInfo model) {
                holder.setChatMssg(model.getMssg());
                holder.setMessengerName(model.getName());
                holder.setMssgDate(model.getTimeStamp());
            }

            @NonNull
            @Override
            public ChatMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_row_chat_mssg, parent, false);
                Log.d("here", "onCreateViewHolder: " + " i am here");
                return new ChatMessageViewHolder(view);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.homeAsUp){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class ChatMessageViewHolder extends RecyclerView.ViewHolder {

        View view;
        public ChatMessageViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;

        }
        public void setChatMssg(String mssg){
            TextView text = view.findViewById(R.id.chat_mssg);
            text.setText(mssg);
        }
        public void setMssgDate(String date){
            TextView text = view.findViewById(R.id.chat_mssg_date);
            text.setText(date);
        }
        public void setMessengerName(String name){
            TextView text = view.findViewById(R.id.messenger_name);
            text.setText(name);
        }
    }
}

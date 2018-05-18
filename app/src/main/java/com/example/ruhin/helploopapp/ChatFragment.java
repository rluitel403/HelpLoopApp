package com.example.ruhin.helploopapp;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Random;


/**
 * created by: rubin
 * version: 2
 * This is the chatfragment which displays all the chats that the user can enter
 */
public class ChatFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private LinkedHashSet<String> classes;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<ChatName,ChatViewHolder> firebaseRecyclerAdapter;
    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        classes = new LinkedHashSet<>();
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        recyclerView = view.findViewById(R.id.chatnames_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("Assignments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss");
                    String formattedDate = dateFormat.format(new Date()).toString();
                    Assignment retrievedAssignment = dataSnapshot.getValue(Assignment.class);
                    String uniqueKey  = ("Welcome to " + retrievedAssignment.getAssignmentClass() + " Chat").hashCode() + "";
                    ChatInfo chatInfo = new ChatInfo("Welcome to " + retrievedAssignment.getAssignmentClass() + " Chat","HelpLoop", formattedDate);
                    reference.child("Chats").child(retrievedAssignment.getAssignmentClass()).child(uniqueKey).setValue(chatInfo);
                    ChatName name = new ChatName(retrievedAssignment.getAssignmentClass());
                    reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("Chats").child(retrievedAssignment.getAssignmentClass().hashCode()+"").setValue(name);
             }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    retrieveChatNames();
    return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();

    }

    private void retrieveChatNames() {
        FirebaseAuth user = FirebaseAuth.getInstance();
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users").child(user.getCurrentUser().getUid()).child("Chats");
        FirebaseRecyclerOptions<ChatName> options =
                new FirebaseRecyclerOptions.Builder<ChatName>()
                        .setQuery(query, ChatName.class)
                        .build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ChatName, ChatViewHolder>
                (options) {
            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull final ChatName model) {
                    holder.setChatName(model.getName());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent toChat = new Intent(getActivity(),Chat.class);
                        toChat.putExtra("ChatName",model.getName());
                        startActivity(toChat);
                    }
                });
            }

            @NonNull
            @Override
            public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.singlerow_chat_name, parent, false);
                return new ChatViewHolder(view);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    public static class ChatViewHolder extends RecyclerView.ViewHolder{
        View view;
        public ChatViewHolder(View itemView) {
            super(itemView);
            view = itemView;

        }
        public void setChatName(String name){
            TextView textView =  view.findViewById(R.id.chat_name);
            textView.setText(name);
        }
    }

}

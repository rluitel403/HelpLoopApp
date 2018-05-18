package com.example.ruhin.helploopapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * Created by rubin
 * version 2
 * this class retrieves all the assignments from firebasedatabase using FirebaseUi for database and displays it into the assignmentfragment
 */
public class AssignmentFragment extends Fragment {

    private ImageView makeAssignBtn;
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    FirebaseRecyclerAdapter<Assignment,AssignmentViewHolder> firebaseRecyclerAdapter;
    public AssignmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);
        reference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        makeAssignBtn = view.findViewById(R.id.make_assign_btn);
        makeAssignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMakeAssignment = new Intent(getActivity(), CreateAssignmentActivity.class);
                startActivity(toMakeAssignment);
            }
        });
        recyclerView = view.findViewById(R.id.assignment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        retrieveFirebaseAssignments();
        return view;
    }

    private void retrieveFirebaseAssignments() {
        FirebaseAuth user = FirebaseAuth.getInstance();
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users").child(user.getCurrentUser().getUid()).child("Assignments");
        FirebaseRecyclerOptions<Assignment> options =
                new FirebaseRecyclerOptions.Builder<Assignment>()
                        .setQuery(query, Assignment.class)
                        .build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Assignment, AssignmentViewHolder>
                (options) {
            @Override
            protected void onBindViewHolder(@NonNull final AssignmentViewHolder holder, int position, @NonNull final Assignment model) {
                    holder.setAssignemntInfo(model.getAssignmentInfo());
                    holder.setAssignmentClass(model.getAssignmentClass());
                    holder.setAssignmentDate(model.getDate());
                    holder.view.findViewById(R.id.item_check_box).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                            alertDialog.setTitle("Delete assignment");
                            alertDialog.setMessage("Are you sure?");
                            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("CompletedAssignments").child(model.getAssignmentInfo().hashCode() + "").setValue(model);
                                    reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("Assignments").child(model.getAssignmentInfo().hashCode() + "").removeValue();
                                    CheckBox checkBox = holder.view.findViewById(R.id.item_check_box);
                                    checkBox.setChecked(false);
                                }
                            });
                            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    CheckBox checkBox = holder.view.findViewById(R.id.item_check_box);
                                    checkBox.setChecked(false);
                                }
                            });
                            AlertDialog dialog = alertDialog.create();
                            dialog.show();
                        }
                    });

            }

            @NonNull
            @Override
            public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.singlerow_assignment, parent, false);
                Log.d("here", "onCreateViewHolder: " + " i am here");
                return new AssignmentViewHolder(view);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder{
        View view;
        public AssignmentViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }
        public void setAssignemntInfo(String info){
            TextView text = view.findViewById(R.id.assignemnt_info);
            text.setText(info);
        }
        public void setAssignmentClass(String assignClass){
            TextView text = view.findViewById(R.id.assignemnt_class);
            text.setText(assignClass);
        }
        public void setAssignmentDate(String date){
            TextView text = view.findViewById(R.id.assignment_date);
            text.setText(date);
        }

    }
}

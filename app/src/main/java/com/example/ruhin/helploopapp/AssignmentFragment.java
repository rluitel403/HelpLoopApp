package com.example.ruhin.helploopapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AssignmentFragment extends Fragment {

    private ImageView makeAssignBtn;

    public AssignmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);
        makeAssignBtn = view.findViewById(R.id.make_assign_btn);
        makeAssignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMakeAssignment = new Intent(getActivity(), CreateAssignmentActivity.class);
                startActivity(toMakeAssignment);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}

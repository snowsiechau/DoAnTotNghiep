package com.example.chaut.hieuthuoc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chaut.hieuthuoc.adapter.FirebaseHolder;
import com.example.chaut.hieuthuoc.adapter.ModelAdapter;
import com.example.chaut.hieuthuoc.model.Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    private static final String TAG = "TAG";
    private DatabaseReference myRef;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;


    ArrayList<Model> modelArrayList = new ArrayList<>();
    public static ArrayList<Model> searchList = new ArrayList<>();
    RecyclerView rcFirebase;
    ModelAdapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("phongkham");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Model model = postSnapshot.getValue(Model.class);
                    modelArrayList.add(model);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        rcFirebase = view.findViewById(R.id.rc_firebase);

        if (searchList.size() == 0) {
            adapter = new ModelAdapter(modelArrayList, getContext());
        }else {
            adapter = new ModelAdapter(searchList, getContext());
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rcFirebase.setLayoutManager(mLayoutManager);
        rcFirebase.setItemAnimator(new DefaultItemAnimator());
        rcFirebase.setAdapter(adapter);

      //  setUpFirebaseAdapter();
        // Inflate the layout for this fragment
        return view;
    }

    private void setUpFirebaseAdapter() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, FirebaseHolder>
                (Model.class, R.layout.item_adapter, FirebaseHolder.class, myRef
                ) {
            @Override
            protected void populateViewHolder(FirebaseHolder viewHolder, Model model, int position) {
                viewHolder.bindFirebase(model);
            }
        };

        rcFirebase.hasFixedSize();
        rcFirebase.setLayoutManager(new LinearLayoutManager(getContext()));
        rcFirebase.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        modelArrayList.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        firebaseRecyclerAdapter.cleanup();
    }
}

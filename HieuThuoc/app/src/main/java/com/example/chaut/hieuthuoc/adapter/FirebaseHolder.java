package com.example.chaut.hieuthuoc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.chaut.hieuthuoc.R;
import com.example.chaut.hieuthuoc.SearchActivity;
import com.example.chaut.hieuthuoc.model.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by chaut on 4/25/2018.
 */

public class FirebaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    Context mContext;

    public FirebaseHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindFirebase(Model model){
        TextView ten = itemView.findViewById(R.id.tv_ten);
        TextView diachi = itemView.findViewById(R.id.tv_diachi);
        TextView chuyenkhoa = itemView.findViewById(R.id.tv_chuyenkhoa);
        TextView bacsi = itemView.findViewById(R.id.tv_bacsi);

        ten.setText(model.getTen());
        diachi.setText("Adr: " +model.getDiachi());
        chuyenkhoa.setText(model.getChuyenkhoa());
        bacsi.setText("Dr: " + model.getBacsi());
    }

    @Override
    public void onClick(View v) {
        Log.d("quy", "onClick: " + getLayoutPosition());
        final ArrayList<Model> restaurants = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("phongkham");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    restaurants.add(snapshot.getValue(Model.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, SearchActivity.class);
                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

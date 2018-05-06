package com.example.chaut.hieuthuoc;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.chaut.hieuthuoc.adapter.CommentAdapter;
import com.example.chaut.hieuthuoc.adapter.FirebaseHolder;
import com.example.chaut.hieuthuoc.model.CommentModel;
import com.example.chaut.hieuthuoc.model.Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeatilActivity extends AppCompatActivity {
    TextView tvTen, tvBacsi, tvChuyenkhoa, tvSDT, tvDiachi;

    private static final String TAG = "TAG";
    private DatabaseReference myRef;

    FirebaseAuth firebaseAuth;

    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    RecyclerView rcFirebase;

    String sdt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatil);

        firebaseAuth = FirebaseAuth.getInstance();

        Log.d(TAG, "onCreate: " +  firebaseAuth.getCurrentUser().getEmail() + firebaseAuth.getCurrentUser().getPhotoUrl());
        firebaseAuth.getCurrentUser().getPhotoUrl();

        tvTen = findViewById(R.id.tv_ten);
        tvBacsi = findViewById(R.id.tv_bacsi);
        tvChuyenkhoa = findViewById(R.id.tv_chuyenkhoa);
        tvDiachi = findViewById(R.id.tv_diachi);
        tvSDT = findViewById(R.id.tv_sdt);

        sdt = getIntent().getStringExtra("SDT");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("phongkham").child(sdt);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Model model = dataSnapshot.getValue(Model.class);
                Log.d(TAG, "onDataChange: " + model.getBacsi());
                tvTen.setText(model.getTen());
                tvBacsi.setText(model.getBacsi());
                tvChuyenkhoa.setText(model.getChuyenkhoa());
                tvDiachi.setText(model.getDiachi());
                tvSDT.setText(model.getSdt());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rcFirebase = findViewById(R.id.rc_commentFirebase);
        myRef = database.getReference().child("binhluan").child(sdt);
        setUpFirebaseAdapter();

        FloatingActionButton button = findViewById(R.id.bt_comment);
        button.setImageResource(R.drawable.ic_comment_black_24dp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeatilActivity.this, CommentActivity.class);
                intent.putExtra("SDTDT", sdt);
                startActivity(intent);
            }
        });
    }


    private void setUpFirebaseAdapter() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CommentModel, CommentAdapter>
                (CommentModel.class, R.layout.item_comment, CommentAdapter.class, myRef
                ) {
            @Override
            protected void populateViewHolder(CommentAdapter viewHolder, CommentModel model, int position) {
                viewHolder.bindFirebase(model);
            }
        };

        rcFirebase.hasFixedSize();
        rcFirebase.setLayoutManager(new LinearLayoutManager(this));
        rcFirebase.setAdapter(firebaseRecyclerAdapter);
    }

}

package com.example.chaut.hieuthuoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.chaut.hieuthuoc.model.CommentModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommentActivity extends AppCompatActivity {
    EditText etComment;
    RatingBar rtRating;
    Button btComment;
    String sdt;

    private DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);




        etComment = findViewById(R.id.et_comment);
        btComment = findViewById(R.id.bt_comment);
        rtRating = findViewById(R.id.rb_rating);

        btComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etComment.getText().toString().equals("")){
                    Toast.makeText(CommentActivity.this, "Fill in comment box", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    CommentModel model = new CommentModel();
                    if (FirebaseAuth.getInstance().getCurrentUser().getDisplayName() == null){
                        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        String name = email.split("@")[0];
                        model.setTieude(name);
                    }else {
                        model.setTieude(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    }
                    model.setNoidung(etComment.getText().toString());
                    model.setDanhgia(rtRating.getRating());

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
                    String strDate = mdformat.format(calendar.getTime());

                    model.setDate(strDate);

                    sdt = getIntent().getStringExtra("SDTDT");
                    if (FirebaseAuth.getInstance().getCurrentUser().getDisplayName() == null){
                        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        String name = email.split("@")[0];
                        myRef = database.getReference().child("binhluan").child(sdt).child(name);

                    }else {
                        myRef = database.getReference().child("binhluan").child(sdt).child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    }
                    myRef.setValue(model);
                    CommentActivity.super.onBackPressed();
                }
            }
        });

    }
}

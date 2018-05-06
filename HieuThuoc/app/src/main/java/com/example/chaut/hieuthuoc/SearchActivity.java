package com.example.chaut.hieuthuoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chaut.hieuthuoc.model.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.chaut.hieuthuoc.ListFragment.searchList;

public class SearchActivity extends AppCompatActivity {
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("phongkham");

        final EditText etSearch = findViewById(R.id.et_search);
        etSearch.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        etSearch.onEditorAction(EditorInfo.IME_ACTION_SEARCH);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    Query query = myRef.orderByChild("chuyenkhoa").startAt(etSearch.getText().toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                searchList.clear();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                    Model model = postSnapshot.getValue(Model.class);
                                    searchList.add(model);
                                }

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("list", searchList);
                                intent.putExtra("BUNDLE", bundle);
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                return false;
            }
        });


    }
}

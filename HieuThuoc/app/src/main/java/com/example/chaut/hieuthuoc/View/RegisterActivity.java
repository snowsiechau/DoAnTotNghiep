package com.example.chaut.hieuthuoc.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaut.hieuthuoc.MainActivity;
import com.example.chaut.hieuthuoc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText etName, etPassword, etRePassword;
    Button btRegister;
    TextView tvLogin;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        etName = findViewById(R.id.input_name);
        etPassword = findViewById(R.id.input_email);
        etRePassword = findViewById(R.id.input_password);
        btRegister = findViewById(R.id.btn_signup);
        tvLogin = findViewById(R.id.link_login);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Loading...");
                progressDialog.setIndeterminate(true);
                progressDialog.show();

                String name = etName.getText().toString();
                String password = etPassword.getText().toString();
                String rePassword = etRePassword.getText().toString();

                if (name.trim().length() == 0 ){
                    Toast.makeText(RegisterActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
                }else if (password.trim().length() == 0 ){
                    Toast.makeText(RegisterActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
                }else if (rePassword.trim().length() == 0){
                    Toast.makeText(RegisterActivity.this, "Enter your confirm password", Toast.LENGTH_SHORT).show();
                }else if (!password.equals(rePassword)){
                    Toast.makeText(RegisterActivity.this, "Password not exact", Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseAuth.createUserWithEmailAndPassword(name, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Email or password not true", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}

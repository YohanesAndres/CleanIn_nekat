package com.Nekat.CleanIn.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.Nekat.CleanIn.Firebase.Model.User;
import com.Nekat.CleanIn.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRoot, mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRoot = mDatabase.getReference();


        binding.ibBackRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = binding.etEmail.getText().toString();
                String password = binding.etPass.getText().toString();
                String nomor = binding.etNo.getText().toString();
                final String name = binding.etName.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    binding.etEmail.setError("Enter your email address!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    binding.etPass.setError("Enter your password!");
                    return;
                }
                if (TextUtils.isEmpty(nomor)) {
                    binding.etNo.setError("Enter your number phone");
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    binding.etName.setError("Enter your full name!");
                    return;
                }
                if (password.length() < 6) {
                    binding.etPass.setError("Password too short, enter minimum 6 characters!");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
//                                    Utilities.getmRetrofit().create(APIService.class).storeUser(name,email,null,nomor).enqueue(new Callback<APIJson<List<UserAPI>>>() {
//                                        @Override
//                                        public void onResponse(Call<APIJson<List<UserAPI>>> call, Response<APIJson<List<UserAPI>>> response) {

//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<APIJson<List<UserAPI>>> call, Throwable t) {
//
//                                        }
//                                    });
//
                                    User user = new User(email,name,nomor,null);
                                    String userId = task.getResult().getUser().getUid();
                                    mRef = mRoot.child("users").child(userId);
                                    mRef.setValue(user);
                                    //Toast.makeText(RegisterActivity.this, "Register successfully!", Toast.LENGTH_SHORT).show();
                                    FancyToast.makeText(RegisterActivity.this,"Register successfully!",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    //Toast.makeText(RegisterActivity.this, "Sign Up failed!", Toast.LENGTH_SHORT).show();
                                    FancyToast.makeText(RegisterActivity.this,"Sign Up failed!",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                                    Log.d("tesr", "onComplete: " + task.getException());
                                }
                            }
                        });

            }
        });


    }
}
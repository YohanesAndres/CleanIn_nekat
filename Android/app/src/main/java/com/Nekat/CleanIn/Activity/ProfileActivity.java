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
import com.Nekat.CleanIn.R;
import com.Nekat.CleanIn.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRoot, mRef;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRoot = mDatabase.getReference();

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mRoot = FirebaseDatabase.getInstance().getReference();
        mRef = mRoot.child("users").child(userId);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User user = dataSnapshot.getValue(User.class);
                binding.etNama.setText(user.getName());
                binding.etNohp.setText(user.getNomor());
                binding.etAlamat.setText(user.getAlamat());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


        binding.btnUpdateprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = binding.etNama.getText().toString();
                String nomorhp = binding.etNohp.getText().toString();
                final String alamat = binding.etAlamat.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    binding.etNama.setError("Enter your new name!");
                    return;
                }
                if (TextUtils.isEmpty(nomorhp)) {
                    binding.etNohp.setError("Enter your new number phone");
                    return;
                }
                if (TextUtils.isEmpty(alamat)) {
                    binding.etAlamat.setError("Enter your new adrres!");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("nomor", nomorhp);
                map.put("alamat", alamat);

                FirebaseDatabase.getInstance().getReference().child("users").child(userId).updateChildren(map, (error, ref) -> {
                    Toast.makeText(ProfileActivity.this, "Berhasil di update", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                });

            }
        });
    }
}
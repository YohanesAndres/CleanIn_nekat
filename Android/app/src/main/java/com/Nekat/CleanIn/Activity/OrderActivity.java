package com.Nekat.CleanIn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.Nekat.CleanIn.API.APIService;
import com.Nekat.CleanIn.API.Model.APIJson;
import com.Nekat.CleanIn.API.Model.Order;
import com.Nekat.CleanIn.Adapter.ItemOrderAdapter;
import com.Nekat.CleanIn.Firebase.Model.User;
import com.Nekat.CleanIn.ItemClickListener;
import com.Nekat.CleanIn.R;
import com.Nekat.CleanIn.Utilities;
import com.Nekat.CleanIn.databinding.ActivityMainBinding;
import com.Nekat.CleanIn.databinding.ActivityOrderBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mRoot, mRef;
    private ActivityOrderBinding binding;
    private String userId, beratString;
    private int berat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        int id_paket = getIntent().getIntExtra("id_paket", 1);

        if (id_paket == 1) {
            binding.tvPaket.setText("Reguler");
            binding.tvHarga.setText("Rp. 7000/kg");
        } else if (id_paket == 2){
            binding.tvPaket.setText("Take Home");
            binding.tvHarga.setText("Rp. 10.000/kg");
        } else {
            binding.tvPaket.setText("Express");
            binding.tvHarga.setText("Rp. 15.0000/kg");
        }

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
                binding.tvNama.setText(user.getName());
                binding.tvAlamat.setText(user.getAlamat());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        binding.btnOrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.tvAlamat.getText().toString().isEmpty()){
                    Toast.makeText(OrderActivity.this, "Harap lengkapi data diri terlebih dahulu", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OrderActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else{
                    beratString = binding.etBerat.getText().toString();

                    mAuth = FirebaseAuth.getInstance();
                    userId = mAuth.getCurrentUser().getUid();
                    mRoot = FirebaseDatabase.getInstance().getReference();
                    mRef = mRoot.child("users").child(userId);
                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);

                            Utilities.getmRetrofit().create(APIService.class).storeOrder(userId,id_paket,user.getAlamat(),beratString).enqueue(new Callback<APIJson<Order>>() {
                                @Override
                                public void onResponse(Call<APIJson<Order>> call, Response<APIJson<Order>> response) {
                                    Toast.makeText(OrderActivity.this,"Berhasil", Toast.LENGTH_SHORT);
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<APIJson<Order>> call, Throwable t) {
                                    Toast.makeText(OrderActivity.this,"Gagal", Toast.LENGTH_SHORT);
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w("TAG", "Failed to read value.", error.toException());
                        }
                    });
                }

            }
        });

    }
}
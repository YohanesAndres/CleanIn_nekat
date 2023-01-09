package com.Nekat.CleanIn.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.Nekat.CleanIn.API.APIService;
import com.Nekat.CleanIn.API.Model.APIJson;
import com.Nekat.CleanIn.API.Model.Order;
import com.Nekat.CleanIn.Firebase.Model.User;
import com.Nekat.CleanIn.Adapter.ItemOrderAdapter;
import com.Nekat.CleanIn.ItemClickListener;
import com.Nekat.CleanIn.Utilities;
import com.Nekat.CleanIn.databinding.ActivityMainBinding;
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

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String mEmail;
    private DatabaseReference mRoot, mRef;
    private ActivityMainBinding binding;
    private String userId;

    private ItemOrderAdapter orderAdapter;
    private ItemClickListener<Order> itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


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
                binding.mainTvName.setText(user.getName());
                binding.mainTvTelp.setText(user.getNomor());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        mEmail = mAuth.getCurrentUser().getEmail();

        if (mEmail.equals("admin@gmail.com")) {
            Toast.makeText(MainActivity.this, "Welcome Admin!", Toast.LENGTH_SHORT).show();
        }

        binding.mainClProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        binding.mainClReguler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEmail.equals("admin@gmail.com")) {
                    Intent intent = new Intent(MainActivity.this, OrderListActivity.class);
                    intent.putExtra("id_paket", 1);
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                    intent.putExtra("id_paket", 1);
                    startActivity(intent);
                    finish();
                }

            }
        });
        binding.mainClTakeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEmail.equals("admin@gmail.com")) {
                    Intent intent = new Intent(MainActivity.this, OrderListActivity.class);
                    intent.putExtra("id_paket", 2);
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                    intent.putExtra("id_paket", 2);
                    startActivity(intent);
                    finish();
                }

            }
        });
        binding.mainClExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEmail.equals("admin@gmail.com")) {
                    Intent intent = new Intent(MainActivity.this, OrderListActivity.class);
                    intent.putExtra("id_paket", 3);
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                    intent.putExtra("id_paket", 3);
                    startActivity(intent);
                    finish();
                }
            }
        });
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mEmail.equals("admin@gmail.com")){
            Utilities.getmRetrofit().create(APIService.class).getallorder().enqueue(new Callback<APIJson<List<Order>>>() {
                @Override
                public void onResponse(Call<APIJson<List<Order>>> call, Response<APIJson<List<Order>>> response) {
                    if(response.isSuccessful()){
                        itemClickListener = new ItemClickListener<Order>() {
                            @Override
                            public void itemClick(Order data, int position) {
                                Intent intent = new Intent(MainActivity.this, NewInActivity.class);
                                intent.putExtra("order", data);
                                startActivity(intent);
                            }
                        };
                        orderAdapter = new ItemOrderAdapter(itemClickListener);
                        orderAdapter.setItemOrderAdapter(response.body());
                        RecyclerView rv = binding.rvOrderListMain;
                        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        rv.setAdapter(orderAdapter);
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<APIJson<List<Order>>> call, Throwable t) {

                }
            });
        } else {
            Utilities.getmRetrofit().create(APIService.class).getOrderByIdUser(userId).enqueue(new Callback<APIJson<List<Order>>>() {
                @Override
                public void onResponse(Call<APIJson<List<Order>>> call, Response<APIJson<List<Order>>> response) {
                    if(response.isSuccessful()){
                        itemClickListener = new ItemClickListener<Order>() {
                            @Override
                            public void itemClick(Order data, int position) {
                                Intent intent = new Intent(MainActivity.this, OrderDetailActivity.class);
                                intent.putExtra(OrderDetailActivity.ORDER_ID, data);
                                startActivity(intent);
                            }
                        };
                        orderAdapter = new ItemOrderAdapter(itemClickListener);
                        orderAdapter.setItemOrderAdapter(response.body());
                        RecyclerView rv = binding.rvOrderListMain;
                        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        rv.setAdapter(orderAdapter);
                    } else {
                        Log.d("TAG", "onResponse ===============================================: GAGAL");

                    }

                }

                @Override
                public void onFailure(Call<APIJson<List<Order>>> call, Throwable t) {
                    Log.d("TAG", "onResponse ====================================================================: GAGAL ON FAILURE");
                }
            });
        }
        Log.d("TAG", "onCreate =============================================================: "+mEmail);
    }
}
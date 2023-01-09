package com.Nekat.CleanIn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.Nekat.CleanIn.API.APIService;
import com.Nekat.CleanIn.API.Model.APIJson;
import com.Nekat.CleanIn.API.Model.Order;
import com.Nekat.CleanIn.Adapter.ItemOrderAdapter;
import com.Nekat.CleanIn.ItemClickListener;
import com.Nekat.CleanIn.R;
import com.Nekat.CleanIn.Utilities;
import com.Nekat.CleanIn.databinding.ActivityOrderBinding;
import com.Nekat.CleanIn.databinding.ActivityOrderListBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListActivity extends AppCompatActivity {

    public static final String TYPE_DATA = "TYPE_DATA";
    private ActivityOrderListBinding binding;
    private ItemOrderAdapter orderAdapter;
    private ItemClickListener<Order> itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Utilities.getmRetrofit().create(APIService.class).getallorder().enqueue(new Callback<APIJson<List<Order>>>() {
            @Override
            public void onResponse(Call<APIJson<List<Order>>> call, Response<APIJson<List<Order>>> response) {
                Log.d("TAG", "onResponse ====================================================================: "+response.body().getData());
                if(response.isSuccessful()){
                    itemClickListener = new ItemClickListener<Order>() {
                        @Override
                        public void itemClick(Order data, int position) {
                            Intent intent = new Intent(OrderListActivity.this, NewInActivity.class);
                            intent.putExtra("order", data);
                            startActivity(intent);
                        }
                    };
                    orderAdapter = new ItemOrderAdapter(itemClickListener);
                    orderAdapter.setItemOrderAdapter(response.body());
                    RecyclerView rv = binding.rvOrderList;
                    rv.setLayoutManager(new LinearLayoutManager(OrderListActivity.this));
                    rv.setAdapter(orderAdapter);
                } else {
                    Log.d("TAG", "onResponse ===============================================: GAGAL");

                }
            }

            @Override
            public void onFailure(Call<APIJson<List<Order>>> call, Throwable t) {
                Toast.makeText(OrderListActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
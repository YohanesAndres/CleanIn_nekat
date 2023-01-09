package com.Nekat.CleanIn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.Nekat.CleanIn.API.APIService;
import com.Nekat.CleanIn.API.Model.APIJson;
import com.Nekat.CleanIn.API.Model.Order;
import com.Nekat.CleanIn.R;
import com.Nekat.CleanIn.Utilities;
import com.Nekat.CleanIn.databinding.ActivityOrderBuktiBinding;
import com.Nekat.CleanIn.databinding.ActivityOrderDetailBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderBuktiActivity extends AppCompatActivity {
    private ActivityOrderBuktiBinding binding;

    public static final String ORDER_ID = "ORDER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrderBuktiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Utilities.getmRetrofit().create(APIService.class).getOrderById(ORDER_ID).enqueue(new Callback<APIJson<Order>>() {
            @Override
            public void onResponse(Call<APIJson<Order>> call, Response<APIJson<Order>> response) {
                Toast.makeText(OrderBuktiActivity.this,"Berhasil", Toast.LENGTH_SHORT);
                Order order = response.body().getData();
                binding.tvAlamatbukti.setText(order.getAlamat());
                binding.tvBeratbukti.setText(order.getBerat());
                int hargaTotal = Integer.parseInt(order.getTotal_harga());
                int berat = Integer.parseInt(order.getBerat());
                int harga = hargaTotal/berat;
                binding.tvHargabukti.setText(harga);
                int orderpaket= Integer.parseInt(order.getId_paket());

                if (orderpaket == 1) {
                    binding.tvPaketbukti.setText("Reguler");
                } else if (orderpaket == 2){
                    binding.tvPaketbukti.setText("Take Home");
                } else {
                    binding.tvPaketbukti.setText("Express");
                }
            }

            @Override
            public void onFailure(Call<APIJson<Order>> call, Throwable t) {
                Toast.makeText(OrderBuktiActivity.this,"Gagal", Toast.LENGTH_SHORT);

            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderBuktiActivity.this, OrderDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
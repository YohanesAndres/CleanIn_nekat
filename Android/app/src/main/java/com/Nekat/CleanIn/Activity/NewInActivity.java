package com.Nekat.CleanIn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.Nekat.CleanIn.API.APIService;
import com.Nekat.CleanIn.API.Model.APIJson;
import com.Nekat.CleanIn.API.Model.Order;
import com.Nekat.CleanIn.API.Model.UserAPI;
import com.Nekat.CleanIn.R;
import com.Nekat.CleanIn.Utilities;
import com.Nekat.CleanIn.databinding.ActivityNewInBinding;
import com.Nekat.CleanIn.databinding.ActivityOrderBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewInActivity extends AppCompatActivity {

    private ActivityNewInBinding binding;
    private String statusUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Order order = getIntent().getParcelableExtra("order");

        int orderpaket= Integer.parseInt(order.getId_paket());

        if (orderpaket == 1) {
            binding.tvPaketNewin.setText("Reguler");
        } else if (orderpaket == 2){
            binding.tvPaketNewin.setText("Take Home");
        } else {
            binding.tvPaketNewin.setText("Express");
        }

        binding.tvAlamatNewin.setText(order.getAlamat());
        binding.tvBeratNewin.setText(order.getBerat());
        int hargaTotal = Integer.parseInt(order.getTotal_harga());
        int berat = Integer.parseInt(order.getBerat());
        int harga = hargaTotal/berat;
        binding.tvHargaNewin.setText(Integer.toString(harga));
        binding.tvIdNewin.setText(order.getId());

        if (order.getStatus().equals("1")) {
            binding.tvStatusNewin.setText("New In");
            statusUpdate = "2";
        } else if (order.getStatus().equals("2")){
            binding.tvStatusNewin.setText("On Process");
            statusUpdate = "3";
        } else {
            binding.tvStatusNewin.setText("Finished");
            binding.btnProcessOrder.setVisibility(View.GONE);
        }

        Utilities.getmRetrofit().create(APIService.class).getOrderById(order.getId()).enqueue(new Callback<APIJson<Order>>() {
            @Override
            public void onResponse(Call<APIJson<Order>> call, Response<APIJson<Order>> response) {
                binding.tvNamaNewin.setText(response.body().getData().getName_user());
            }

            @Override
            public void onFailure(Call<APIJson<Order>> call, Throwable t) {

            }
        });

        binding.btnProcessOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewInActivity.this,"Button di tekan", Toast.LENGTH_SHORT);
                Utilities.getmRetrofit().create(APIService.class).updateOrder(order.getId(),statusUpdate).enqueue(new Callback<APIJson<Order>>() {
                    @Override
                    public void onResponse(Call<APIJson<Order>> call, Response<APIJson<Order>> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(NewInActivity.this,"Berhasil", Toast.LENGTH_SHORT);
                            finish();
                        } else {
                            Toast.makeText(NewInActivity.this,"Gagal", Toast.LENGTH_SHORT);

                        }

                    }

                    @Override
                    public void onFailure(Call<APIJson<Order>> call, Throwable t) {
                        Toast.makeText(NewInActivity.this,"Gagal", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }
}
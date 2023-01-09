package com.Nekat.CleanIn.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.Nekat.CleanIn.API.APIService;
import com.Nekat.CleanIn.API.Model.APIJson;
import com.Nekat.CleanIn.API.Model.Order;
import com.Nekat.CleanIn.Firebase.Model.User;
import com.Nekat.CleanIn.R;
import com.Nekat.CleanIn.Utilities;
import com.Nekat.CleanIn.databinding.ActivityOrderDetailBinding;
import com.Nekat.CleanIn.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mRoot, mRef;
    private ActivityOrderDetailBinding binding;


    public static final String ORDER_ID = "ORDER_ID";
    private String mEmail, userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Order order = getIntent().getParcelableExtra(ORDER_ID);

        int orderpaket= Integer.parseInt(order.getId_paket());

        if (orderpaket == 1) {
            binding.tvPaketdetail.setText("Reguler");
        } else if (orderpaket == 2){
            binding.tvPaketdetail.setText("Take Home");
        } else {
            binding.tvPaketdetail.setText("Express");
        }

        binding.tvAlamatdetail.setText(order.getAlamat());
        binding.tvBeratdetail.setText(order.getBerat());
        int hargaTotal = Integer.parseInt(order.getTotal_harga());
        int berat = Integer.parseInt(order.getBerat());
        int harga = hargaTotal/berat;
        binding.tvHargadetail.setText(Integer.toString(harga));
        binding.tvTotalHarga.setText(order.getTotal_harga());

        binding.btnOrderdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Utilities.getmRetrofit().create(APIService.class).getOrderById(order.getId()).enqueue(new Callback<APIJson<Order>>() {
            @Override
            public void onResponse(Call<APIJson<Order>> call, Response<APIJson<Order>> response) {
                binding.tvNamadetail.setText(response.body().getData().getName_user());
            }

            @Override
            public void onFailure(Call<APIJson<Order>> call, Throwable t) {

            }
        });

    }

//    private void putImageInStorage(StorageReference storageReference, Uri uri, final String key) {
//        storageReference.putFile(uri).addOnCompleteListener(OrderDetailActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                if (task.isSuccessful()) {
//                    task.getResult().getMetadata().getReference().getDownloadUrl()
//                            .addOnCompleteListener(OrderDetailActivity.this, new OnCompleteListener<Uri>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Uri> task) {
//                                    if (task.isSuccessful()) {
//                                        ChatMessage chatMessage = new ChatMessage(null, userId, task.getResult().toString());
//                                        mRoot.child("messages").child(key).setValue(chatMessage);
//
//                                        Data data = new Data(mUsername, "Image Message", userId, task.getResult().toString());
//                                        Sender sender = new Sender(data, "/topics/messages");
//                                        sendNotification(sender);
//                                    }
//                                }
//                            });
//                } else {
//                    Log.w(TAG, "Image upload task failed!", task.getException());
//                }
//            }
//        });
//    }

}
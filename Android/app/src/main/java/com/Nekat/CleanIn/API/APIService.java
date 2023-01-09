package com.Nekat.CleanIn.API;

import com.Nekat.CleanIn.API.Model.APIJson;
import com.Nekat.CleanIn.API.Model.Order;
import com.Nekat.CleanIn.API.Model.UserAPI;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @GET("user/index")
    Call<APIJson<List<UserAPI>>> getAllUser();

    @GET("user/show/{id}")
    Call<APIJson<UserAPI>> getUserById(@Path("id") String id);

    @FormUrlEncoded
    @POST("user/store")
    Call<APIJson<UserAPI>> storeUser(@Field("user_id") String user_id,
                                            @Field("name_user") String name_user,
                                           @Field("email") String email,
                                           @Field("alamat") String alamat,
                                           @Field("no_telp") String no_telp);

    @FormUrlEncoded
    @POST("user/update")
    Call<APIJson<UserAPI>> updateUser(
                                            @Field("name_user") String name_user,
                                            @Field("alamat") String alamat,
                                            @Field("no_telp") String no_telp);

    @GET("user/destroy")
    Call<APIJson<UserAPI>> deleteUser(@Path("id") String id);



    @GET("order/index")
    Call<APIJson<List<Order>>> getallorder();

    @GET("order/show/{id}")
    Call<APIJson<Order>> getOrderById(@Path("id") String id);

    @FormUrlEncoded
    @POST("order/store")
    Call<APIJson<Order>> storeOrder(@Field("id_user") String id_user,
                                           @Field("id_paket") int id_paket,
                                           @Field("alamat") String alamat,
                                           @Field("berat") String berat);
    @FormUrlEncoded
    @POST("order/update/{id}")
    Call<APIJson<Order>> updateOrder(@Path("id") String id,
                                            @Field("status") String status);

    @GET("order/destroy")
    Call<APIJson<Order>> deleteOrder(@Path("id") String id);

    //gek ni ubah lg be
    @GET("order/showByStatus/{status}")
    Call<APIJson<List<Order>>> getOrderByStatus(@Path("status") String status);

    @GET("order/getOrderByIdUser/{id_user}")
    Call<APIJson<List<Order>>> getOrderByIdUser(@Path("id_user") String id_user);
}

package com.Nekat.CleanIn.API.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Order  implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("id_paket")
    private String id_paket;
    @SerializedName("alamat")
    private  String alamat;
    @SerializedName("berat")
    private String berat;
    @SerializedName("status")
    private String status;
    @SerializedName("total_harga")
    private String total_harga;
    @SerializedName("name_user")
    private String name_user;

    protected Order(Parcel in) {
        id = in.readString();
        id_user = in.readString();
        id_paket = in.readString();
        alamat = in.readString();
        berat = in.readString();
        status = in.readString();
        total_harga = in.readString();
        name_user = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(id_user);
        dest.writeString(id_paket);
        dest.writeString(alamat);
        dest.writeString(berat);
        dest.writeString(status);
        dest.writeString(total_harga);
        dest.writeString(name_user);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_paket() {
        return id_paket;
    }

    public void setId_paket(String id_paket) {
        this.id_paket = id_paket;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public Order() {
    }

    public Order(String id, String id_user, String id_paket, String alamat, String berat, String status, String total_harga) {
        this.id = id;
        this.id_user = id_user;
        this.id_paket = id_paket;
        this.alamat = alamat;
        this.berat = berat;
        this.status = status;
        this.total_harga = total_harga;
    }

    public Order(String id, String id_user, String id_paket, String alamat, String berat, String status, String total_harga, String name_user) {
        this.id = id;
        this.id_user = id_user;
        this.id_paket = id_paket;
        this.alamat = alamat;
        this.berat = berat;
        this.status = status;
        this.total_harga = total_harga;
        this.name_user = name_user;
    }
}

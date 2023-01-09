package com.Nekat.CleanIn.Firebase.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String email;
    private String name;
    private  String nomor;
    private String alamat;

    public User(String email, String name, String nomor, String alamat) {
        this.email = email;
        this.name = name;
        this.nomor = nomor;
        this.alamat = alamat;
    }

    public User() {
    }

    protected User(Parcel in) {
        email = in.readString();
        name = in.readString();
        nomor = in.readString();
        alamat = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(nomor);
        parcel.writeString(alamat);
    }
}

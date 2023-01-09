package com.Nekat.CleanIn.API.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAPI implements Parcelable {
    @SerializedName("name_user")
    @Expose
    private String name_user;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("no_telp")
    @Expose
    private String no_telp;
    @SerializedName("email")
    @Expose
    private String email;

    public UserAPI() {
    }

    public UserAPI(String name_user, String alamat, String no_telp, String email) {
        this.name_user = name_user;
        this.alamat = alamat;
        this.no_telp = no_telp;
        this.email = email;
    }

    protected UserAPI(Parcel in) {
        name_user = in.readString();
        alamat = in.readString();
        no_telp = in.readString();
        email = in.readString();
    }

    public static final Creator<UserAPI> CREATOR = new Creator<UserAPI>() {
        @Override
        public UserAPI createFromParcel(Parcel in) {
            return new UserAPI(in);
        }

        @Override
        public UserAPI[] newArray(int size) {
            return new UserAPI[size];
        }
    };

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name_user);
        parcel.writeString(alamat);
        parcel.writeString(no_telp);
        parcel.writeString(email);
    }
}

package com.vkcrestore.api.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vkcrestore.api.Model.Userdetails;

public class LoginResponse {

    @SerializedName("userdetails")
    @Expose
    private Userdetails userdetails;

    public Userdetails getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(Userdetails userdetails) {
        this.userdetails = userdetails;
    }

}

package com.userticketingsystem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayank on 29/12/16.
 */

public class SignUp {
    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("profile_pic")
    @Expose
    String profilePic;

    public SignUp(String email, String name, String profilePic) {
        this.email = email;
        this.name = name;
        this.profilePic = profilePic;
    }
}

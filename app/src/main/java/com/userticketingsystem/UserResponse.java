package com.userticketingsystem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rohanarora on 22/12/16.
 * Retrofit response class for user
 */

public class UserResponse extends ApiResponse {
    @SerializedName("id")
    @Expose
    Integer id;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("profile_pic")
    @Expose
    String profilePic;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}

package com.hamz.sistemsarpras.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamz on 04/03/2018.
 */

public class Contact {

    @SerializedName("name")
    String name;
    @SerializedName("image")
    String image;
    @SerializedName("phone")
    String phone;

    public Contact() {
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getPhone() {
        return phone;
    }
}

package com.hamz.sistemsarpras.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamz on 08/03/2018.
 */

public class Borrowed {

    @SerializedName("id_pinjam")
    int id_pinjam;
    @SerializedName("image_projector")
    private String image_projector;
    @SerializedName("image_user")
    private String image_user;
    @SerializedName("name_user")
    private String name_user;
    @SerializedName("description")
    private String description;
    @SerializedName("date_request")
    private String date_borrow;

    public Borrowed() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public void setImage_user(String image_user) {
        this.image_user = image_user;
    }

    public void setImage_projector(String image_projector) {
        this.image_projector = image_projector;
    }

    public void setDate_borrow(String date_borrow) {
        this.date_borrow = date_borrow;
    }

    public void setId_pinjam(int id_pinjam) {
        this.id_pinjam = id_pinjam;
    }

    public String getImage_user() {
        return image_user;
    }

    public String getName_user() {
        return name_user;
    }

    public String getImage_projector() {
        return image_projector;
    }

    public int getId_pinjam() {
        return id_pinjam;
    }

    public String getDate_borrow() {
        return date_borrow;
    }


}

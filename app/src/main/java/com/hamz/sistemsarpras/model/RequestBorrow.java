package com.hamz.sistemsarpras.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamz on 08/03/2018.
 */

public class RequestBorrow {

    @SerializedName("id_pinjam")
    int id_request;
    @SerializedName("image_projector")
    private String image_projector;
    @SerializedName("image_user")
    private String image_user;
    @SerializedName("name_user")
    private String name_user;
    @SerializedName("description")
    private String description_borrow;
    @SerializedName("date_request")
    private String date_request;

    public RequestBorrow(){};

    public String getDate_request() {
        return date_request;
    }

    public void setDate_request(String date_request) {
        this.date_request = date_request;
    }

    public int getId_request() {
        return id_request;
    }

    public String getDescription_borrow() {
        return description_borrow;
    }

    public String getImage_projector() {
        return image_projector;
    }

    public String getImage_user() {
        return image_user;
    }

    public String getName_user() {
        return name_user;
    }

    public void setDescription_borrow(String description_borrow) {
        this.description_borrow = description_borrow;
    }

    public void setId_request(int id_request) {
        this.id_request = id_request;
    }

    public void setImage_projector(String image_projector) {
        this.image_projector = image_projector;
    }

    public void setImage_user(String image_user) {
        this.image_user = image_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }
}

package com.hamz.sistemsarpras.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamz on 07/03/2018.
 */

public class ProjectorUser {

    @SerializedName("id_projector")
    private Integer id_projector;
    @SerializedName("name")
    private String name_projector;
    @SerializedName("image")
    private String image_projector;
    @SerializedName("description")
    private String description;
    @SerializedName("created_at")
    private String create;
    @SerializedName("id_pinjam")
    private Integer id_pinjam;
    @SerializedName("name_borrow")
    private String name_borrow;

    public ProjectorUser() {}

    public String getName_borrow() {
        return name_borrow;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_projector() {
        return image_projector;
    }

    public Integer getId_pinjam() {
        return id_pinjam;
    }

    public Integer getId_projector() {
        return id_projector;
    }

    public String getCreate() {
        return create;
    }

    public String getName_projector() {
        return name_projector;
    }


    public void setName_borrow(String name_borrow) {
        this.name_borrow = name_borrow;
    }

    public void setId_pinjam(Integer id_pinjam) {
        this.id_pinjam = id_pinjam;
    }

    public void setImage_projector(String image_projector) {
        this.image_projector = image_projector;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public void setId_projector(Integer id_projector) {
        this.id_projector = id_projector;
    }

    public void setName_projector(String name_projector) {
        this.name_projector = name_projector;
    }
}

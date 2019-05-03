package com.hamz.sistemsarpras.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamz on 07/03/2018.
 */

public class Projector {

    @SerializedName("id_projector")
    Integer id_projector;
    @SerializedName("name")
    String name_projector;
    @SerializedName("image")
    private String image_projector;
    @SerializedName("description")
    private String description;
    @SerializedName("created_at")
    String create;

    public Projector() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_projector() {
        return image_projector;
    }

    public void setImage_projector(String image_projector) {
        this.image_projector = image_projector;
    }

    public Integer getId_projector() {
        return id_projector;
    }

    public String getName_projector() {
        return name_projector;
    }

    public String getCreate() {
        return create;
    }


    public void setId_projector(Integer id_projector) {
        this.id_projector = id_projector;
    }

    public void setCreate(String create) {
        this.create = create;
    }


    public void setName_projector(String name_projector) {
        this.name_projector = name_projector;
    }
}

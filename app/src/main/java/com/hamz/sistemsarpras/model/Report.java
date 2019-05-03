package com.hamz.sistemsarpras.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamz on 02/03/2018.
 */

public class Report {

    @SerializedName("id_report")
    private int id_report;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private String image;
    @SerializedName("name")
    private String name;

    public Report(){};

    public Report(int id_report, String title, String description) {
        this.id_report = id_report;
        this.title = title;
        this.description = description;
    }

    public void setId_report(int id_report) {
        this.id_report = id_report;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_report() {
        return id_report;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

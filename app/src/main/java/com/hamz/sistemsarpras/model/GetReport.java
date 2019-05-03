package com.hamz.sistemsarpras.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Hamz on 02/03/2018.
 */

public class GetReport {

    @SerializedName("result")
    ArrayList<Report> listReport;

    public void setListReport(ArrayList<Report> listReport) {
        this.listReport = listReport;
    }

    public ArrayList<Report> getListReport() {
        return listReport;
    }
}

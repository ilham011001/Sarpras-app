package com.hamz.sistemsarpras.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Hamz on 08/03/2018.
 */

public class GetAdmin {

    @SerializedName("result_projector")
    ArrayList<Projector> listProjector;

    public ArrayList<Projector> getListProjector() {
        return listProjector;
    }

    public void setListProjector(ArrayList<Projector> listProjector) {
        this.listProjector = listProjector;
    }

    @SerializedName("result_projectorUser")
    ArrayList<ProjectorUser> listProjectorUser;

    public ArrayList<ProjectorUser> getListProjectorUser() {
        return listProjectorUser;
    }

    public void setListProjectorUser(ArrayList<ProjectorUser> listProjectorUser) {
        this.listProjectorUser = listProjectorUser;
    }

    @SerializedName("result_request_borrow")
    ArrayList<RequestBorrow> listRequestBorrow;

    public ArrayList<RequestBorrow> getListRequestBorrow() {
        return listRequestBorrow;
    }

    public void setListRequestBorrow(ArrayList<RequestBorrow> listRequestBorrow) {
        this.listRequestBorrow = listRequestBorrow;
    }

    @SerializedName("result_borrowed")
    ArrayList<Borrowed> listBorrowed;

    public ArrayList<Borrowed> getListBorrowed() {
        return listBorrowed;
    }

    public void setListBorrowed(ArrayList<Borrowed> listBorrowed) {
        this.listBorrowed = listBorrowed;
    }



}

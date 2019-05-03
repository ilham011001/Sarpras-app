package com.hamz.sistemsarpras.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Hamz on 04/03/2018.
 */

public class GetContact {

    @SerializedName("result")
    ArrayList<Contact> listContact;

    public ArrayList<Contact> getListContact() {
        return listContact;
    }

    public void setListContact(ArrayList<Contact> listContact) {
        this.listContact = listContact;
    }
}

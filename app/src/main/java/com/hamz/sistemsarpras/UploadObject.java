package com.hamz.sistemsarpras;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamz on 03/03/2018.
 */

public class UploadObject {

    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}

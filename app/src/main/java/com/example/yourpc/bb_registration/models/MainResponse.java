package com.example.yourpc.bb_registration.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hendi on 12/5/16.
 */

public class MainResponse {

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
}

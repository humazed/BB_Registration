package com.example.yourpc.bb_registration.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * hendiware 12 2016
 */

public class User extends RealmObject {

    @SerializedName("user_name")
    public String username;
    @SerializedName("id")
    public String id;
    @SerializedName("password")
    public String password;
    @SerializedName("blood_type")
    public String bloodType;
    @SerializedName("fcm_registration_token")
    public String fcmRegistrationToken;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", fcmRegistrationToken='" + fcmRegistrationToken + '\'' +
                "} " + super.toString();
    }
}


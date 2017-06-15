package com.example.yourpc.bb_registration.webservices;


import com.example.yourpc.bb_registration.models.MainResponse;
import com.example.yourpc.bb_registration.models.NotificationResponse;
import com.example.yourpc.bb_registration.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    @POST("login-user.php")
    Call<MainResponse> loginUser(@Body User user);

    @POST("register-user.php")
    Call<MainResponse> registerUser(@Body User user);

    @POST("notification_result.php")
    Call<MainResponse> sendResponse(@Body NotificationResponse response);
}
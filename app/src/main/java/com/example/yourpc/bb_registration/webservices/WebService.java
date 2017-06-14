package com.example.yourpc.bb_registration.webservices;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * hendiware 2016
 */

public class WebService {
    private static WebService instance;
    private API api;

    public WebService() {

//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();

        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Urls.MAIN_URL)
                .build();

        api = retrofit.create(API.class);
    }

    public static WebService getInstance() {
        if (instance == null) {
            instance = new WebService();
        }
        return instance;
    }

    public API getApi() {
        return api;
    }
}

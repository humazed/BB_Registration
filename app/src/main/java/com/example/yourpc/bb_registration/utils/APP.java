package com.example.yourpc.bb_registration.utils;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import io.realm.Realm;
import okhttp3.OkHttpClient;

/**
 * Created by hendiware on 2016/12 .
 */

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

    }
}

package com.paprbit.bhamashah.net.retrofit;

import android.content.Context;
import android.provider.ContactsContract;

import com.paprbit.bhamashah.net.retrofit.service.ApiService;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by ankush38u on 12/26/2015.
 */
public class ServiceGenerator {
    // creates ApiService instance as singleton
    private static ApiService service = null;
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient=null;


    public static ApiService getService() {

        if(okHttpClient==null){
            okHttpClient=new OkHttpClient();
            okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
            okHttpClient.setConnectTimeout(60,TimeUnit.SECONDS);
        }

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://apitest.sewadwaar.rajasthan.gov.in")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        if (service == null) service = retrofit.create(ApiService.class);

        return service;
    }

}

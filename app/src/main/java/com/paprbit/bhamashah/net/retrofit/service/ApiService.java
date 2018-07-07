package com.paprbit.bhamashah.net.retrofit.service;


import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Url;

/**
 * Created by ankush38u on 12/26/2015.
 */
public interface ApiService {


    //service1
    @GET("/app/live/Service/hofMembphoto/{id}/0?client_id=ad7288a4-7764-436d-a727-783a977f1fe1")
    Call<ResponseBody> getHofPhoto(@Path("id") String ackId);

    //service2 gethofandfamilyalldetailsinEnglishIncludingAccount
    //works only with family id
    @GET("/app/live/Service/hofAndMember/ForApp/{id}?client_id=ad7288a4-7764-436d-a727-783a977f1fe1")
    Call<ResponseBody> getHofAndFamilyEnglishFullDetails(@Path("id") String familyId);

    //4 and 5 service
    @GET("/app/live/Service/Bhamashah/hofAndMember/{id}?client_id=ad7288a4-7764-436d-a727-783a977f1fe1")
    Call<ResponseBody> getHofAndFamilyHindiEnglishLessDetails(@Path("id") String familyIdOrAckId);

    //6 service
    @GET("/app/live/Service/family/details/{id}?client_id=ad7288a4-7764-436d-a727-783a977f1fe1")
    Call<ResponseBody> getBhamashahFamilyIdFromAckId(@Path("id") String familyIdOrAckId);

    //6 service
    //This service gives most of the details like ration card too in both eng and hindi but not account info
    //best one to use in most cases
    @GET("/app/live/Service/family/details/{id}?client_id=ad7288a4-7764-436d-a727-783a977f1fe1")
    Call<ResponseBody> getBhamashahHofAndFamilyEngHindiDetailsMedium(@Path("id") String familyIdOrAckId);
}

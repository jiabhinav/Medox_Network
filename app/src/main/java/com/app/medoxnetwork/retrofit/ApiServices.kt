package com.app.medoxnetwork.retrofit

import com.app.medoxnetwork.model.ModelEntry
import com.app.medoxnetwork.model.ModelRegister
import com.app.medoxnetwork.model.ModelUser
import com.app.medoxnetwork.model.RegisterOTP
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

      @GET("/entries")
     suspend  fun getEntries(): Response<ModelEntry>

    @FormUrlEncoded
    @POST("android-register-member")
    suspend  fun android_register_member(@FieldMap jsonObject: LinkedHashMap<String, String>): Response<ModelRegister>

    @FormUrlEncoded
    @POST("android-register-otp")
    suspend  fun android_register_otp(@FieldMap jsonObject: LinkedHashMap<String, String>): Response<RegisterOTP>

    @FormUrlEncoded
    @POST("android-success-register-otp")
    suspend  fun android_success_register_otp(@FieldMap jsonObject: LinkedHashMap<String, String>): Response<RegisterOTP>

    @FormUrlEncoded
    @POST("android-login-member")
    suspend  fun android_login_member(@FieldMap bodyParams: LinkedHashMap<String, String>): Response<ModelUser>


}
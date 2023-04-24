package com.app.medoxnetwork.retrofit

import com.app.medoxnetwork.model.*
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    @FormUrlEncoded
    @POST("android-dashboard")
     suspend  fun android_dashboard(@FieldMap jsonObject: LinkedHashMap<String, String>): Response<ModelDashboard>

    @FormUrlEncoded
    @POST("android-claim-reward")
    suspend  fun android_claim_reward(@FieldMap jsonObject: LinkedHashMap<String, String>): Response<ModelClaim>

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

    @FormUrlEncoded
    @POST("android-reset-password")
    suspend  fun android_reset_password(@FieldMap bodyParams: LinkedHashMap<String, String>): Response<ModelResetPassword>

    @FormUrlEncoded
    @POST("android-user-wallet")
    suspend  fun android_user_wallet(@FieldMap jsonObject: LinkedHashMap<String, String>): Response<ModelWalletList>

    @FormUrlEncoded
    @POST("android-wallet-details")
    suspend  fun android_wallet_details(@FieldMap jsonObject: LinkedHashMap<String, String>): Response<ModelWalletHistory>

    @FormUrlEncoded
    @POST("android-team")
    suspend  fun android_team(@FieldMap jsonObject: LinkedHashMap<String, String>): Response<ModelTeam>



}
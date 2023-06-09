package com.app.medoxnetwork.retrofit


import com.google.gson.JsonObject
import javax.inject.Inject

    class ApiRepository @Inject constructor(
    private val apiServices: ApiServices)
{
   suspend fun android_dashboard(jsonObject: LinkedHashMap<String, String>) = apiServices.android_dashboard(jsonObject)
    suspend fun android_claim_reward(jsonObject: LinkedHashMap<String, String>) = apiServices.android_claim_reward(jsonObject)

    suspend fun android_register_member(jsonObject: LinkedHashMap<String, String>) = apiServices.android_register_member(jsonObject)

    suspend fun android_register_otp(jsonObject: LinkedHashMap<String, String>) = apiServices.android_register_otp(jsonObject)

    suspend fun android_success_register_otp(jsonObject: LinkedHashMap<String, String>) = apiServices.android_success_register_otp(jsonObject)

    suspend fun android_login_member(bodyParams: LinkedHashMap<String, String>) = apiServices.android_login_member(bodyParams)

    suspend fun android_reset_password(bodyParams: LinkedHashMap<String, String>) = apiServices.android_reset_password(bodyParams)

    suspend fun android_user_wallet(bodyParams: LinkedHashMap<String, String>) = apiServices.android_user_wallet(bodyParams)

    suspend fun android_wallet_details(bodyParams: LinkedHashMap<String, String>) = apiServices.android_wallet_details(bodyParams)

    suspend fun android_team(bodyParams: LinkedHashMap<String, String>) = apiServices.android_team(bodyParams)

    suspend fun android_deposit_page(bodyParams: LinkedHashMap<String, String>) = apiServices.android_deposit_page(bodyParams)

    suspend fun android_refresh_fund(bodyParams: LinkedHashMap<String, String>) = apiServices.android_refresh_fund(bodyParams)

    suspend fun android_stake_mnt(bodyParams: LinkedHashMap<String, String>) = apiServices.android_stake_mnt(bodyParams)


    suspend fun android_total_team(bodyParams: LinkedHashMap<String, String>) = apiServices.android_total_team(bodyParams)

    suspend fun android_support(bodyParams: LinkedHashMap<String, String>) = apiServices.android_support(bodyParams)


    suspend fun android_change_password(bodyParams: LinkedHashMap<String, String>) = apiServices.android_change_password(bodyParams)

 suspend fun android_withdraw_page(bodyParams: LinkedHashMap<String, String>) =apiServices.android_withdraw_page(bodyParams)

 suspend fun  android_withdraw_fund(bodyParams: LinkedHashMap<String, String>) =apiServices.android_withdraw_fund(bodyParams)

 suspend fun  android_withdraw_history(bodyParams: LinkedHashMap<String, String>) =apiServices.android_withdraw_history(bodyParams)











}
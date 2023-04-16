package com.app.medoxnetwork.retrofit


import com.google.gson.JsonObject
import javax.inject.Inject

    class ApiRepository @Inject constructor(
    private val apiServices: ApiServices)
{
   suspend fun getEntries() = apiServices.getEntries()
    suspend fun android_register_member(jsonObject: LinkedHashMap<String, String>) = apiServices.android_register_member(jsonObject)

    suspend fun android_register_otp(jsonObject: LinkedHashMap<String, String>) = apiServices.android_register_otp(jsonObject)

    suspend fun android_success_register_otp(jsonObject: LinkedHashMap<String, String>) = apiServices.android_success_register_otp(jsonObject)

    suspend fun android_login_member(bodyParams: LinkedHashMap<String, String>) = apiServices.android_login_member(bodyParams)



}
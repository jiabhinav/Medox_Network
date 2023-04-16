package com.app.medoxnetwork.retrofit

import javax.inject.Inject

    class ApiRepository @Inject constructor(
    private val apiServices: ApiServices)
{
   suspend fun getEntries() = apiServices.getEntries()
}
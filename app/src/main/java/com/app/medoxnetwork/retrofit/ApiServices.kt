package com.app.medoxnetwork.retrofit

import com.app.medoxnetwork.model.ModelEntry
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

      @GET("/entries")
     suspend  fun getEntries(): Response<ModelEntry>
}
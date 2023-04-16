package com.app.medoxnetwork.viewmodel


import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.medoxnetwork.model.ModelEntry
import com.app.medoxnetwork.retrofit.ApiRepository
import com.app.medoxnetwork.utils.Utility.getError
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
     private val apiRepository: ApiRepository
) : ViewModel() {

     val userResponse = MutableLiveData<ModelEntry>()
    val error: MutableLiveData<String> = MutableLiveData()
    val loading = MutableLiveData<Boolean>()

    fun callAPI()
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.getEntries().let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        userResponse.postValue(it.body())
                    }else{
                        val res = getError(it)
                        val respons=Gson().toJson(res)
                        Log.d("TAG", "callAPI: "+respons)
                        error.postValue(respons)
                    }
                }
            }
            catch (e:Exception)
            {
                loading.postValue(false)
                error.postValue(e.message)
            }



        }

    }


}
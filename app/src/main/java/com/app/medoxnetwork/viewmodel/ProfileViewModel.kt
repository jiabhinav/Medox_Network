package com.app.medoxnetwork.viewmodel


import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.medoxnetwork.model.*
import com.app.medoxnetwork.retrofit.ApiRepository
import com.app.medoxnetwork.utils.Utility.getError
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
     private val apiRepository: ApiRepository
) : ViewModel() {

     val userwallet = MutableLiveData<ModelWalletList>()
    val walletHistory = MutableLiveData<ModelWalletHistory>()

    val supportSuccess = MutableLiveData<ModelSuccess>()

    val error: MutableLiveData<String> = MutableLiveData()
    val loading = MutableLiveData<Boolean>()

    fun android_user_wallet(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_user_wallet(jsonObject).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        userwallet.postValue(it.body())
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
                Log.d("TAG", "registerMember: "+e.message)
                loading.postValue(false)
                error.postValue(e.message)
            }

        }

    }

    fun android_wallet_details(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_wallet_details(jsonObject).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        Log.d("TAG", "sendOTP: "+Gson().toJson(it.body()))
                        walletHistory.postValue(it.body())
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

    fun android_support(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_support(jsonObject).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        supportSuccess.postValue(it.body())
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
                Log.d("TAG", "registerMember: "+e.message)
                loading.postValue(false)
                error.postValue(e.message)
            }

        }

    }



}
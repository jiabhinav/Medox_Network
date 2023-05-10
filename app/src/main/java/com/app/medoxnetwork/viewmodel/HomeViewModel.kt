package com.app.medoxnetwork.viewmodel


import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.medoxnetwork.model.*
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

     val userResponse = MutableLiveData<ModelDashboard>()
    val modelstake = MutableLiveData<ModelSuccess>()
    val claim = MutableLiveData<ModelClaim>()

    val modelTotalTeam = MutableLiveData<ModelTotalTeam>()

    val error: MutableLiveData<String> = MutableLiveData()
    val loading = MutableLiveData<Boolean>()

    fun callAPI(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_dashboard(jsonObject).let {
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

    fun android_claim_reward(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_claim_reward(jsonObject).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        claim.postValue(it.body())
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

    fun  android_stake_mnt(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_stake_mnt(jsonObject).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        modelstake.postValue(it.body())
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

    fun  android_total_team(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_total_team(jsonObject).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        modelTotalTeam.postValue(it.body())
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
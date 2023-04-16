package com.app.medoxnetwork.viewmodel


import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.medoxnetwork.model.ModelEntry
import com.app.medoxnetwork.model.ModelRegister
import com.app.medoxnetwork.model.ModelUser
import com.app.medoxnetwork.model.RegisterOTP
import com.app.medoxnetwork.retrofit.ApiRepository
import com.app.medoxnetwork.utils.Utility.getError
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
     private val apiRepository: ApiRepository
) : ViewModel() {

     val userRegister = MutableLiveData<ModelRegister>()
    val userOTP = MutableLiveData<RegisterOTP>()
    val userLogin = MutableLiveData<ModelUser>()

    val verifyOTP = MutableLiveData<RegisterOTP>()
    val error: MutableLiveData<String> = MutableLiveData()
    val loading = MutableLiveData<Boolean>()

    fun registerMember(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_register_member(jsonObject).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        userRegister.postValue(it.body())
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

    fun sendOTP(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_register_otp(jsonObject).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        Log.d("TAG", "sendOTP: "+Gson().toJson(it.body()))
                        userOTP.postValue(it.body())
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

    fun veryOTP(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_success_register_otp(jsonObject).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        verifyOTP.postValue(it.body())
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

    fun loginUser(bodyParams: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_login_member(bodyParams).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        userLogin.postValue(it.body())
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
                Log.d("TAG", "registerMember: "+e.message)
            }

        }

    }



}
package com.app.medoxnetwork.viewmodel


import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.medoxnetwork.model.*
import com.app.medoxnetwork.retrofit.ApiRepository
import com.app.medoxnetwork.ui.home.WithdrawHistory
import com.app.medoxnetwork.utils.Utility.getError
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
     private val apiRepository: ApiRepository
) : ViewModel() {

     val userwallet = MutableLiveData<ModelWalletList>()
    val walletHistory = MutableLiveData<ModelWalletHistory>()
    val deposit = MutableLiveData<ModelDepositAddress>()

    val withdrwaBalance = MutableLiveData<ModelWithdrawBalance>()

    val refreshDeposit = MutableLiveData<ModelSuccess>()
    val withdrawamount = MutableLiveData<ModelSuccess>()
    val withdrawHistory = MutableLiveData<ModelWithdrawHistory>()


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


    fun android_deposit_page(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_deposit_page(jsonObject).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        deposit.postValue(it.body())
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

    fun android_refresh_fund(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_refresh_fund(jsonObject).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        refreshDeposit.postValue(it.body())
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

    fun android_withdraw_page(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_withdraw_page(jsonObject).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        withdrwaBalance.postValue(it.body())
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

    fun android_withdraw_fund(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_withdraw_fund(jsonObject).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        withdrawamount.postValue(it.body())
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

    fun android_withdraw_history(jsonObject: LinkedHashMap<String, String>)
    {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                apiRepository.android_withdraw_history(jsonObject).let {
                    loading.postValue(false)
                    if (it.isSuccessful){
                        withdrawHistory.postValue(it.body())
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
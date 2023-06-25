package com.app.medoxnetwork.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.medoxnetwork.R
import com.app.medoxnetwork.base.BaseFragment
import com.app.medoxnetwork.databinding.AlertGiftcardBinding
import com.app.medoxnetwork.databinding.FragmentHomeBinding
import com.app.medoxnetwork.databinding.FragmentStakeBinding
import com.app.medoxnetwork.databinding.FragmentWithdraFundBinding
import com.app.medoxnetwork.model.ModelDashboard
import com.app.medoxnetwork.utils.ScratchView
import com.app.medoxnetwork.utils.Utility
import com.app.medoxnetwork.utils.Utility.showToast
import com.app.medoxnetwork.viewmodel.HomeViewModel
import com.app.medoxnetwork.viewmodel.WalletViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WithdrawFundragment : BaseFragment() {

    private var binding: FragmentWithdraFundBinding? = null
    
    private val viewModel: WalletViewModel by viewModels()
     var type="1"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (binding==null)
        {
            binding = FragmentWithdraFundBinding.inflate(inflater, container, false)
            init()
        }
        return binding!!.root
    }

    fun init()
    {
        observeData()
        callWithdrawAPI()
        binding!!.stakeamount.setOnClickListener{
            if (binding!!.editamount.text.toString().equals(""))
            {
                showToast("Enter withdraw amount",2)
            }
            else
            {
                callAPI()
            }
        }


    }
    fun callWithdrawAPI()
    {
        val params = LinkedHashMap<String, String>()
        params.put("username", sp.getUser()!!.result.username)
        viewModel.android_withdraw_page(params)
    }

    fun callAPI()
    {
        var type="1"
        val typeWithdra=binding?.spinner?.selectedItem.toString()
        if (typeWithdra.equals("USDT"))
        {
            type="1"
        }
        else
        {
         type="2"
        }

        val params = LinkedHashMap<String, String>()
        params.put("username", sp.getUser()!!.result.username)
        params.put("amount", binding!!.editamount.text.toString())
        params.put("type", type)
        viewModel.android_withdraw_fund(params)
    }

    fun observeData() {
        viewModel.withdrawamount.observe(requireActivity()) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            if (it.status==1)
            {
                binding?.editamount?.setText("")
            }
            showToast(it.result.msg,2)

        }

        viewModel.withdrwaBalance.observe(requireActivity()) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            if (it.status == 1) {
                binding?.withdrawbalance?.text="Balance: ${it.result?.balance.toString()}$"

            } else {
               showToast(getString(R.string.contecttosupport), 2)
            }

        }

        viewModel.error.observe(requireActivity()) {
            showToast(it,2)
        }

        viewModel.loading.observe(requireActivity()) {
            loadingProgress(it)
        }

    }



}
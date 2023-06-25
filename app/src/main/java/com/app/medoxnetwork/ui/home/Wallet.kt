package com.app.medoxnetwork.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.app.medoxnetwork.R
import com.app.medoxnetwork.base.BaseFragment
import com.app.medoxnetwork.databinding.FragmentHomeBinding
import com.app.medoxnetwork.databinding.FragmentWalletBinding
import com.app.medoxnetwork.utils.Utility
import com.app.medoxnetwork.viewmodel.HomeViewModel
import com.app.medoxnetwork.viewmodel.WalletViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Wallet : BaseFragment() {

    lateinit var binding:FragmentWalletBinding
    private val viewModel: WalletViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::binding.isInitialized)
        {
            binding = FragmentWalletBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }


    fun init()
    {
        binding.card1.setOnClickListener{

            val bundle=Bundle()
            bundle.putInt("type",2)
            findNavController().navigate(R.id.nav_wallet_history,bundle)
        }
        binding.card2.setOnClickListener{
            val bundle=Bundle()
            bundle.putInt("type",1)
            findNavController().navigate(R.id.nav_wallet_history,bundle)
        }
        binding.card3.setOnClickListener{
            val bundle=Bundle()
            bundle.putInt("type",3)
            findNavController().navigate(R.id.nav_wallet_history,bundle)
        }

        binding.swiprefresh.setOnRefreshListener {
            binding.swiprefresh.isRefreshing=false
            callAPI()
        }

        binding.lldeposit.setOnClickListener{
            findNavController().navigate(R.id.nav_deposit)
        }

        binding.withdraw.setOnClickListener{

        }

        observeData()
        callAPI()

    }

    fun callAPI()
    {
        val params = LinkedHashMap<String, String>()
        params.put("username", sp.getUser()!!.result.username)
        viewModel.android_user_wallet(params)
    }





    fun observeData() {

        viewModel.userwallet.observe(requireActivity()) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            if (it.status == 1) {
                binding.funding.text=it.result.funding_wallet.toString()
                binding.bonus.text=it.result.bonus_wallet.toString()
                binding.reward.text=it.result.reward_wallet.toString()

            } else {
                Utility.showToast(getString(R.string.contecttosupport), 2)
            }


            viewModel.error.observe(requireActivity()) {
                Utility.showToast(it, 2)
            }

            viewModel.loading.observe(requireActivity()) {
                loadingProgress(it)
            }

        }
    }



}
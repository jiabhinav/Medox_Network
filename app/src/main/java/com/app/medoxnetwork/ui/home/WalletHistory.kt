package com.app.medoxnetwork.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.app.medoxnetwork.R
import com.app.medoxnetwork.adapter.WalletHistoryAdapter
import com.app.medoxnetwork.base.BaseFragment
import com.app.medoxnetwork.databinding.FragmentHomeBinding
import com.app.medoxnetwork.databinding.FragmentWalletBinding
import com.app.medoxnetwork.databinding.FragmentWalletHistoryBinding
import com.app.medoxnetwork.model.ModelWalletHistory
import com.app.medoxnetwork.utils.Utility
import com.app.medoxnetwork.viewmodel.HomeViewModel
import com.app.medoxnetwork.viewmodel.WalletViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletHistory : BaseFragment() {

    lateinit var binding:FragmentWalletHistoryBinding
    private val viewModel: WalletViewModel by viewModels()
    var wallet_type:Int?=1

    var list=ArrayList<ModelWalletHistory.Result>()
    lateinit var adapter: WalletHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wallet_type=arguments?.getInt("type",1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::binding.isInitialized)
        {
            binding = FragmentWalletHistoryBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }
    fun init()
    {
        adapter= WalletHistoryAdapter(requireContext(),list)
        binding.recyclerview.adapter=adapter

        binding.swiprefresh.setOnRefreshListener {
            binding.swiprefresh.isRefreshing=false
            callAPI()
        }

        observeData()
        callAPI()
    }

    fun callAPI()
    {
        val params = LinkedHashMap<String, String>()
        params.put("username", sp.getUser()!!.result.username)
        params.put("wallet", wallet_type.toString())

        viewModel.android_wallet_details(params)
    }

    fun observeData() {
        viewModel.walletHistory.observe(requireActivity()) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            if(it.status==1)
            {
                list.clear()
                list.addAll(it.result)
                adapter.notifyDataSetChanged()
            }
            else
            {
                Utility.showToast(getString(R.string.contecttosupport), 2)
            }

        }


        viewModel.error.observe(requireActivity()) {
            Utility.showToast(it, 2)
        }

        viewModel.loading.observe(requireActivity()) {
            loadingProgress(it)
        }

    }

}
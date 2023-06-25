package com.app.medoxnetwork.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.medoxnetwork.R
import com.app.medoxnetwork.adapter.WithdrawHistoryAdapter
import com.app.medoxnetwork.base.BaseFragment
import com.app.medoxnetwork.databinding.FragmentWithdrawHistoryBinding
import com.app.medoxnetwork.model.ModelWithdrawHistory

import com.app.medoxnetwork.utils.Utility

import com.app.medoxnetwork.viewmodel.WalletViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawHistory : BaseFragment() {

    lateinit var binding:FragmentWithdrawHistoryBinding
    private val viewModel: WalletViewModel by viewModels()
    lateinit var adapter: WithdrawHistoryAdapter
    var listhistory=ArrayList<ModelWithdrawHistory.Result?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::binding.isInitialized)
        {
            binding = FragmentWithdrawHistoryBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }
    fun init()
    {
        adapter=WithdrawHistoryAdapter(requireContext(),listhistory)
        binding.recyclerView.adapter=adapter

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
        viewModel.android_withdraw_history(params)
    }

    fun observeData() {
        viewModel.withdrawHistory.observe(requireActivity()) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            if(it.status==1)
            {
                listhistory.clear()
                if (it.result?.size!!>0)
                {
                    listhistory.addAll(it.result)
                }
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
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
import com.app.medoxnetwork.model.ModelDashboard
import com.app.medoxnetwork.utils.ScratchView
import com.app.medoxnetwork.utils.Utility.showToast
import com.app.medoxnetwork.viewmodel.HomeViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StakeFragment : BaseFragment() {

    private var binding: FragmentStakeBinding? = null
    
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (binding==null)
        {
            binding = FragmentStakeBinding.inflate(inflater, container, false)
            init()
        }
        return binding!!.root
    }

    fun init()
    {
        observeData()
        binding!!.stakeamount.setOnClickListener{
            if (binding!!.editamount.text.toString().equals(""))
            {
                showToast("Enter Stake Amount",2)
            }
            else
            {
                callAPI()
            }
        }


    }

    fun callAPI()
    {
        val params = LinkedHashMap<String, String>()
        params.put("username", sp.getUser()!!.result.username)
        params.put("amount", binding!!.editamount.text.toString())
        viewModel.android_stake_mnt(params)
    }

    fun observeData() {
        viewModel.modelstake.observe(requireActivity()) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            showToast(it.result.msg,2)

        }

        viewModel.error.observe(requireActivity()) {
            showToast(it,2)
        }

        viewModel.loading.observe(requireActivity()) {
            loadingProgress(it)
        }

    }



}
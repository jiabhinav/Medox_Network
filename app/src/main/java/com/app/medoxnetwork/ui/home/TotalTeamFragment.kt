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
import com.app.medoxnetwork.adapter.TotalTeamAdapter
import com.app.medoxnetwork.base.BaseFragment
import com.app.medoxnetwork.databinding.AlertGiftcardBinding
import com.app.medoxnetwork.databinding.FragmentHomeBinding
import com.app.medoxnetwork.databinding.FragmentStakeBinding
import com.app.medoxnetwork.databinding.FragmentTotalTeamBinding
import com.app.medoxnetwork.model.ModelDashboard
import com.app.medoxnetwork.model.ModelTotalTeam
import com.app.medoxnetwork.utils.ScratchView
import com.app.medoxnetwork.utils.Utility.showToast
import com.app.medoxnetwork.viewmodel.HomeViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TotalTeamFragment : BaseFragment() {

    private var binding: FragmentTotalTeamBinding? = null
    
    private val viewModel: HomeViewModel by viewModels()
    lateinit var adapter: TotalTeamAdapter
    var listTeam=ArrayList<ModelTotalTeam.Result.Data>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (binding==null)
        {
            binding = FragmentTotalTeamBinding.inflate(inflater, container, false)
            init()
        }
        return binding!!.root
    }

    fun init()
    {
        adapter= TotalTeamAdapter(requireContext(),listTeam)
        binding!!.recyclerView.adapter=adapter
        observeData()
        callAPI()
    }

    fun callAPI()
    {
        val params = LinkedHashMap<String, String>()
        params.put("username", sp.getUser()!!.result.username)
        viewModel.android_total_team(params)
    }

    fun observeData() {
        viewModel.modelTotalTeam.observe(requireActivity()) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            if (it.status==1)
            {
                if (it.result.data.size>0)
                {
                    listTeam.clear()
                    listTeam.addAll(it.result.data)
                    adapter.notifyDataSetChanged()
                }
            }
            else
            {
                showToast(getString(R.string.contecttosupport),2)
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
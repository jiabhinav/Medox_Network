package com.app.medoxnetwork.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.medoxnetwork.R
import com.app.medoxnetwork.base.BaseFragment
import com.app.medoxnetwork.databinding.FragmentHomeBinding
import com.app.medoxnetwork.databinding.FragmentTeamBinding
import com.app.medoxnetwork.databinding.FragmentWalletBinding
import com.app.medoxnetwork.utils.Utility
import com.app.medoxnetwork.viewmodel.HomeViewModel
import com.app.medoxnetwork.viewmodel.TeamViewModel
import com.app.medoxnetwork.viewmodel.WalletViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Team : BaseFragment() {

    lateinit var binding:FragmentTeamBinding
    private val viewModel: TeamViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::binding.isInitialized)
        {
            binding = FragmentTeamBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }
    fun init()
    {
        binding.card1.setOnClickListener{

            val bundle=Bundle()
            bundle.putInt("type",2)
           // findNavController().navigate(R.id.nav_wallet_history,bundle)
        }

        binding.referral.setOnClickListener{
            Invite()
        }

        binding.swiprefresh.setOnRefreshListener {
            binding.swiprefresh.isRefreshing=false
            callAPI()
        }
        binding.llteamlevel.setOnClickListener{
            findNavController().navigate(R.id.nav_totalteam)
        }
        observeData()
        callAPI()
    }

    fun callAPI()
    {
        val params = LinkedHashMap<String, String>()
        params.put("username", sp.getUser()!!.result.username)
        viewModel.android_team(params)
    }

    fun observeData() {
        viewModel.userTeam.observe(requireActivity()) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            if(it.status==1)
            {
                binding.name.text=it.result.Sponsor_name
                binding.mobile.text=it.result.sponsor_phone

                binding.username.text=it.result.sponsor_username

                binding.totalmember.text=it.result.total_downline.toString()
                binding.directmemeber.text=it.result.direct_downline.toString()


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

    fun Invite()
    {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT, """
     Hi,
     Inviting you to join ${getString(R.string.app_name)} an interesting app which provides you incredible offer, Refer and earn.
     
     Use my referrer code:
     
     ${sp.getUser()?.result?.username}
     
     Download app from link:
     https://play.google.com/store/apps/details?id=${requireActivity().packageName}&referrer=${sp.getUser()?.result?.username}
     """.trimIndent()
        )
        startActivity(intent)
    }

}
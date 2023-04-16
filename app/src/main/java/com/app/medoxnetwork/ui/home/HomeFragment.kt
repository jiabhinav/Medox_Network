package com.app.medoxnetwork.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.app.medoxnetwork.R
import com.app.medoxnetwork.base.BaseFragment
import com.app.medoxnetwork.databinding.FragmentHomeBinding
import com.app.medoxnetwork.model.ModelDashboard
import com.app.medoxnetwork.utils.Utility.showToast
import com.app.medoxnetwork.viewmodel.HomeViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding? = null
    
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (binding==null)
        {
            binding = FragmentHomeBinding.inflate(inflater, container, false)
            init()
        }
        return binding!!.root
    }

    fun init()
    {
        binding!!.dropdown.setOnClickListener{
            if (binding!!.llgloblestate.isVisible)
            {
                binding!!.llgloblestate.visibility=View.GONE
                binding!!.dropdown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                binding!!.rltabtoearm.visibility=View.VISIBLE
            }
            else
            {
                binding!!.llgloblestate.visibility=View.VISIBLE
                binding!!.dropdown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                binding!!.rltabtoearm.visibility= View.GONE

            }
        }
        binding!!.swiprefresh.setOnRefreshListener {
            binding!!.swiprefresh.isRefreshing=false
            callAPI()
        }

        observeData()
        callAPI()
    }

    fun callAPI()
    {
        val params = LinkedHashMap<String, String>()
        params.put("username", sp.getUser()!!.result.username)
       viewModel.callAPI(params)
    }

    fun observeData() {
        viewModel.userResponse.observe(requireActivity()) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            if(it.status==1)
            {
                setDataOnText(it.result)

            }
            else
            {
                showToast("Contact to support",2)
            }

        }


        viewModel.error.observe(requireActivity()) {
            showToast(it,2)
        }

        viewModel.loading.observe(requireActivity()) {
            loadingProgress(it)
        }

    }

    fun setDataOnText(model: ModelDashboard.Result)
    {
        binding!!.mystaking.text=model.my_staking.toString()
        binding!!.stackingrewards.text=model.staking_reward.toString()
        binding!!.myteam.text=model.my_team.toString()
        binding!!.teamrewards.text=model.team_reward.toString()

        binding!!.directrewards.text=model.direct_reward.toString()
        binding!!.totalrewards.text=model.total_reward.toString()

        binding!!.totalparticipate.text=model.total_participants.toString()
        binding!!.countries.text=model.countries.toString()

        binding!!.totalstackpool.text=model.total_staked_in_pool.toString()
        binding!!.totalstackedpoolusd.text=model.total_staked_in_pool_usd.toString()

        binding!!.globetotalrewards.text=model.total_rewards.toString()
        binding!!.totalrewardsusd.text=model.total_rewards_usd.toString()

        binding!!.totalwithdraws.text=model.total_withdraw.toString()
        binding!!.totalrewardsusd.text=model.total_withdraw_usd.toString()

    }


}
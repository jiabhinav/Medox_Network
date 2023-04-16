package com.app.medoxnetwork.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.medoxnetwork.R
import com.app.medoxnetwork.base.BaseFragment
import com.app.medoxnetwork.databinding.FragmentHomeBinding
import com.app.medoxnetwork.databinding.FragmentWalletBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Wallet : BaseFragment() {

    lateinit var binding:FragmentWalletBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWalletBinding.inflate(inflater, container, false)
        return binding.root
    }
    fun init()
    {

    }

}
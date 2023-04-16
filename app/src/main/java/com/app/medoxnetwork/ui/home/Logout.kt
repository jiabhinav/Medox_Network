package com.app.medoxnetwork.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.medoxnetwork.R
import com.app.medoxnetwork.databinding.FragmentLogoutBinding
import com.app.medoxnetwork.session.SesssionManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class Logout  : BottomSheetDialogFragment() ,View.OnClickListener{

    lateinit var binding: FragmentLogoutBinding
    @Inject
    lateinit var sp:SesssionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NO_FRAME, R.style.CustomBottomSheetDialogTheme);

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLogoutBinding.inflate(layoutInflater)
        binding.logout.setOnClickListener(this)
        binding.cancel.setOnClickListener(this)
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        var behavior = BottomSheetBehavior.from(binding.root.parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {


    }

    override fun onClick(v: View?) {
        if (v?.id==R.id.logout)
        {
            dismiss()
            sp.logoutSession()
            requireActivity().finish()
        }
       else if (v?.id==R.id.cancel)
        {
           dismiss()
        }
    }





}
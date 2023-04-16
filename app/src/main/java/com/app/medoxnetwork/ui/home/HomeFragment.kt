package com.app.medoxnetwork.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.app.medoxnetwork.R
import com.app.medoxnetwork.databinding.FragmentHomeBinding
import com.app.medoxnetwork.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        init()
        return root
    }

    fun init()
    {
        binding.dropdown.setOnClickListener{
            if (binding.llgloblestate.isVisible)
            {
                binding.llgloblestate.visibility=View.GONE
                binding.dropdown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }
            else
            {
                binding.llgloblestate.visibility=View.VISIBLE
                binding.dropdown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
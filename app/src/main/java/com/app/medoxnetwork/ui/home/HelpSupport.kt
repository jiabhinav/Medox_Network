package com.app.medoxnetwork.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.medoxnetwork.base.BaseFragment
import com.app.medoxnetwork.databinding.FragmentHelpSupportBinding
import com.app.medoxnetwork.model.ModelUser
import com.app.medoxnetwork.session.SesssionManager
import com.app.medoxnetwork.utils.Utility.isOnline
import com.app.medoxnetwork.utils.Utility.showToast
import com.app.medoxnetwork.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HelpSupport : BaseFragment() {

    lateinit var binding: FragmentHelpSupportBinding

    var model: ModelUser.Result?=null

    @Inject
    lateinit var sess: SesssionManager

    private val viewmodel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHelpSupportBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    fun init()
    {


        model=sess.getUser()!!.result
        observeUser()

        binding.save.setOnClickListener {
            checkVaidation()
        }



    }

    fun  checkVaidation() {
        if (binding.subject.getText().toString() == "") {
            showToast("Enter subject",2)
        } else if (binding.message.getText().toString() == "") {
            showToast("Enter message",2)
        }  else {

            if(isOnline(requireContext()))
            {
                callAPI()
            }
            else
            {
                checkConnection(requireActivity())
            }
        }
    }

    fun callAPI()
    {

        val map= LinkedHashMap<String,String>()
        map["username"]=model?.username!!
        map["subject"]=binding.subject.text.toString()
        map["message"]=binding.message.text.toString()
        viewmodel.android_support(map)
    }



    fun observeUser()
    {
        viewmodel.supportSuccess.observe(requireActivity(),{
            if (it.status==1)
            {
                showToast(it.result.msg, 2)
                findNavController().navigateUp()

            }
            else
            {
                showToast(it.result.msg, 2)
            }

        })

        viewmodel.loading.observe(requireActivity(),{

            if (it)
            {
                showDialogs(requireActivity())
            }
            else{
                dismiss()

            }

        })


    }


}
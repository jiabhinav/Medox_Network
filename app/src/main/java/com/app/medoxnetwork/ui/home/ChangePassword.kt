package com.app.medoxnetwork.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.medoxnetwork.base.BaseFragment
import com.app.medoxnetwork.databinding.FragmentChangePasswordBinding
import com.app.medoxnetwork.model.ModelUser
import com.app.medoxnetwork.session.SesssionManager
import com.app.medoxnetwork.utils.Utility.isOnline
import com.app.medoxnetwork.utils.Utility.showToast
import com.app.medoxnetwork.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.HashMap
import javax.inject.Inject

@AndroidEntryPoint
class ChangePassword : BaseFragment() {

    lateinit var binding: FragmentChangePasswordBinding

    var model: ModelUser.Result?=null

    @Inject
    lateinit var sess: SesssionManager

    private val viewmodel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentChangePasswordBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    fun init()
    {


        model=sess.getUser()!!.result
        observeUser()

        binding.savepassword.setOnClickListener {
            checkVaidation()
        }



    }

    fun  checkVaidation() {
        if (binding.oldpaswword.getText().toString() == "") {
            showToast("Enter current password",2)
        } else if (binding.newpassword.getText().toString() == "") {
            showToast("Enter new password",2)
        } else if (binding.confirmpassword.getText().toString() == "") {
            showToast("confirm password is empty",2)
        } else if (binding.confirmpassword.text.toString() != binding.newpassword.text.toString()) {
            showToast("Confirm password dose not match to current password",2)
        } else {
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
        map["current"]=binding.oldpaswword.text.toString()
        map["new"]=binding.newpassword.text.toString()
        map["confirm"]=binding.confirmpassword.text.toString()
        viewmodel.android_change_password(map)
    }



    fun observeUser()
    {
        viewmodel.passwordSuccess.observe(requireActivity(),{
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
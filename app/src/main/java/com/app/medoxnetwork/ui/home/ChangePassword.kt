package com.app.medoxnetwork.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
            showToast("Enter Old password",2)
        } else if (binding.newpassword.getText().toString() == "") {
            showToast("Enter new password",2)
        } else if (binding.confirmpassword.getText().toString() == "") {
            showToast("confirm password",2)
        } else if (binding.confirmpassword.text.toString() != binding.newpassword.text.toString()) {
            showToast("Confirm password dose not match",2)
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

        val map= HashMap<String,String>()
        map["username"]=model?.username!!
        map["old"]=binding.oldpaswword.text.toString()
        map["new_password"]=binding.newpassword.text.toString()
      //  viewmodel.changePassword(map)
    }



    fun observeUser()
    {
       /* viewmodel.listpassword.observe(requireActivity(),{
            Log.d("TAG", "observeUser12414: "+ Gson().toJson(it))
            if (it.status==200)
            {
                showToast(it.message, 2)
                findNavController().navigateUp()

            }
            else
            {
                showToast(it.message, 2)
            }

        })*/

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
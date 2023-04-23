package com.app.medoxnetwork.ui.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController
import com.app.medoxnetwork.BuildConfig
import com.app.medoxnetwork.base.BaseFragment
import com.app.medoxnetwork.databinding.FragmentProfileBinding
import com.app.medoxnetwork.model.ModelUser
import com.app.medoxnetwork.session.SesssionManager
import com.app.medoxnetwork.utils.FileUtilsNew
import com.app.medoxnetwork.utils.loadImage
import com.app.medoxnetwork.viewmodel.ProfileViewModel

import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class Profile : BaseFragment() {

    lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var sess: SesssionManager
    lateinit var model: ModelUser.Result

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentProfileBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init()
    {
         model=sess.getUser()!!.result
        binding.name.text=model.name
        binding.name2.text=model.name
        binding.email.text=model.email
        binding.mobile.text=model.phone
        binding.username.text=model.username
        binding.referral.text=model.username
        val url= BuildConfig.IMAGE_BASE_URL+model.image
        Log.d("TAG", "init: "+url)
        binding.image.loadImage(url)


        binding.image.setOnClickListener {
            getImage()
        }


        observeUser()



    }

    fun observeUser()
    {


        viewModel.loading.observe(requireActivity(),{

            if (it)
            {
               showDialogs(requireActivity())
            }
            else{
                dismiss()

            }

        })


    }

    fun getImage()
    {
        val with: ImagePicker.Builder = ImagePicker.with(this)
        with.crop()
        with.compress(640)
        with.maxResultSize(640, 640)
        with.createIntent { Intent: Intent? ->
            startForCameraResult.launch(Intent)
            null
        }
    }

    private val startForCameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            if (resultCode == Activity.RESULT_OK) {
                val data:Intent? = result.data
                if (data!=null)
                {
                    val uri = data.data
                    Log.d("mProfileUri", ": "+uri)
                   // binding.image.setLocalImage(uri!!, false)
                  /*  val file2= File(uri.toString())
                     val uri2= Uri.fromFile(file2)*/
                    //val file= File(uri.toString())
                    val fUtils = FileUtilsNew(requireContext())
                    val file: File = File(fUtils.getPath(uri!!))
                    val file2 = Uri.fromFile(file)
                    //val url = f.absolutePath

                    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

                    val image = MultipartBody.Part.createFormData("image", file.name, requestBody)

                    val fields: HashMap<String, RequestBody> = HashMap()
                    fields["id"] = (model.username).toRequestBody("text/plain".toMediaTypeOrNull())
                  //  viewModel.updateProfile(image,fields)


                }

            }
            else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }




}
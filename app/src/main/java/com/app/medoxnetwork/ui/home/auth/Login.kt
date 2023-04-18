package com.app.medoxnetwork.auth

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import com.app.medoxnetwork.MainActivity
import com.app.medoxnetwork.R
import com.app.medoxnetwork.base.BaseActivity
import com.app.medoxnetwork.databinding.AlertOtpBinding
import com.app.medoxnetwork.databinding.LoginActivityBinding

import com.app.medoxnetwork.utils.Utility
import com.app.medoxnetwork.viewmodel.RegisterViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject
import java.util.LinkedHashMap

@AndroidEntryPoint
class Login : BaseActivity(),View.OnClickListener {

    lateinit var binding:LoginActivityBinding
    lateinit var mBottomSheetDialog: BottomSheetDialog
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding= LoginActivityBinding.inflate(layoutInflater)
          binding.login.setOnClickListener(this)
           binding.register.setOnClickListener(this)
        binding.forgotpassword.setOnClickListener(this)
        setContentView(binding.root)
        observeData()
    }


    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.login -> {

                if (binding.editemail.text.toString().equals(""))
                {
                   Utility.showToast("Enter registered username",2)
                }
                if (binding.password.text.toString().equals(""))
                {
                    Utility.showToast("Enter valid password",2)
                }
                else
                {
                    login()
                }
            }
            R.id.close
            -> {
               mBottomSheetDialog.dismiss()
            }
            R.id.register
            -> {
                startActivity(Intent(this@Login,Register::class.java).putExtra("register",false))
            }

            R.id.forgotpassword
            -> {
                forgotpasssowrd()
            }


        }
    }

    fun login()
    {
        val params = LinkedHashMap<String, String>()
        params.put("username", binding.editemail.text.toString())
        params.put("password", binding.password.text.toString())
        viewModel.loginUser(params)
    }

    fun forgotpasssowrd()
    {
        if (binding.editemail.text.toString().equals(""))
        {
            Utility.showToast("Enter registered username",2)
        }
        else{
            val params = LinkedHashMap<String, String>()
            params.put("username", binding.editemail.text.toString())
            viewModel.forgotpassword(params)
        }

    }


    fun observeData() {
        viewModel.userLogin.observe(this) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            if (it.status==1)
            {
                Utility.showToast(it.result.msg, 2)
                sp.sessionLogin(it)

                val loginsucces = Intent(this, MainActivity::class.java)
                loginsucces.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(loginsucces)
                finish()

            }
            else
            {
                Utility.showToast(it.result.msg, 2)
            }
        }

        viewModel.resetpassword.observe(this) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            if (it.status==1)
            {
                Utility.showToast(it.result.msg, 2)
            }
            else
            {
                Utility.showToast(it.result.msg, 2)
            }
        }


        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this) {
            loadingProgress(it)
        }

    }




}
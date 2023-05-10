package com.app.medoxnetwork.auth


import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.RemoteException
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.app.medoxnetwork.R
import com.app.medoxnetwork.base.BaseActivity
import com.app.medoxnetwork.databinding.AlertOtpBinding
import com.app.medoxnetwork.databinding.RegisterActivityBinding
import com.app.medoxnetwork.utils.AppConstant

import com.app.medoxnetwork.utils.Utility.isValidEmail
import com.app.medoxnetwork.utils.Utility.showToast

import com.app.medoxnetwork.viewmodel.RegisterViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject


@AndroidEntryPoint
class Register : BaseActivity(),View.OnClickListener,InstallReferrerStateListener  {

    var countDownTimer: CountDownTimer? = null
    lateinit var binding: RegisterActivityBinding
    private lateinit var mBottomSheetDialog: BottomSheetDialog
    lateinit var alertbinding: AlertOtpBinding
    var otp = 0
    private lateinit var mReferrerClient: InstallReferrerClient

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }



    fun init()
    {
        observeData()

        binding.register.setOnClickListener {
            checkValidation()
        }
        binding.llsignin.setOnClickListener{
            val loginsucces = Intent(this, Login::class.java)
            loginsucces.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
           startActivity(loginsucces)
        }

        if (binding.referralcode.text.toString().isEmpty())
        {
            binding.referralcode.setText(AppConstant.referral)
        }

    }




    
    private fun checkValidation() {
         if (binding.referralcode.text.toString().equals("")) {
            showToast("Enter vailid referral code", 2)
        }
       else if (binding.name.text.toString().equals("")) {
            showToast("Enter Full name", 2)
        } else if (binding.email.text.toString().equals("")) {
            showToast("Enter  email address", 2)
        } else if (!isValidEmail(binding.email.text.toString())) {
            showToast("Check Email format", 2)
        } else if (binding.mobile.text.toString().equals("")) {
            showToast("Enter 10 digit mobile number", 2)
        } else if (binding.mobile.text.toString().length < 10) {
            showToast("Required 10 digit mobile number", 2)
        }
         else if (binding.username.text.toString().equals("")) {
             showToast("Enter user name", 2)
         }
         else if (binding.password.text.toString().equals("")) {
             showToast("Enter password", 2)
         }
         else if (binding.confpassword.text.toString().equals("") || !binding.confpassword.text.toString().equals(binding.password.text.toString())) {
             showToast("Password doest not match to Confirm password", 2)
         }
         else {

             val params = LinkedHashMap<String, String>()
                 params.put("reference", binding.referralcode.text.toString())
                 params.put("name", binding.name.text.toString())
                 params.put("email", binding.email.text.toString())
                 params.put("country", binding.countrycode3.selectedCountryName)
                 params.put("mobile", binding.mobile.text.toString())
                 params.put("password", binding.password.text.toString())
                 params.put("username", binding.username.text.toString())
                 params.put("c_password",binding.confpassword.text.toString())
                 Log.d("FFFF3F3F33F3F", "regsiter: "+params.toString())

             register(params)
        }

    }



    fun register(jsonObject: LinkedHashMap<String, String>)
    {
        viewModel.registerMember(jsonObject)
    }

    fun observeData() {
        viewModel.userRegister.observe(this) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            if (it.status==1)
            {
                showToast(it.result.msg,2)
                alertSendotp()

            }
            else
            {
                showToast(it.result.msg,2)
            }
        }

        viewModel.userOTP.observe(this) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            if (it.status==1)
            {
                otp=it.result.otp
                showToast(it.result.msg,2)
               alertbinding.llotp.visibility=View.VISIBLE

            }
            else
            {
                showToast(it.result.msg,2)
            }
        }
        viewModel.verifyOTP.observe(this) {
            Log.d("TAG", "observeData: " + Gson().toJson(it))
            if (it.status==1)
            {
                val loginsucces = Intent(this, Login::class.java)
                loginsucces.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(loginsucces)
                showToast(it.result.msg,2)

            }
            else
            {
                showToast(it.result.msg,2)
            }
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this) {
            loadingProgress(it)
        }

    }




    private fun alertSendotp() {

        if (this::mBottomSheetDialog.isInitialized)
        {
            if (mBottomSheetDialog.isShowing) {
                mBottomSheetDialog.dismiss()
            }
        }


        alertbinding = AlertOtpBinding.inflate(layoutInflater)
        mBottomSheetDialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        mBottomSheetDialog.setCancelable(false)
        mBottomSheetDialog.setCanceledOnTouchOutside(false)
        alertbinding.veryotp.setOnClickListener(this)
        alertbinding.resendotp.setOnClickListener(this)
        // alertbinding.veryotp.alpha = .8f
        // alertbinding.resendotp.alpha = .6f
        //alertbinding.resendotp.isEnabled = false
         alertbinding.veryotp.isEnabled = false
        mBottomSheetDialog.setContentView(alertbinding.root)
        val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(alertbinding.getRoot().getParent() as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        mBottomSheetDialog.show()
        alertbinding.textdesc.text = "Please enter the otp code we have sent to email address " + binding.email.text.toString()

        alertbinding.email.setText(binding.email.text.toString())
        alertbinding.edit1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = alertbinding.edit1.text.toString()
                if (text.length==4) {
                    alertbinding.veryotp.alpha=1f
                    alertbinding.veryotp.isEnabled=true
                    alertbinding.edit1.onEditorAction(EditorInfo.IME_ACTION_DONE)
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        alertbinding.resendotp.setOnClickListener {

            val params = LinkedHashMap<String, String>()
            params.put("email", binding.email.text.toString())
            viewModel.sendOTP(params)
        }

        alertbinding.veryotp.setOnClickListener {
            checkCredential()
        }
        alertbinding.close.setOnClickListener {
            mBottomSheetDialog.dismiss()
        }


        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
        coundDownTimer(30000)
    }

    private fun checkCredential() {
        val otp2 = alertbinding.edit1.text.toString().toInt() //+""+alertbinding.edit2.getText().toString()
        Log.d("otpisi", "" + otp2)
        if (otp == otp2) {

            val params = LinkedHashMap<String, String>()
            params.put("email", binding.email.text.toString())
            viewModel.veryOTP(params)
        } else {
            showToast("Incorrect OTP", 2)
        }
    }


    private fun coundDownTimer(time: Int) {
        countDownTimer = object : CountDownTimer(time.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                alertbinding.time.text = "00:" + millisUntilFinished / 1000 + ""
            }

            override fun onFinish() {
                alertbinding.time.text = "00:00"
                //alertbinding.resendotp.alpha = 1f
               // alertbinding.resendotp.isEnabled = true
            }
        }.start()
    }

    override fun onClick(p0: View?) {

    }

    override fun onInstallReferrerSetupFinished(p0: Int) {
        when (p0) {
            InstallReferrerClient.InstallReferrerResponse.OK -> try {
                // Log.v(TAG, "InstallReferrer conneceted");
                val response: ReferrerDetails = mReferrerClient.getInstallReferrer()
                //handleReferrer(response);
                response.installReferrer
                response.referrerClickTimestampSeconds
                response.installBeginTimestampSeconds
                // Toast.makeText(this, "link id is "+ response.getInstallReferrer(), Toast.LENGTH_SHORT).show();
                if (response.installReferrer != "utm_source=google-play&utm_medium=organic") {
                    binding.referralcode.setText(response.installReferrer)
                } else {
                    binding.referralcode.setText("")
                }
                mReferrerClient.endConnection()
            } catch (e: RemoteException) {
                Log.d("TAG", "onInstallReferrerSetupFinished: "+e.message)
                e.printStackTrace()
            }
            InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED ->                 // Log.w(TAG, "InstallReferrer not supported");
                Toast.makeText(this, "InstallReferrer not supported", Toast.LENGTH_SHORT).show()
            InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE ->                 //  Log.w(TAG, "Unable to connect to the service");
                Toast.makeText(this, "Unable to connect to the service", Toast.LENGTH_SHORT).show()
            else ->                 //  Log.w(TAG, "responseCode not found.");
                Toast.makeText(this, "responseCode not found.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onInstallReferrerServiceDisconnected() {

    }



}
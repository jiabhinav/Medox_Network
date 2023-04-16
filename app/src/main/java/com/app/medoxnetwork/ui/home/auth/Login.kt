package com.app.medoxnetwork.auth

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import com.app.medoxnetwork.R
import com.app.medoxnetwork.base.BaseActivity
import com.app.medoxnetwork.databinding.ActivityLoginBinding
import com.app.medoxnetwork.databinding.AlertOtpBinding

import com.app.medoxnetwork.utils.Utility
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class Login : BaseActivity(),View.OnClickListener {

    lateinit var binding:ActivityLoginBinding
    lateinit var alertbinding: AlertOtpBinding
    lateinit var mBottomSheetDialog: BottomSheetDialog
    var countDownTimer: CountDownTimer? = null
    var typeApi=1
    var otp=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding= ActivityLoginBinding.inflate(layoutInflater)
          binding.sendotp.setOnClickListener(this)
           binding.register.setOnClickListener(this)
        setContentView(binding.root)
    }


    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.sendotp -> {

                if (binding.editemail.text.toString().equals("")||binding.editemail.text.toString().length<10)
                {
                   Utility.showToast("Enter 10 digit mobile number",2)
                }
                else
                {
                    generateOTP(binding.editemail.text.toString())
                }
            }
            R.id.close
            -> {
               mBottomSheetDialog.dismiss()
            }
            R.id.register
            -> {

                startActivity(Intent(this@Login,Register::class.java).putExtra("edit",false))
                finish()
            }
            R.id.veryotp
            -> {
                if (otp.equals(alertbinding.edit1.text.toString()))
                {
                    loginNow()
                }
                else
                {
                    Utility.showToast("Invalid OTP!!",2)


                }

            }

            R.id.resendotp
            -> {
                if (countDownTimer != null) {
                    countDownTimer!!.cancel()
                }
                generateOTP(binding.editemail.text.toString())
                coundDownTimer(30000)

            }

        }
    }


    private fun sendotp() {

        if (this::mBottomSheetDialog.isInitialized)
        {
            if (mBottomSheetDialog.isShowing) {
                mBottomSheetDialog.dismiss()
            }
        }

        //Toast.makeText(Login.this, "OTP has been send on your registered mobile number!", Toast.LENGTH_SHORT).show();
        alertbinding = AlertOtpBinding.inflate(layoutInflater)
        mBottomSheetDialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        mBottomSheetDialog.setCancelable(false)
        mBottomSheetDialog.setCanceledOnTouchOutside(false)
        alertbinding.close.setOnClickListener(this)
        alertbinding.veryotp.setOnClickListener(this)
        alertbinding.resendotp.setOnClickListener(this)
       // alertbinding.veryotp.alpha = .8f
        alertbinding.resendotp.alpha = .6f
        alertbinding.resendotp.isEnabled = false
        alertbinding.veryotp.isEnabled = false
        mBottomSheetDialog.setContentView(alertbinding.root)
        val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(alertbinding.getRoot().getParent() as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        mBottomSheetDialog.show()
        alertbinding.textdesc.text = "Please enter the otp code we have sent to phone number " + binding.editemail.text.toString()

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
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
        coundDownTimer(30000)
    }

    private fun generateOTP(mobile: String) {

        typeApi=1
        showDialogs(this)
        val params = LinkedHashMap<String, String>()
        params.put("phone",mobile)
      /*  RetrofitApiCall.hitApi(
            ApiClient.apiInterFace.generateOtp(params),
            this, typeApi
        )*/
    }





    private fun loginNow() {
        typeApi=2
        showDialogs(this)
        val params = LinkedHashMap<String, String>()
        params.put("mobile_no",binding.editemail.text.toString())
        params.put("password",otp.toString())
       /* RetrofitApiCall.hitApi(
            ApiClient.apiInterFace.login(params),
            this, typeApi
        )*/
    }


    private fun coundDownTimer(time: Int) {
        countDownTimer = object : CountDownTimer(time.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                alertbinding.time.text = "00:" + millisUntilFinished / 1000 + ""
            }

            override fun onFinish() {
                alertbinding.time.text = "00:00"
                alertbinding.resendotp.alpha = 1f
                alertbinding.resendotp.isEnabled = true
            }
        }.start()
    }


}
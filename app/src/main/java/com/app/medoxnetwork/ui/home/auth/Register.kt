package com.app.medoxnetwork.auth


import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.app.medoxnetwork.base.BaseActivity

import com.app.medoxnetwork.databinding.ActivityRegisterBinding
import com.app.medoxnetwork.databinding.AlertOtpBinding


import com.app.medoxnetwork.session.SesssionManager
import com.app.medoxnetwork.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class Register : BaseActivity(),View.OnClickListener{

    var countDownTimer: CountDownTimer? = null
    lateinit var binding: ActivityRegisterBinding
    private lateinit var mBottomSheetDialog: BottomSheetDialog
    lateinit var alertbinding: AlertOtpBinding
   // var alertDialog: AlertDialog? = null
    lateinit var session:SesssionManager
    var user_type = "1"
    var otp = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  utill()
    }











/*    private fun checkValidation() {
        if (binding.name.text.toString().equals("")) {
            utility.showToast("Enter Full name", 2)
        } else if (binding.email.text.toString().equals("")) {
            utility.showToast("Enter  email address", 2)
        } else if (!utility.isValidEmail(binding.email.text.toString())) {
            utility.showToast("Check Email format", 2)
        } else if (binding.editmobile.text.toString().equals("")) {
            utility.showToast("Enter 10 digit mobile number", 2)
        } else if (binding.editmobile.text.toString().length < 10) {
            utility.showToast("Required 10 digit mobile number", 2)
        } else if (listcate.size == 0) {
            utility.showToast("Select Interested Field", 2)
        } else if (binding.checkbusiness.isChecked) {
            checkBusinessValidation()
        } else {
            generateOTP(binding.editmobile.text.toString(),"1")
        }

    }
    private fun checkBusinessValidation() {
        if (binding.editBussinessname.text.toString().equals("")) {
            utility.showToast("Enter Company name", 2)
        } else if (listcate.size == 0) {
            utility.showToast("Select Interested Field", 2)
        } else if (binding.editbussinesmail.text.toString().equals("")) {
            utility.showToast("Enter Company email", 2)
        } else if (!utility.isValidEmail(binding.editbussinesmail.text.toString())) {
            utility.showToast("Check Email format", 2)
        } else if (binding.businesseditmobile.text.toString().equals("")) {
            utility.showToast("Enter Company mobile", 2)
            Constant.setForcusErrro(binding.businesseditmobile, "Enter Company mobile")
        } else if (binding.businesseditmobile.text.toString().length < 10) {
            utility.showToast("Required 10 digit mobile number", 2)
        } else if (binding.companyaddress.text.toString().equals("")) {
            utility.showToast("Enter Company address", 2)
        } else if (state_id == "null" || state_id == "0") {
            utility.showToast("Enter State", 2)
        } else if (city_id == "null") {
            utility.showToast("Enter city", 2)
        } else if (binding.zip.text.toString().equals("")) {
            utility.showToast("Enter Company Zip code", 2)
        } else if (!binding.businesswebsite.text.toString().equals("")) {
            if (!Constant.isWebValidUrl(binding.businesswebsite.text.toString())) {
                utility.showToast("Invailid website url", 2)
                Constant.setForcusErrro(binding.businesswebsite, "Invailid website url")
            } else {
                generateOTP(binding.editmobile.text.toString(),"2")
            }
        } else {
            generateOTP(binding.editmobile.text.toString(),"2")
        }
    }*/

/*    private fun checkCredential() {
        val otp2 = alertbinding.edit1.text.toString() //+""+alertbinding.edit2.getText().toString()
        //+""+alertbinding.edit3.getText().toString()+""+alertbinding.edit4.getText().toString();
        Log.d("otpisi", "" + otp2)
        if (otp == otp2) {
            if (binding.checkbusiness.isChecked) {
                resgisterBusinessUser()
            } else {
                resgisterUser()
            }
        } else {
            //resgisterUser();
            utility.showToast("Incorrect OTP", 2)
        }
    }*/



   /* private fun generateOTP(mobile: String,type:String) {

        typeApi=6
        processingDialog.show("")
        val params = LinkedHashMap<String, String>()
        params.put("phone",mobile)
        params.put("type",type)
        RetrofitApiCall.hitApi(
            ApiClient.apiInterFace.generate_otp_Register(params),
            this, typeApi
        )
    }
*/

/*
    private fun setItemInAlert(list: List<ModelInterestedIn.Result.Interestedin>) {
        alertDropdownLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.alertlistitemlayout,
            null,
            false
        )
        val builder = AlertDialog.Builder(this)
        builder.setView(alertDropdownLayoutBinding.root)
        alertDropdownLayoutBinding.closeDialog.setOnClickListener(this)
        alertDropdownLayoutBinding.applynow.setOnClickListener(this)
        alertDialog = builder.create()
        alertDialog?.show()
        alertDropdownLayoutBinding.recycler.adapter = AlertRecyclerAdapter(this, list, this, listcate)
    }

    private fun setBusnessItemInAlert(list: List<ModelDealInCategory.Result.Dealcategory>) {
        if (list.size > 0) {
            alertLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.alertlistitemlayout,
                null,
                false
            )
            val builder = AlertDialog.Builder(this)
            builder.setView(alertLayoutBinding.root)
            alertLayoutBinding.closeDialog.setOnClickListener(this)
            alertLayoutBinding.applynow.setOnClickListener(this)
            alertDialog = builder.create()
            alertDialog?.show()
            alertLayoutBinding.recycler.adapter = AlertSubCategoryAdapter(this@Register, list, this, businesslistcate)
        }
    }
*/





/*    private fun resgisterBusinessUser()
    {
        typeApi=7
        processingDialog.show("")
        val data= LinkedHashMap<String, Any>()
        Log.d("qdddddd", "regsiter: "+data.toString())
        try {
            data.put("name", binding.name.text.toString())
            data.put("email", binding.email.text.toString())
            data.put("mobile_no", binding.editmobile.text.toString())
            data.put("otp", otp)
            data.put("user_type", user_type)
            data.put("device_type", "A")
            data.put("device_token", session.AUTH_TOKEN)
            data.put("interested_in", getArray())

            //=====================Business User Details====================================


            //=====================Business User Details====================================
            data.put("business_name", binding.editBussinessname.text.toString())
            data.put("category_id", category_id)
            data.put("subcategory_id", getBusinessArray())
            data.put("business_email", binding.editbussinesmail.text.toString())
            data.put("business_phone", binding.businesseditmobile.text.toString())
            data.put("gst_number", binding.gst.text.toString())
            data.put("estiblishmnet_year", binding.establishemnet.text.toString())
            data.put("company_address", binding.companyaddress.text.toString())
            data.put("state", state_id)
            data.put("city", city_id)
            data.put("zip_code", binding.zip.text.toString())
            data.put("about_company", binding.aboutcompany.text.toString())
            data.put("lat", lattitude.toString())
            data.put("long", longitude.toString())
            data.put("brand_name", binding.brandname.text.toString())
            data.put("contact_details", binding.alertnateeditmobile.text.toString())
            data.put("business_website", binding.businesswebsite.text.toString())
            data.put("jobtime_from", jobtime_from)
            data.put("jobtime_to", jobtime_to)
            data.put("job_day", job_day)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        RetrofitApiCall.hitApi(
            ApiClient.apiInterFace.register_profile(data), this, typeApi
        )
    }*/


  /*  private fun alertSendotp() {

        if (this::mBottomSheetDialog.isInitialized)
        {
            if (mBottomSheetDialog.isShowing) {
                mBottomSheetDialog.dismiss()
            }
        }


        alertbinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.alert_otp,
            null,
            false)
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
        alertbinding.textdesc.text = "Please enter the otp code we have sent to phone number " + binding.editmobile.text.toString()

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
*/



    private fun callDashboardApi() {
        //typeApi=4
        //processingDialog.show("")
       // RetrofitApiCall.hitApi(ApiClient.apiInterFace.myProfile(), this, typeApi)
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

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


}
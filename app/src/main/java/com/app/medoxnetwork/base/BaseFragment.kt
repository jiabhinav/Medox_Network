package com.app.medoxnetwork.base

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import com.app.medoxnetwork.R
import com.app.medoxnetwork.session.SesssionManager
import javax.inject.Inject


open class BaseFragment : Fragment() {
    lateinit var mContext: Context

    var alertDialog:AlertDialog?=null

    @Inject
    lateinit var sp: SesssionManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    var pDialog: Dialog? = null

    fun showDialogs(context: Activity) {
            pDialog = Dialog(context)
            pDialog?.setContentView(R.layout.progress_bar_layout)
            pDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            pDialog!!.setCancelable(false)
            if (!(context).isFinishing) {
                if (!pDialog!!.isShowing) {
                    pDialog!!.show()
                }
            }

            pDialog!!.setOnCancelListener { pDialog!!.dismiss() }
            pDialog!!.setOnKeyListener { dialog, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (pDialog != null) pDialog!!.dismiss()
                }
                true
            }
    }

    fun dismiss() {
        if (pDialog != null) {
            pDialog!!.dismiss()
            pDialog = null
        }
    }



    fun loadingProgress(isLoding:Boolean)
    {
        val activity: Activity? = activity
        if(activity != null){
            if(isLoding) {
                showDialogs(requireActivity())
            } else {
                dismiss()
            }
        }

    }



    fun checkConnection(context: Activity?) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("No internet Connection")
        builder.setMessage("Please turn on internet connection to continue")
        builder.setCancelable(false)
        builder/*.setPositiveButton(
            "Retry!!"
        ) { dialog, which ->
            retryCallAPI()
        }*/.setNegativeButton(
            "Close"
        ) { dialog, which -> }
         alertDialog = builder.create()
          alertDialog?.setCancelable(false)
        alertDialog?.show()
    }


}
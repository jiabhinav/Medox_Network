package com.app.medoxnetwork.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.KeyEvent
import com.app.medoxnetwork.R

import com.app.medoxnetwork.session.SesssionManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

open class BottomSheetDialog : BottomSheetDialogFragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    @Inject
    lateinit var sp: SesssionManager

    var mDialog: Dialog? = null

    fun showDialogs(context: Activity) {
        mDialog = Dialog(context)
        mDialog?.setContentView(R.layout.progress_bar_layout)
        mDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog!!.setCancelable(false)
        if (!(context).isFinishing) {
            if (!mDialog!!.isShowing) {
                mDialog!!.show()
            }
        }
        mDialog!!.setOnCancelListener { mDialog!!.dismiss() }
        mDialog!!.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (mDialog != null)
                    mDialog!!.dismiss()
            }
            true
        }

    }

    fun mDismis() {
        if (mDialog != null) {
            mDialog!!.dismiss()
            mDialog = null
        }
    }

    fun loadingProgress(isLoding:Boolean)
    {
        if (isLoding) {
            showDialogs(requireActivity())
        } else {
            dismiss()
        }
    }
}
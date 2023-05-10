package com.app.medoxnetwork.utils

import android.content.BroadcastReceiver
import android.content.Context
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.InstallReferrerClient
import android.content.Intent
import android.os.RemoteException
import android.util.Log
import com.app.medoxnetwork.utils.PackageStatusReceiver
import com.android.installreferrer.api.ReferrerDetails
import com.app.medoxnetwork.utils.AppConstant.referral

class PackageStatusReceiver : BroadcastReceiver(), InstallReferrerStateListener {
    private var referrerClient: InstallReferrerClient? = null
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != null) {
            if (intent.action == Intent.ACTION_PACKAGE_FIRST_LAUNCH) {
                referrerClient = InstallReferrerClient.newBuilder(context).build()
                referrerClient?.startConnection(this)
            }
        }
    }

    override fun onInstallReferrerSetupFinished(responseCode: Int) {
        when (responseCode) {
            InstallReferrerClient.InstallReferrerResponse.OK -> {
                Log.d(LOG_TAG, "InstallReferrer Response.OK")
                try {
                    val response = referrerClient!!.installReferrer
                    val referrer = response.installReferrer
                    /*val clickTimestamp = response.referrerClickTimestampSeconds
                    val installTimestamp = response.installBeginTimestampSeconds*/
                    referral=referrer
                    Log.d(LOG_TAG, "InstallReferrer $referrer")
                    referrerClient!!.endConnection()
                } catch (e: RemoteException) {
                    Log.e(LOG_TAG, "" + e.message)
                }
            }
            InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> Log.w(
                LOG_TAG,
                "InstallReferrer Response.FEATURE_NOT_SUPPORTED"
            )
            InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> Log.w(
                LOG_TAG,
                "InstallReferrer Response.SERVICE_UNAVAILABLE"
            )
            InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED -> Log.w(
                LOG_TAG,
                "InstallReferrer Response.SERVICE_DISCONNECTED"
            )
            InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR -> Log.w(
                LOG_TAG,
                "InstallReferrer Response.DEVELOPER_ERROR"
            )
        }
    }

    override fun onInstallReferrerServiceDisconnected() {
        Log.w(LOG_TAG, "InstallReferrer onInstallReferrerServiceDisconnected()")
    }

    companion object {
        protected val LOG_TAG = PackageStatusReceiver::class.java.simpleName
    }
}
package com.app.medoxnetwork.utils
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.medoxnetwork.MyApp
import com.app.medoxnetwork.retrofit.ErrorModel
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object  Utility {

    fun <T> getError(result: retrofit2.Response<T>): ErrorModel {
        try {
            val jObjError = JSONObject(result.errorBody()!!.string())

            val model = ErrorModel().apply {
                this.status = jObjError.getInt("status")

                if (jObjError.getString("message").toString().isNullOrEmpty()) {
                    this.message = jObjError.getString("error").toString()
                } else {
                    this.message = jObjError.getString("message").toString()
                }
            }
            return model
        } catch (e: Exception) {
            val model = ErrorModel().apply {
                this.message = result.message()
                this.status = result.code()
            }
            return model
        }


    }

    fun showToast(msg: String, type: Int) {
        //todo 1=uper,2=center,3=bottom
        var msg = msg
        if (type == 1) {
            val t = Toast.makeText(MyApp.instance.applicationContext, msg + "", Toast.LENGTH_SHORT)
            t.setGravity(Gravity.TOP, 0, 0)
            t.show()
        } else if (type == 2) {
            val t = Toast.makeText(MyApp.instance.applicationContext, "".let { msg += it; msg }, Toast.LENGTH_SHORT)
            t.setGravity(Gravity.CENTER, 0, 0)
            t.show()
        } else {
            val t = Toast.makeText(MyApp.instance.applicationContext, msg + "", Toast.LENGTH_SHORT)
            t.setGravity(Gravity.BOTTOM, 0, 0)
            t.show()
        }
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    @SuppressLint("NewApi")
    fun GetWindowStatusBarColor(activity: Activity, color: Int) {
        val window: Window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, color)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI_AWARE
                    )
                ) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                } else if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    return true
                }
//                else{
//                        val activeNetworkInfo = connectivityManager.activeNetworkInfo
//                        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
//                            return true
//                        }
//                }
            }
        }
        return false
    }
    var alertDialog: AlertDialog? = null
    const val MULTIPLE_PERMISSIONS = 10
    lateinit var REQUIRED_PERMISSIONS: Array<String>
    lateinit var REQUIRED_CAMERA_PERMISSIONS: Array<String>

    fun hasPermissions(context: Activity, permissions: Array<String>): Boolean {
        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(context, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                context, listPermissionsNeeded.toTypedArray(), MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }
    fun alertPermission(context: Activity, dismis: DismissSetting? = null) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("\"Need Permissions\"")
        builder.setMessage("\"This app needs permission to use this feature. You can grant them in app settings.\"")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("GOTO SETTINGS")
        { dialogInterface, which ->
            alertDialog?.dismiss()
            alertDialog = null
            openSettings(context)
        }
        builder.setNegativeButton("Cancel") { dialogInterface, which ->
            alertDialog?.dismiss()
            alertDialog = null
            if (dismis != null) {
                dismis.alertSetting()
            }


        }
        alertDialog = builder.create()
        alertDialog?.setCancelable(false)
        alertDialog!!.show()
    }

    fun openSettings(context: Activity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivityForResult(intent, 101)
    }



    fun getAttendanceDate(currtdate:String):Date
    {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = dateFormat.parse(currtdate)
        return date
    }

    fun permissionCameraList(): Array<String> {

      return  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {

            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE)

        } else {

            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES)
        }


    }



}
package com.app.medoxnetwork.session

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.app.medoxnetwork.BuildConfig
import com.app.medoxnetwork.MainActivity
import com.app.medoxnetwork.MyApp
import com.app.medoxnetwork.auth.Login
import com.app.medoxnetwork.model.ModelUser


import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SesssionManager @Inject constructor(@ApplicationContext context: Context?) {

    val sp_login: SharedPreferences = context!!.getSharedPreferences(BuildConfig.SESSION, Context.MODE_PRIVATE)
    var sp_editor: SharedPreferences.Editor = sp_login.edit()

    var USER_JSON = "userlogin"
    var LANGUAGE = "lang"
    var IS_SKIP_Video = "skipeVideo"

    companion object {
        private var instance: SesssionManager? = null
        fun getInstance(): SesssionManager? {
            if (instance == null) {
                synchronized(SesssionManager::class.java) {
                    if (instance == null) {
                        instance = SesssionManager(MyApp.instance)
                    }
                }
            }
            return instance
        }
    }

    private var instance: SesssionManager? = null

    fun getInstance(context: Context?): SesssionManager? {
        if (instance == null) {
            synchronized(SesssionManager::class.java) {
                if (instance == null) {
                    instance = SesssionManager(context)
                }
            }
        }
        return instance
    }


    fun sessionLogin(user: ModelUser?) {
        val userdata = Gson().toJson(user)
        sp_editor.putString(USER_JSON, userdata)
        sp_editor.commit()

    }

    fun logoutSession(): Boolean {
        sp_editor.clear()
        sp_editor.commit()
        return true
    }


    fun getUser(): ModelUser? {

        try {
            val gson = Gson()
            val list = gson.fromJson(sp_login.getString(USER_JSON, "0"), ModelUser::class.java)
            Log.d("ddfefefef", "getUserDetails: " + Gson().toJson(list))
            // list = gson.fromJson(new Gson().toJson(sp_login.getString(USER_JSON, "0")), new TypeToken< UserModel.User>(){}.getType());
            return list
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun setLanguage(lang: String) {

        sp_editor.putString(LANGUAGE, lang)
        sp_editor.commit()
    }

    fun getLanguage(): String? {
        return sp_login.getString(LANGUAGE, "")
    }


    fun isSKipVideo(): Boolean {
        return sp_login.getBoolean(IS_SKIP_Video, false)
    }

    fun setSKipVideo(isSync: Boolean) {
        sp_editor.putBoolean(IS_SKIP_Video, isSync)
        sp_editor.commit()
    }


    fun checkLogin(context: Context) {
        if (isLoggedIn() == null) {
            val loginsucces = Intent(context, Login::class.java)
            loginsucces.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(loginsucces)

        } else {
            val loginsucces = Intent(context, MainActivity::class.java)
            loginsucces.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(loginsucces)
        }
    }

    fun isLoggedIn(): String? {
        return sp_login.getString(USER_JSON, null)
    }

}













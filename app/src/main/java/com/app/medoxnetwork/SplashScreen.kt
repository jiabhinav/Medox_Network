package com.app.medoxnetwork

import android.os.Build
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import com.app.medoxnetwork.base.BaseActivity
import com.app.medoxnetwork.databinding.ActivitySplashScreenBinding

import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreen : BaseActivity() {

    lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val an2: Animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        binding.logo.startAnimation(an2)


        val tim=Timer()
        tim.schedule(object : TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            override fun run() {
                sp.checkLogin(this@SplashScreen)
                finish()
            }
        }, 2000)



    }

}
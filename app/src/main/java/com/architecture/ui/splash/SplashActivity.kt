package com.architecture.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer

import com.architecture.R
import com.architecture.base.*
import com.architecture.ui.activity.HomeActivity
import com.architecture.util.Util

class SplashActivity : BaseActivity() {

    private val SPLASH_DURATION: Long = 2000
    lateinit var mViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initWork()
        otherWork()
    }


    private fun otherWork() {

        mViewModel.proceedAhead.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        })

        Handler(Looper.getMainLooper()).postDelayed({ mViewModel.proceedAhead.value = true }, SPLASH_DURATION)

    }

    private fun initWork() {
        setContentView(R.layout.activity_splash)
        Util.updateStatusBarColor(R.color.black,this as FragmentActivity)
        mViewModel = androidx.lifecycle.ViewModelProvider(this).get(SplashViewModel::class.java)
    }


}

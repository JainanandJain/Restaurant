package com.architecture.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.architecture.R
import com.architecture.base.*
import com.architecture.databinding.ActivityMainBinding

class HomeActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    lateinit var context: Context
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    lateinit var mBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initWork()
        otherWork()
    }


    fun showLogo(show: Boolean) {
        if (show) mBinding.imgLogo.visible()
        else mBinding.imgLogo.gone()
    }

    fun showBack(show: Boolean) {
        if (show) mBinding.imgBack.visible()
        else mBinding.imgBack.gone()
    }

    fun setTitle(title: String) {
        if (title.isEmptyy()) mBinding.tvTitle.gone()
        else {
            mBinding.tvTitle.visible()
            mBinding.tvTitle.text = title
        }
    }

    private fun otherWork() {
        backPressWork()
    }

    private fun backPressWork() {
        mBinding.imgBack.push()?.setOnClickListener {
            onBackPressed()
        }
    }


    private fun initWork() {
        context = this
        setStatusBarColor(R.color.white)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.apply {
            toolbar.title = ""
            setSupportActionBar(toolbar)
        }

        navController = setNavigationController()

        showBack(false)
        showLogo(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun setNavigationController(): NavController {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener(this)
        navController.addOnDestinationChangedListener(this)
        return navController
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {

    }

    override fun setTitle(title: CharSequence?) {
        mBinding.tvTitle.text = title
    }


    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            val f = getCurrentFragment(BaseFragment::class.java)
            f?.onActivityResult(requestCode, resultCode, data)
    }


    override fun onBackPressed() {

        when {
            navController.currentDestination?.id == R.id.HomeFragment -> {
                finish()
            }
            else -> super.onBackPressed()
        }

    }



}

package com.architecture.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.architecture.util.*
import java.util.*

var API_SUCCESS = 200
var API_ERROR = 201
var SUBSCRIPTION_EXPIRED = 202
var SESSION_EXPIRED = 203


abstract class BaseActivity : AppCompatActivity(), CommonCallbacks {

    lateinit var mBaseViewModel: BaseActivityViewModel
    var isSecond = false

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setupBasics()
        setStatusBarColor("#F5333F")

        manageGlobalObservers()

    }

    private fun manageGlobalObservers() {
        MainApplication.get().globalObservers.manageGlobalObservers(this@BaseActivity)
    }

    fun setStatusBarColor(color: String) {
        Util.updateStatusBarColor(color, this as FragmentActivity)
    }

    fun setStatusBarColor(color: Int) {
        Util.updateStatusBarColor(color, this as FragmentActivity)
    }

    private fun setupBasics() {
        mBaseViewModel = androidx.lifecycle.ViewModelProvider(this, MyViewModelProvider(this as AsyncViewController)).get(
            BaseActivityViewModel::class.java)
        setObservers()
    }


    override fun showProgressDialog() {
        if (mBaseViewModel.progressDialogStatus.value == null || !mBaseViewModel.progressDialogStatus.value.equals(
                "_show")) {
            mBaseViewModel.progressDialogStatus.value = "_show"
        }
    }

    override fun hideProgressDialog() {
        if (mBaseViewModel.progressDialogStatus.value == null || !mBaseViewModel.progressDialogStatus.value.equals(
                "_hide")) {
            mBaseViewModel.progressDialogStatus.value = "_hide"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val bf = getCurrentFragment(BaseFragment::class.java)
        if (bf?.handlingBackPress() == false) {
            showAppCloseDialog()
        }
    }


    private fun setObservers() {
        mBaseViewModel.keyboardController.observe(this, Observer {
            runOnUiThread {
                if (it) {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                } else {
                    val imm =
                        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                }
            }
        })


    }


    override fun getSharedModel(): BaseActivityViewModel {
        return mBaseViewModel
    }




    override fun hideAlertDialog() {
        mBaseViewModel.alertDialogController.value = "null"
    }

    override fun hideKeyboard() {
        mBaseViewModel.keyboardController.value = false
    }

    override fun hideKeyboard(v: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    override fun showKeyboard() {
        mBaseViewModel.keyboardController.value = true
    }


    override fun setupActionBarWithNavController(toolbar: Toolbar) {}

    override fun forceBack() {
        super.onBackPressed()
    }

    private fun showAppCloseDialog() {
        if (!isSecond) {
            isSecond = true
        } else {
            finish()
        }
    }

}
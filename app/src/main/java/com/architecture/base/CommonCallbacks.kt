package com.architecture.base

import android.view.View
import androidx.appcompat.widget.Toolbar


interface CommonCallbacks : AsyncViewController {

    fun setupActionBarWithNavController(toolbar: Toolbar)

    fun hideKeyboard(v: View)

    fun forceBack()

    fun getSharedModel() : BaseActivityViewModel

}
package com.architecture.util

import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UtilJava {

    fun some(str : String){

    }


    fun jm(navigationView: BottomNavigationView) {
        navigationView.setOnNavigationItemSelectedListener { item: MenuItem? -> false }
    }

    companion object {
        // ("US","USD","000123456789", BankAccountTokenParams.Type.Individual,"Avinash","110000000");
        var liveData: MutableLiveData<String>? = null
        fun convertUtcToLocal(ourDate: String?, inputFormat: String?, outputFormat: String?): String? {
            var ourDate = ourDate
            try {
                val formatter = SimpleDateFormat(inputFormat)
                formatter.timeZone = TimeZone.getTimeZone("UTC")
                val value = formatter.parse(ourDate)
                val dateFormatter = SimpleDateFormat(outputFormat) //this format changeable
                dateFormatter.timeZone = TimeZone.getDefault()
                ourDate = dateFormatter.format(value)

                //Log.d("ourDate", ourDate);
            } catch (e: Exception) {
                ourDate = "00-00-0000 00:00"
            }
            return ourDate
        }
    }
}
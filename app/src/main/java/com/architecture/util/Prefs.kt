package com.architecture.util

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.preference.PreferenceManager

import com.google.gson.Gson
import com.architecture.base.MainApplication

class Prefs {

    val PREF_AUTH_TOKEN = "_PREF_AUTH_TOKEN"
    val PREF_NAME_GLOBAL = "_global_pref"

    private var sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(MainApplication.get().getContext())

    init {
        instance = this
    }

    val gson = Gson()

    companion object {
        private var instance: Prefs? = null
        fun get(): Prefs {
            if (instance == null) {
                instance = Prefs()
            }
            return instance!!
        }
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    var deviceToken: String
        get() {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            return sF.getString(PREF_AUTH_TOKEN, "") ?: ""
        }
        set(value) {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            sF.edit().putString(PREF_AUTH_TOKEN, value).apply()
        }


}
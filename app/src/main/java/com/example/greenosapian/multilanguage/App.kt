package com.example.greenosapian.multilanguage

import android.app.Application
import android.content.Context

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun setLanguagePref(localeKey: String){
        val pref = getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
        pref.putString(LOCALE, localeKey)
        pref.apply()
    }

    fun getLanguagePref():String? {
        val pref = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return pref.getString(LOCALE, "en")
    }

    companion object{
        var instance:App? = null
        const val PREFS: String = "SHARED_PREFS"
        const val LOCALE: String = "LOCALE"
    }
}
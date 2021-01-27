package com.example.greenosapian

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.greenosapian.multilanguage.LocaleManager
import java.util.*


class MainActivity : AppCompatActivity() {

    private var mLangReceiver:BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
    }

    private fun setupToolbar() {
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase!!))
    }

//    private fun setUpBroadCastReceiver() : BroadcastReceiver {
//        if (mLangReceiver == null) {
//            mLangReceiver = object : BroadcastReceiver() {
//                override fun onReceive(context: Context, intent: Intent) {
//                    println("language: ${Locale.getDefault().language}")
//                    when(Locale.getDefault().language){
//                        "en" -> {
//                            println("english selected")
//                        }
//                        "hi" ->{
//                            println("Hindi selected")
//
//                        }
//                    }
//                }
//            }
//            val filter = IntentFilter(Intent.ACTION_LOCALE_CHANGED)
//            registerReceiver(mLangReceiver, filter)
//        }
//
//        return mLangReceiver!!
//    }

}
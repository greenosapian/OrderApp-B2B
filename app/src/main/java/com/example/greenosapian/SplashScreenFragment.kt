package com.example.greenosapian

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.greenosapian.database.Account
import com.example.greenosapian.database.GreenDatabase
import com.example.greenosapian.databinding.FragmentSplashScreenBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_splash_screen, container, false)


//        TODO("uncomment this")
//        chooseNextScreen()
        testingProfilePage()
//        databaseTesting()
        return binding.root
    }

    private fun testingProfilePage() {
        this.findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToProfilePageFragment())
    }

    private fun databaseTesting() {
        CoroutineScope(Dispatchers.Main).launch {
            val application = requireNotNull(this@SplashScreenFragment.activity).application
            val datasource = GreenDatabase.getInstance(application).dao
            withContext(Dispatchers.IO) {
//                println("Record added successfully:")
            }
        }
    }

    //choosing next fragment based on whether user have registered or not
    private fun chooseNextScreen() {
        val currentUser = Firebase.auth.currentUser
        println("CurrentUser: $currentUser")

        println("PhoneNo: ${currentUser?.phoneNumber}")
        println("username: ${currentUser?.displayName}")

        if (currentUser == null) {
            //go to signup page
            this@SplashScreenFragment.findNavController()
                .navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToSignUpFragment())
        } else {
            //go to home page
            this@SplashScreenFragment.findNavController()
                .navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomePageFragment())
        }
    }

    private suspend fun getUserFromDatabase(): Account? {
        val application = requireNotNull(this.activity).application
        val datasource = GreenDatabase.getInstance(application).dao
        var userAccount: Account? = null
        withContext(Dispatchers.IO) {
            userAccount = datasource.getUserAccount()
        }
        return userAccount
    }
}
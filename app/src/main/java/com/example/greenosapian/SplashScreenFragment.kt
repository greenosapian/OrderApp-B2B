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
import com.example.greenosapian.network.ElasticApi
import com.example.greenosapian.orderlist.OrderRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit


class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_splash_screen, container, false)


        deleteCache()

        chooseNextScreen()
//        testingProfilePage()
//        databaseTesting()
        return binding.root
    }

    private fun deleteCache() {
        CoroutineScope(Dispatchers.IO).launch {
            val application = requireNotNull(this@SplashScreenFragment.activity).application
            GreenDatabase.getInstance(application).dao.deleteAllCachedVegetables()
        }
    }

    //choosing next fragment based on whether user have registered or not
    private fun chooseNextScreen() {
        CoroutineScope(Dispatchers.Main).launch {
            val phoneNumber = Firebase.auth.currentUser?.phoneNumber
            if (!phoneNumber.isNullOrEmpty() && doesUserExist(phoneNumber)) {
                this@SplashScreenFragment.findNavController()
                    .navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomePageFragment(phoneNumber))
            } else {
                this@SplashScreenFragment.findNavController()
                    .navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToSignUpFragment())
            }
        }
    }
}
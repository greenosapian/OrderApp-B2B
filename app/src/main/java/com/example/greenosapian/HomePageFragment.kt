package com.example.greenosapian

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.greenosapian.database.GreenDatabase
import com.example.greenosapian.databinding.FragmentHomePageBinding
import com.example.greenosapian.network.ElasticApi
import com.example.greenosapian.network.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess

class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)
        // Inflate the layout for this fragment
        binding.deleteAccountButton.isEnabled = false
        var userPhoneNumber = HomePageFragmentArgs.fromBundle(requireArguments()).userPhoneNumber
        CoroutineScope(Dispatchers.Main).launch {
            user = ElasticApi.retrofitService.getUser(userPhoneNumber)
            binding.deleteAccountButton.isEnabled = true
            binding.userTV.text = user.name
        }

        binding.signOutButton.setOnClickListener{
            this@HomePageFragment.findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToSplashScreenFragment())
            Toast.makeText(context, "Signed Out Successful", Toast.LENGTH_LONG).show()
            Firebase.auth.signOut()
        }

        binding.deleteAccountButton.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch{
                val response =  ElasticApi.retrofitService.deleteUser(user.phoneNumber)
                if(response._shards?.successful == 1){
                    Firebase.auth.currentUser?.delete()
                    Toast.makeText(context, "User Deleted Successfull: ${response._shards.successful}", Toast.LENGTH_LONG).show()
                    this@HomePageFragment.findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToSplashScreenFragment())
                }
            }
        }

        binding.lifecycleOwner = this
        return binding.root
    }


}
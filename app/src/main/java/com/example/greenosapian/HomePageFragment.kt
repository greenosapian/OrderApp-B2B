package com.example.greenosapian

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.greenosapian.database.GreenDatabase
import com.example.greenosapian.databinding.FragmentHomePageBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess

class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)
        // Inflate the layout for this fragment

        binding.signOutButton.setOnClickListener{
            this@HomePageFragment.findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToSplashScreenFragment())
            Toast.makeText(context, "Signed Out Successfull", Toast.LENGTH_LONG).show()
            Firebase.auth.signOut()
        }

        binding.deleteAccountButton.setOnClickListener{
            Firebase.auth.currentUser?.delete()
            Toast.makeText(context, "User Deleted Successfull", Toast.LENGTH_LONG).show()
            this@HomePageFragment.findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToSplashScreenFragment())
        }

        Firebase.auth.currentUser?.displayName?.let {
            binding.userTV.text = it
        }

        return binding.root
    }


}
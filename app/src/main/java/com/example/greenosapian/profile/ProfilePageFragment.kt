package com.example.greenosapian.profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.greenosapian.R
import com.example.greenosapian.databinding.FragmentProfilePageBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class ProfilePageFragment : Fragment() {
    private val TAG = "ProfilePageFragment"
    private lateinit var binding: FragmentProfilePageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_page, container, false)
        // Inflate the layout for this fragment

        binding.profileSubmitButton.setOnClickListener{
            submitProfile()
        }
        return binding.root
    }

    fun submitProfile(){
        val userName = binding.userNameET.text.toString().toUpperCase()
        if(userName.length == 0){
            Toast.makeText(context, "User Name cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = userName
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                    this.findNavController().navigate(ProfilePageFragmentDirections.actionProfilePageFragmentToHomePageFragment())
                } else{
                    Log.d(TAG, "user profile updating failure")
                }
            }
    }

}
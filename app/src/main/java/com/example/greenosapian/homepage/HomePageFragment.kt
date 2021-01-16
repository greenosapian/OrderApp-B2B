package com.example.greenosapian.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.greenosapian.R
import com.example.greenosapian.databinding.FragmentHomePageBinding
import com.example.greenosapian.network.ElasticApi
import com.example.greenosapian.network.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home_page, container, false)

        binding.orderButton.setOnClickListener{
            println("order button clicked")
        }

        binding.historyButton.setOnClickListener{
            println("history button clicked")
        }

        binding.contactUsButton.setOnClickListener{
            println("contact us button clicked")
        }


        binding.lifecycleOwner = this
        return binding.root
    }


}



//
//
//
//binding.deleteAccountButton.isEnabled = false
//var userPhoneNumber = HomePageFragmentArgs.fromBundle(
//    requireArguments()
//).userPhoneNumber
//CoroutineScope(Dispatchers.Main).launch {
//    user = ElasticApi.retrofitService.getUser(userPhoneNumber)
//    binding.deleteAccountButton.isEnabled = true
//    binding.userTV.text = user.name
//}
//
//binding.signOutButton.setOnClickListener{
//    this@HomePageFragment.findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToSplashScreenFragment())
//    Toast.makeText(context, "Signed Out Successful", Toast.LENGTH_LONG).show()
//    Firebase.auth.signOut()
//}
//
//binding.deleteAccountButton.setOnClickListener{
//    CoroutineScope(Dispatchers.Main).launch{
//        val response =  ElasticApi.retrofitService.deleteUser(user.phoneNumber)
//        if(response._shards?.successful == 1){
//            Firebase.auth.currentUser?.delete()
//            Toast.makeText(context, "User Deleted Successfull: ${response._shards.successful}", Toast.LENGTH_LONG).show()
//            this@HomePageFragment.findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToSplashScreenFragment())
//        }
//    }
//}
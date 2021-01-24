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
            findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToOrderListFragment())
        }

        binding.historyButton.setOnClickListener{
            findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToHistoryFragment())
        }

        binding.contactUsButton.setOnClickListener{
//            println("contact us button clicked")
            findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToContactUs())
        }



        binding.lifecycleOwner = this
        return binding.root
    }


}
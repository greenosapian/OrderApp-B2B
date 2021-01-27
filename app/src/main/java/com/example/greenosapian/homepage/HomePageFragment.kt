package com.example.greenosapian.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.greenosapian.R
import androidx.appcompat.widget.Toolbar
import com.example.greenosapian.databinding.FragmentHomePageBinding

class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home_page, container, false
        )

        binding.orderButton.setOnClickListener {
            findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToOrderListFragment())
        }

        binding.historyButton.setOnClickListener {
            findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToHistoryFragment())
        }

        binding.contactUsButton.setOnClickListener {
//            println("contact us button clicked")
            findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToContactUs2())
        }


        setUpToolbar()
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun setUpToolbar() {
        val navController = findNavController()
        val appBarConfigurations = AppBarConfiguration(setOf(R.id.homePageFragment), binding.drawerLayout)
        binding.toolbarLayout.toolbar.setupWithNavController(navController, appBarConfigurations)
    }

    override fun onStop() {
        binding.toolbarLayout.toolbar.title = "GreenoSapian"
        super.onStop()
    }
}
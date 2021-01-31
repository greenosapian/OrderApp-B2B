package com.example.greenosapian.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.greenosapian.R
import androidx.lifecycle.ViewModelProvider
import com.example.greenosapian.database.GreenDatabase
import com.example.greenosapian.databinding.FragmentHomePageBinding
import com.example.greenosapian.history.HistoryViewModel
import com.example.greenosapian.history.HistoryViewModelFactory

class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var viewmodel: HomePageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home_page, container, false
        )


        val application = requireActivity().application
        val repository = HomePageRepository(GreenDatabase.getInstance(application).dao)
        val viewModelFactory = HomePageViewModelFactory(repository, application)
        viewmodel = ViewModelProvider(this, viewModelFactory).get(HomePageViewModel::class.java)

        setUpClickListeners()

        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun setUpClickListeners() {
        binding.orderButton.setOnClickListener {
            findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToOrderListFragment())
        }

        binding.historyButton.setOnClickListener {
            findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToHistoryFragment())
        }

        binding.contactUsButton.setOnClickListener {
            findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToContactUs2())
        }
    }

}
package com.example.greenosapian.contactus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.greenosapian.R
import com.example.greenosapian.databinding.FragmentContactUsBinding


class ContactUs : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =
            DataBindingUtil.inflate<FragmentContactUsBinding>(inflater, R.layout.fragment_contact_us, container, false)

        binding.urlIV.setOnClickListener {
            openSiteUrl()
        }
        binding.urlTV.setOnClickListener {
            openSiteUrl()
        }

        return binding.root
    }

    private fun openSiteUrl() {
        val getIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.site_url_value)))
        startActivity(getIntent)
    }

}
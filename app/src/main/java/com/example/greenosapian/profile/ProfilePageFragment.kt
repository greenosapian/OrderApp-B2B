package com.example.greenosapian.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.greenosapian.R
import com.example.greenosapian.databinding.FragmentProfilePageBinding
import com.google.android.gms.location.LocationServices

class ProfilePageFragment : Fragment() {
    private lateinit var binding: FragmentProfilePageBinding
    lateinit var viewModel: ProfilePageViewModel
    private var location: Location? = null

    companion object {
        const val TAG = "@ProfilePageFragment"
        const val PICK_PROFILE_IMAGE = 1
        const val LOCATION_PERMISSION_CODE = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_page, container, false)


        viewModel = ViewModelProvider(this).get(ProfilePageViewModel::class.java)
        binding.viewmodel = viewModel

        binding.submitButton.setOnClickListener {
            onSubmit()
        }
        binding.profilePicIV.setOnClickListener {
            pickProfilePic()
        }

        getLocation()

        viewModel.navigateToHomeFragment.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(ProfilePageFragmentDirections.actionProfilePageFragmentToHomePageFragment())
                viewModel.navigationToHomeFragmentComplete()
            }
        })

        binding.lifecycleOwner = this
        return binding.root
    }

    private fun onSubmit() {
        val name = binding.nameET.text.toString()
        val address = binding.addressET.text.toString()

        binding.nameTF.error = null
        binding.addressTF.error = null

        if (name.isEmpty()) {
            binding.nameTF.error = getString(R.string.required_field)
            return
        } else if (address.isEmpty()) {
            binding.addressTF.error = getString(R.string.required_field)
            return
        }

        viewModel.insertUser(name, address, location)
    }


    private fun getLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context?.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //get location here
                LocationServices.getFusedLocationProviderClient(requireActivity())
                    .lastLocation.addOnSuccessListener {
                        location = it
                        Log.i(TAG, "location: $it")
                    }.addOnFailureListener {
                        Log.i(TAG, it.toString())
                    }
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    LOCATION_PERMISSION_CODE
                )
            }
        }
    }

    private fun pickProfilePic() {
        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
            .setType("image/*")
        startActivityForResult(pickIntent, PICK_PROFILE_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PICK_PROFILE_IMAGE -> if (resultCode == Activity.RESULT_OK) {
                data?.data?.let { uri ->
                    viewModel.profilePicUri.value = uri
                }
            }
        }
    }

}
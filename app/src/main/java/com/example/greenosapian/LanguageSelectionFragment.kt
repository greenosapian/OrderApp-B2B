package com.example.greenosapian

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.greenosapian.databinding.FragmentLanguageSelectionBinding
import com.example.greenosapian.multilanguage.LocaleManager


class LanguageSelectionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLanguageSelectionBinding>(
            inflater,
            R.layout.fragment_language_selection,
            container,
            false
        )

        binding.englishSelectButton.setOnClickListener {
            LocaleManager.setNewLocale(requireContext(), "en")
            requireActivity().recreate()
            navigateToSignUpFragment()
        }

        binding.hindiSelectButton.setOnClickListener {
            LocaleManager.setNewLocale(requireContext(), "hi")
            requireActivity().recreate()
            navigateToSignUpFragment()
        }

        return binding.root
    }

    fun navigateToSignUpFragment() {
        this.findNavController()
            .navigate(LanguageSelectionFragmentDirections.actionLanguageSelectionFragmentToSignUpFragment())
    }


}
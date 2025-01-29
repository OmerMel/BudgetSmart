package com.example.budgetsmart.presentation.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.budgetsmart.R
import com.example.budgetsmart.presentation.ui.settings.profileSettings.ProfileSettingsFragment
import com.google.android.material.button.MaterialButton

class SettingsFragment : Fragment() {

    private lateinit var settings_BTN_toProfileSettings: MaterialButton
    private lateinit var settings_LL_profileSettings: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews()
        initViews()
    }

    private fun findViews() {
        settings_BTN_toProfileSettings = view?.findViewById(R.id.settings_BTN_toProfileSettings)!!
        settings_LL_profileSettings = view?.findViewById(R.id.settings_LL_profileSettings)!!
    }

    private fun initViews() {
        settings_BTN_toProfileSettings.setOnClickListener { navigateToProfileSettings() }
        settings_LL_profileSettings.setOnClickListener { navigateToProfileSettings() }
    }

    private fun navigateToProfileSettings() {
        val profileSettingsFragment = ProfileSettingsFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, profileSettingsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
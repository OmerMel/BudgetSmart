package com.example.budgetsmart

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.budgetsmart.fragments.HomeFragment
import com.example.budgetsmart.fragments.ProfileSettingsFragment
import com.example.budgetsmart.fragments.SettingsFragment
import com.example.budgetsmart.fragments.TransactionsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

    class MainActivity : AppCompatActivity() {

    private lateinit var bottom_navigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        val homeFragment = HomeFragment()
        val transactionsFragment = TransactionsFragment()
        val settingsFragment = SettingsFragment()

        findViews()
        replaceFragment(homeFragment)

        bottom_navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.Home -> replaceFragment(homeFragment)
                R.id.transactions -> replaceFragment(transactionsFragment)
                R.id.Settings -> replaceFragment(settingsFragment)
                else -> replaceFragment(homeFragment)
            }
            true
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            when (currentFragment) {
                is ProfileSettingsFragment -> hideBottomNavigation()
                else -> showBottomNavigation()
            }
        }
    }

    private fun showBottomNavigation() {
        bottom_navigation.visibility = View.VISIBLE
    }

    private fun hideBottomNavigation() {
        bottom_navigation.visibility = View.GONE
    }

    private fun findViews() {
        bottom_navigation = findViewById(R.id.bottom_navigation)
    }


    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
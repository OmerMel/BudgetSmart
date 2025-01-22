    package com.example.budgetsmart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.budgetsmart.Fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

    class MainActivity : AppCompatActivity() {

    private lateinit var bottom_navigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()

        findViews()
        replaceFragment(homeFragment)

        bottom_navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.Home -> replaceFragment(homeFragment)
                R.id.Reports -> replaceFragment(homeFragment)
                R.id.Settings -> replaceFragment(homeFragment)
                else -> replaceFragment(homeFragment)
            }
            true
        }
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
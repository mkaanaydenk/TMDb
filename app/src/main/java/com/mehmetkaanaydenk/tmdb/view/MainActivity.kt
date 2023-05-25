package com.mehmetkaanaydenk.tmdb.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mehmetkaanaydenk.tmdb.R
import com.mehmetkaanaydenk.tmdb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        bottomNavigationView = binding.bottomNavView
        navController = findNavController(R.id.fragmentContainerView)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            if (destination.id == R.id.detailsFragment) {
                bottomNavigationView.visibility = View.GONE
                binding.topAppBar.navigationIcon =
                    AppCompatResources.getDrawable(this, R.drawable.baseline_arrow_back_ios_24)
            } else {
                bottomNavigationView.visibility = View.VISIBLE
                binding.topAppBar.navigationIcon = null
            }

        }

    }


}
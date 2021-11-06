package com.example.shiftify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isNotEmpty
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shiftify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val bottomNavView = binding.bottomNavView
            if ((destination.id == R.id.nurseShiftsFragment || destination.id == R.id.stationFragment)
                && bottomNavView.menu.findItem(R.id.stationFragment) == null ) {
                    bottomNavView.menu.clear()
                bottomNavView.inflateMenu(R.menu.nurse_bottom_nav_menu)
            } else if (destination.id != R.id.stationFragment && destination.id != R.id.nurseShiftsFragment) {
                bottomNavView.menu.clear()
            }
        }
    }
}
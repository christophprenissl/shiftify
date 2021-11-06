package com.example.shiftify.nurse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shiftify.R
import com.example.shiftify.databinding.ActivityNurseBinding

class NurseActivity : AppCompatActivity(){

    private lateinit var binding: ActivityNurseBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNurseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        navController = findNavController(R.id.nurse_nav_host)
        binding.nurseBottomNavMenu.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        navController.popBackStack()
        super.onBackPressed()
    }
}

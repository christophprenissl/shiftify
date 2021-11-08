package com.example.shiftify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.children
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shiftify.databinding.ActivityMainBinding
import timber.log.Timber
import timber.log.Timber.d
import timber.log.Timber.i

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}
package com.christophprenissl.shiftify.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.christophprenissl.shiftify.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())

        auth = Firebase.auth
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}

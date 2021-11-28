package com.christophprenissl.shiftify.views.nurse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.FragmentNurseShiftsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NurseShiftsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNurseShiftsBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        navController = findNavController()

        if (auth.currentUser != null) {
            database = Firebase.database.reference
                .child("users")
                .child(auth.currentUser!!.uid)
            database.get().addOnSuccessListener {
                val firstname = it.child("firstName").value as String? ?: "Firstname"
                val lastname = it.child("lastName").value as String? ?: "Lastname"
                binding.greetingLabel.text = getString(R.string.hello_nurse_label, firstname, lastname)
            }
        }

        binding.logoutButton.setOnClickListener {
            if (auth.currentUser != null) {
                FirebaseAuth.getInstance().signOut()
            }
            navController.navigate(R.id.action_nurseShiftsFragment_to_loginFragment)
        }

        return binding.root
    }
}

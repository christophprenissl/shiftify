package com.christophprenissl.shiftify.nurse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.christophprenissl.shiftify.databinding.FragmentNurseShiftsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NurseShiftsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNurseShiftsBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            database = Firebase.database.reference
                .child("users")
                .child(auth.currentUser!!.uid)
            database.child("name").get().addOnSuccessListener {
                binding.greetingLabel.text = it.value as String
            }
        }

        return binding.root
    }
}

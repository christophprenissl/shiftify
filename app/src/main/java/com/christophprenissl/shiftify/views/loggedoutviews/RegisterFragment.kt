package com.christophprenissl.shiftify.views.loggedoutviews

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.FragmentRegisterBinding
import com.christophprenissl.shiftify.models.Nurse
import com.christophprenissl.shiftify.utils.showSmallInfoToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import timber.log.Timber.i

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        navController = findNavController()
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        if (auth.currentUser != null) {
            navController.navigate(R.id.nav_graph)
        } else {
            binding.registerButton.isActivated = true
        }

        binding.identityRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.nurse_radio_button -> {
                    binding.stationIdLabel.text = getString(R.string.station_code_label)
                }
                R.id.shift_owner_button -> {
                    binding.stationIdLabel.text = getString(R.string.station_name_label)
                }
            }
        }

        binding.registerButton.setOnClickListener {
            registerWhenValidRegistrationForm()
        }

        return binding.root
    }

    private fun registerWhenValidRegistrationForm() {
        val isShiftOwner = binding.identityRadioGroup.checkedRadioButtonId == R.id.shift_owner_radio_button
        val email = binding.emailEdit.text.toString()
        val firstName = binding.firstNameEdit.text.toString()
        val lastName = binding.lastNameEdit.text.toString()
        val password1 = binding.password1Edit.text.toString()
        val password2 = binding.password2Edit.text.toString()
        val stationEdit = binding.stationIdEdit.text.toString()

        if (email.isEmpty()
            || firstName.isEmpty()
            || lastName.isEmpty()
            || stationEdit.isEmpty()) {
            showSmallInfoToast(context, "Some input is missing.")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showSmallInfoToast(context, "$email is not a correct email address.")
            return
        }

        if (password1.length < 6) {
            showSmallInfoToast(context, "Password must at least have 6 characters.")
            return
        }

        if (password1 != password2) {
            showSmallInfoToast(context, "Passwords don't match.")
            return
        }
        val newNurse = Nurse (
            lastName,
            firstName,
            isShiftOwner,
            stationEdit,
            null,
            null
            )
        registerNurse(email,password1, newNurse)
    }

    private fun registerNurse(email: String, password: String, nurse : Nurse) {
        binding.registerButton.isActivated = false
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    auth.currentUser!!.let {
                        i("$email was registered correctly")
                        database.child("users").child(it.uid).setValue(nurse)
                            .addOnSuccessListener {
                                i("${nurse.firstName} ${nurse.lastName} created in database.")
                                showSmallInfoToast(context, "Shiftify account created")
                            }
                            .addOnFailureListener {
                                showSmallInfoToast(context, "user  data couldn't be created")
                            }
                            .addOnCompleteListener {
                                navController.navigate(R.id.action_registerFragment_to_nurseShiftsFragment)
                            }
                    }
                } else {
                    binding.registerButton.isActivated = true
                    i(task.exception)
                    showSmallInfoToast(context, "Authentication failed. ${task.result}")
                }
            }
    }
}

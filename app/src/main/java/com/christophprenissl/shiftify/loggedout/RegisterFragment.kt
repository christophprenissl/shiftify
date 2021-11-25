package com.christophprenissl.shiftify.loggedout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.FragmentRegisterBinding
import com.christophprenissl.shiftify.persistence.model.Nurse
import com.christophprenissl.shiftify.utils.isEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import timber.log.Timber.i

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val navController = findNavController()
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

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
            val isShiftOwner = binding.identityRadioGroup.checkedRadioButtonId == R.id.shift_owner_radio_button

            if (!isShiftOwner) {
                //TODO check if station code is correct and return if not
            }

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
                Toast.makeText(context, "Some input is missing.",
                    Toast.LENGTH_SHORT).show()
            }

            if (!email.isEmail()) {
                Toast.makeText(context, "$email is not a correct email address.",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password1.length < 6) {
                Toast.makeText(context, "Password must at least have 6 characters.",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password1 != password2) {
                Toast.makeText(context, "Passwords don't match .",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(email, password1)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        auth.currentUser!!.let {
                            i("$email was registered correctly")
                            val newNurse = Nurse(
                                it.uid,
                                lastName,
                                firstName,
                                isShiftOwner,
                                stationEdit,
                                null,
                                null)
                            database.child("users").child(it.uid).setValue(newNurse)
                                .addOnSuccessListener {
                                    i("$firstName $lastName created in database.")
                                    Toast.makeText(context, "Shiftify account created",
                                        Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "user  data couldn't be created",
                                        Toast.LENGTH_SHORT).show()
                                }
                                .addOnCompleteListener {
                                    navController.navigate(R.id.action_registerFragment_to_nurseShiftsFragment)
                                }
                        }
                    } else {
                        i(task.exception)
                        Toast.makeText(context, "Authentication failed. ${task.result}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        return binding.root
    }
}

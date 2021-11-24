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
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber.i

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val navController = findNavController()
        auth = FirebaseAuth.getInstance()

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
            val email = binding.emailEdit.text.toString()
            val firstName = binding.firstNameEdit.text.toString()
            val lastName = binding.lastNameEdit.text.toString()
            val password1 = binding.password1Edit.text.toString()
            val password2 = binding.password2Edit.text.toString()
            val isNurse = binding.identityRadioGroup.checkedRadioButtonId == R.id.nurse_radio_button
            val stationEdit = binding.stationIdEdit.text.toString()


            if (password1 != password2 && password1.length < 6) {
                Toast.makeText(context, "Passwords don't match.",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(email, password1)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser?.email ?: "user"
                        i("$user was signed up correctly")
                        navController.navigate(R.id.action_registerFragment_to_nurseShiftsFragment)
                    } else {
                        Toast.makeText(context, "Authentication failed. ${task.result}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        return binding.root
    }
}

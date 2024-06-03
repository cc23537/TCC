package com.example.loginfrag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.loginfrag.databinding.FragmentLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.btnReg.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerUserFragment)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val pwd = binding.edtPassworld.text.toString()
            auth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener {task ->
                    if (task.isSuccessful){
                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                   }
                   else  {
                       Toast.makeText(activity,task.exception?.message,
                           Toast.LENGTH_LONG).show()
                    }

                }

        }

    }
}
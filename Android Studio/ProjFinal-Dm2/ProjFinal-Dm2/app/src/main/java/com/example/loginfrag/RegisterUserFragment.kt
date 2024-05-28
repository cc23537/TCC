package com.example.loginfrag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.loginfrag.databinding.FragmentRegisterUserBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterUserFragment : Fragment() {

    private var _binding: FragmentRegisterUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterUserBinding.inflate(inflater, container,false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.BtnRegistrar.setOnClickListener {
            val email = binding.edtEmailAddressRG.text.toString()
            val pwd = binding.edtPasswordRg.text.toString()

            auth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(activity, "Usuario cadastrado corretament",
                            Toast.LENGTH_LONG).show()
                    }

                }
                .addOnFailureListener{exception ->
                    Toast.makeText(activity, exception.localizedMessage,
                        Toast.LENGTH_LONG).show()
                }

        }


    }

}
package com.example.loginfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.loginfrag.databinding.AddFragmentBinding
import com.example.loginfrag.model.Armario
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.*

class AddDialog : DialogFragment(){

    private var _binding: AddFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddFragmentBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseRef = FirebaseDatabase.getInstance().getReference("Diciplinas")

        binding.btnAdd.setOnClickListener{
            adicionar()
        }
    }

    private fun adicionar() {
        val nome = binding.edNome.text.toString()
        val codigo = binding.edCodigo.text.toString()
        val id = firebaseRef.push().key!!
        val fruta = Armario(nome,codigo.toInt())
        val gson = Gson()
        val d = gson.toJson(fruta)
        println("---------" + d)



        if(nome.isEmpty() || codigo.isEmpty()){
            Toast.makeText(context, "Preencha todos os campos para adicionar uma Disciplina!", Toast.LENGTH_SHORT).show()
        }else{
            val alimentos = Alimentos(id, nome, codigo)

            firebaseRef.child(id).setValue(alimentos)
                .addOnCompleteListener{
                    Toast.makeText(context, "Disciplina Adicionada com sucesso", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
                .addOnFailureListener{
                    Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }


    }
}
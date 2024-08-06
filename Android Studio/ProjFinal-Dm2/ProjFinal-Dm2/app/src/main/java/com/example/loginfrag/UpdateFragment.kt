package com.example.loginfrag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.loginfrag.databinding.FragmentUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.navigation.fragment.navArgs as navArgs

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args: UpdateFragmentArgs by navArgs()

    private lateinit var firebaseRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseRef = FirebaseDatabase.getInstance().getReference("Diciplinas")

        binding.imgSetaVoltar.setOnClickListener{
            findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
        }

        binding.apply {
            edNome.setText(args.nome)
            edCodigo.setText(args.codigo)
            edId.setText(args.id)
            btnEditar.setOnClickListener {
                update()
            }

        }
    }

    private fun update() {
        val nome = binding.edNome.text.toString()
        val codigo = binding.edCodigo.text.toString()
        val id = binding.edId.text.toString()

        val alimentos = Alimentos(id, nome, codigo)

        firebaseRef.child(id).setValue(alimentos)
            .addOnCompleteListener{
                Toast.makeText(context, "Sua Edição foi concluida com sucesso", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
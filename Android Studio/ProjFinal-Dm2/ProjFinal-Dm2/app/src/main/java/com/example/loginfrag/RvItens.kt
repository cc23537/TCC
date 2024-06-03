package com.example.loginfrag

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.loginfrag.databinding.RvItensBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class RvItens(private  val discplinasList : ArrayList<Disciplinas> ): RecyclerView.Adapter<RvItens.ViewHolder>() {

    class ViewHolder(val binding: RvItensBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvItensBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return discplinasList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = discplinasList[position]
        val firebaseRef = FirebaseDatabase.getInstance().getReference("Diciplinas")

        holder.apply {
            binding.apply {
                tvNameItem.text = currentItem.Nome
                tvNameItem.text = "Valor Calórico: " + currentItem.Calorias
                tvQuantidade.text = "ID: " + currentItem.ID
                rvContainer.setOnClickListener {
                    val action = MainFragmentDirections.actionMainFragmentToUpdateFragment(
                        currentItem.ID.toString(),
                        currentItem.Nome.toString(),
                        currentItem.Calorias.toString()
                    )
                    findNavController(holder.itemView).navigate(action)
                }

            }
        }
    }

    public fun deleteItem(position: Int, context: Context) {
        // Show confirmation dialog
        MaterialAlertDialogBuilder(context)
            .setTitle("Deletar Disciplina Permanentemente")
            .setMessage("Você tem certeza que vai excluir essa Disciplina?")
            .setPositiveButton("Sim") { _, _ ->
                val firebaseRef = FirebaseDatabase.getInstance().getReference("Diciplinas")
                firebaseRef.child(discplinasList[position].ID.toString()).removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(context, "Item Excluido com Sucesso!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { error ->
                        Toast.makeText(context, "ERROR: ${error.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .setNegativeButton("Não") { dialog, _ ->
                dialog.dismiss()
                notifyItemChanged(position)
            }
            .show()
    }

    public fun deletar(position: Int) {

    }
}
package com.example.appcomida.ui.lista

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.appcomida.api.DeleteLista
import com.example.appcomida.api.registrarAlimento
import com.example.appcomida.api.removeAlimento
import com.example.appcomida.databinding.ListaAddBinding
import com.example.appcomida.dataclass.Alimento
import com.example.appcomida.dataclass.Compra
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import java.util.ArrayList

class RvLista(private  val compraList : ArrayList<Compra>): RecyclerView.Adapter<RvLista.ViewHolder>() {

    private var alimento_teste: String = " "

    class ViewHolder(val binding: ListaAddBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ListaAddBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return compraList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = compraList[position]

        holder.apply {
            binding.apply {
                alimento_teste = Alimento.text.toString()
                Alimento.text = currentItem.alimentoASerComprado
                quantidade.text = "Quantidade: " + currentItem.quantidade
            }
        }
    }

    suspend fun deleteItem(position: Int, context: Context, nome: String, quantidade: Int) : String{
        // Show confirmation dialog
        MaterialAlertDialogBuilder(context)
            .setTitle("Deletar Alimento Permanentemente")
            .setMessage("Você tem certeza que vai excluir esse Alimento?")
            .setPositiveButton("Sim") { _, _ ->
                //compraList.removeAt(position)
                notifyItemRemoved(position)

                DeleteLista(nome, quantidade)
            }
            .setNegativeButton("Não") { dialog, _ ->
                // Reverte o swipe ao notificar a mudança do item
                dialog.dismiss()
                notifyItemChanged(position)
            }
            .show()
        return alimento_teste
    }
}
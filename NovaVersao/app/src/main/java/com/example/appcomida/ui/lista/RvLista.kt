package com.example.appcomida.ui.lista

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appcomida.ApiService
import com.example.appcomida.databinding.ListaAddBinding
import com.example.appcomida.dataclass.Compra
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import getRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.ArrayList

class RvLista(private  val compraList : ArrayList<Compra>): RecyclerView.Adapter<RvLista.ViewHolder>() {

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
                Alimento.text = currentItem.alimentoASerComprado
                quantidade.text = "Quantidade: " + currentItem.quantidade
            }
        }
    }

    public fun deleteItem(position: Int, context: Context) {
        // Show confirmation dialog
        MaterialAlertDialogBuilder(context)
            .setTitle("Deletar Disciplina Permanentemente")
            .setMessage("Você tem certeza que vai excluir essa Disciplina?")
            .setPositiveButton("Sim") { _, _ ->
                println("Deletado")
            }
            .setNegativeButton("Não") { dialog, _ ->
                dialog.dismiss()
                notifyItemChanged(position)
            }
            .show()
    }


    public fun DeleteLista(nome: String, quantidade: Int) {
        val apiService = getRetrofit().create(ApiService::class.java)
        val response = { apiService.removeCompra(nome, quantidade) }
    }

}
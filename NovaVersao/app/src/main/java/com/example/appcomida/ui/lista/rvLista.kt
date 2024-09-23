package com.example.appcomida.ui.lista

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.appcomida.dataclass.alimento
import com.example.appcomida.dataclass.compra
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.ArrayList

//class rvLista(private  val CompraList : ArrayList<compra>): RecyclerView.Adapter<rvLista.ViewHolder>() {

    /*class ViewHolder(val binding: rvListaBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            rvListaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return CompraList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = CompraList[position]

        holder.apply {
            binding.apply {
                alimento.text = currentItem.alimentoASerComprado
                quantidade.text = "Quantidade: " + currentItem.quantidade
            }
        }
    }


}*/
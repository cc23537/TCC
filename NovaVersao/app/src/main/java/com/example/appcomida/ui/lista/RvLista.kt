package com.example.appcomida.ui.lista

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcomida.databinding.ListaAddBinding
import com.example.appcomida.dataclass.Compra
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


}
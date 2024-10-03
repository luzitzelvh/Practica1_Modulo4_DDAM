
package com.valdeolivar.practica1livh.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.valdeolivar.practica1livh.data.db.model.BorrowerEntity
import com.valdeolivar.practica1livh.databinding.BorrowerElementBinding

class BorrowerAdapter(
    private val onBorrowerClicked: (BorrowerEntity) -> Unit
): RecyclerView.Adapter<BorrowerViewHolder>() {

    private var borrowers: MutableList<BorrowerEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BorrowerViewHolder {

        val binding = BorrowerElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BorrowerViewHolder(binding)
    }

    override fun getItemCount(): Int = borrowers.size

    override fun onBindViewHolder(holder: BorrowerViewHolder, position: Int) {

        val borrower = borrowers[position]

        holder.bind(borrower)

        //Manejamos el click
        holder.itemView.setOnClickListener{
            onBorrowerClicked(borrower)
        }

    }

    fun updateList(list: MutableList<BorrowerEntity>){
        borrowers = list
        notifyDataSetChanged()
    }


}

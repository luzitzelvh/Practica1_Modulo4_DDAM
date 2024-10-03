package com.valdeolivar.practica1livh.ui

import androidx.recyclerview.widget.RecyclerView
import com.valdeolivar.practica1livh.R
import com.valdeolivar.practica1livh.data.db.model.BorrowerEntity
import com.valdeolivar.practica1livh.databinding.BorrowerElementBinding

class BorrowerViewHolder(
    private val binding: BorrowerElementBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(borrower: BorrowerEntity) {
        binding.apply {
            tvAlias.text = borrower.alias
            tvName.text = borrower.name + " " + borrower.surname
            tvState.text = borrower.state

            // Accede a los recursos a través de binding.root.context
            val stateOptions = binding.root.context.resources.getStringArray(R.array.user_states)
            val stateIndex = stateOptions.indexOf(borrower.state)

            when (stateIndex) {
                0 -> ivIcon.setImageResource(R.drawable.inactive)
                1 -> ivIcon.setImageResource(R.drawable.active)

                else -> ivIcon.setImageResource(R.drawable.inactive)
            }
        }
    }
}






package com.valdeolivar.practica1livh.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.valdeolivar.practica1livh.R
import com.valdeolivar.practica1livh.application.TandaApp
import com.valdeolivar.practica1livh.data.BorrowerRepository
import com.valdeolivar.practica1livh.data.db.model.BorrowerEntity
import com.valdeolivar.practica1livh.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var borrowers: MutableList<BorrowerEntity> = mutableListOf()
    private lateinit var repository: BorrowerRepository

    private lateinit var borrowerAdapter: BorrowerAdapter //inicializa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        repository = (application as TandaApp).repository

        borrowerAdapter = BorrowerAdapter{ sltdBorrower ->

            //Click al registro de cada usuario

            val dialog = BorrowerDialog(newBorrower = false, borrower = sltdBorrower, updateUI = {
                updateUI()
            },message = { text ->
                message(text)
            })

            dialog.show(supportFragmentManager, "dialog2")


        } //Instancia

        binding.rvBorrowers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = borrowerAdapter
        }

        /*
        val borrower = BorrowerEntity(
            alias = "Paleteria",
            name = "Javier",
            surname = "Tocumbo",
            state = "Activo"
        )

        lifecycleScope.launch {
            repository.insertBorrower(borrower)
        }*/


        updateUI()

    }

    fun click(view: View) {
        //Manejo de click en el floating action button

        val dialog = BorrowerDialog(updateUI = {
            updateUI()
        }, message = { text ->
            //Aqui va el mensaje
            message(text)
        })

        dialog.show(supportFragmentManager, "dialog1")

    }

    private fun message(text: String){
        /*Toast.makeText(
            this,
            text,
            Toast.LENGTH_SHORT
        ).show()*/

        Snackbar.make(
            binding.cl,
            text,
            Snackbar.LENGTH_SHORT
        )
            .setTextColor(getColor(R.color.white))
            .setBackgroundTint(getColor(R.color.snackbar))
            .show()

        //#9E1734
    }

    private fun updateUI(){
        lifecycleScope.launch {
            borrowers = repository.getAllBorrowers()

            binding.tvSinRegistros.visibility =
                if (borrowers.isNotEmpty()) View.INVISIBLE else View.VISIBLE

            borrowerAdapter.updateList(borrowers)

        }
    }

}
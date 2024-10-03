package com.valdeolivar.practica1livh.ui

import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.valdeolivar.practica1livh.R
import com.valdeolivar.practica1livh.application.TandaApp
import com.valdeolivar.practica1livh.data.BorrowerRepository
import com.valdeolivar.practica1livh.data.db.model.BorrowerEntity
import com.valdeolivar.practica1livh.databinding.BorrowerDialogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class BorrowerDialog(
    private val newBorrower: Boolean = true,
    private var borrower: BorrowerEntity = BorrowerEntity(
        alias = "",
        name = "",
        surname = "",
        state = "",
        image = 0
    ),
    private val updateUI:() -> Unit,
    private val message: (String) -> Unit
): DialogFragment() {

    private var _binding: BorrowerDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var  builder: AlertDialog.Builder
    private lateinit var dialog: Dialog

    private var saveButton: Button? = null

    private lateinit var repository: BorrowerRepository

    //crear y configurar el dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = BorrowerDialogBinding.inflate(requireActivity().layoutInflater)

        //Instancia del repositorio dentro del dialog fragment
        repository = (requireContext().applicationContext as TandaApp).repository

        builder = AlertDialog.Builder(requireContext())

        //Establecemos en los textos input edit text los valores del objeto borrower
        binding.apply {
            tietAlias.setText(borrower.alias)
            tietName.setText(borrower.name)
            tietSurname.setText(borrower.surname)

            //Configurando el Spinner con el estado del borrower
            val stateOption = resources.getStringArray(R.array.user_states)
            val stateIndex = stateOption.indexOf(borrower.state)
            if(stateIndex >= 0){
                spinnerState.setSelection(stateIndex)
            }
        }

        dialog = if(newBorrower)
            buildDialog("Guardar","Cancelar", {
                //Acción de guardar

                binding.apply {
                    borrower.apply {
                        alias = tietAlias.text.toString()
                        name = tietName.text.toString()
                        surname = tietSurname.text.toString()
                        state = spinnerState.selectedItem.toString()
                    }
                }

                try {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val result = async{
                            repository.insertBorrower(borrower)
                        }

                        result.await()

                        withContext(Dispatchers.Main){
                            message(getString(R.string.success_saving))
                            updateUI()
                        }
                    }


                }catch(e: IOException){
                    message(getString(R.string.error_saving))
                }


            }, {
                //Acción de cancelar
            })
        else
            buildDialog("Actualizar", "Borrar", {
                //Acción de actualizar

                binding.apply {
                    borrower.apply {
                        alias = tietAlias.text.toString()
                        name = tietName.text.toString()
                        surname = tietSurname.text.toString()
                        state = spinnerState.selectedItem.toString()
                    }
                }

                try {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val result = async {
                            repository.updateBorrower(borrower)
                        }

                        result.await()

                        withContext(Dispatchers.Main){
                            message(getString(R.string.success_update))
                            updateUI()
                        }
                    }


                }catch(e: IOException){
                    message(getString(R.string.error_update))
                }

            }, {
                //Acción de borrar

                //Almacenamos el contexto en una variable antes de mandar a llamar el dialogo nuevo
                val context = requireContext()

                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.confirm))
                    .setMessage(getString(R.string.confirm_borrower, borrower.alias))

                    .setPositiveButton(getString(R.string.ok)){_,_ ->
                        try {
                            lifecycleScope.launch(Dispatchers.IO) {
                                val result = async {
                                    repository.deleteBorrower(borrower)
                                }

                                result.await()

                                withContext(Dispatchers.Main) {
                                    message(context.getString(R.string.success_delete))
                                    updateUI()

                                }

                            }

                        }catch (e: IOException){
                                message("Error al borrar el juego")
                        }

                    }
                    .setNegativeButton("Cancelar"){ dialog, _ ->
                        dialog.dismiss()
                    }.create().show()
            })

        return dialog

        /*dialog = builder.setView(binding.root)
            .setTitle(getString(R.string.borrower))
            .setPositiveButton(R.string.save, DialogInterface.OnClickListener{ _, _ ->
                //Click positivo

                //Obtenemos los textos ingresados y los asignamos al objeto borrower
                binding.apply {
                    borrower.apply {
                        alias = tietAlias.text.toString()
                        name = tietName.text.toString()
                        surname = tietSurname.text.toString()
                        state = spinnerState.selectedItem.toString()
                    }
                }

                try {

                    lifecycleScope.launch(Dispatchers.IO) {
                        repository.insertBorrower(borrower)
                    }

                    Toast.makeText(
                        requireContext(),
                        R.string.success_saving,
                        Toast.LENGTH_SHORT
                    ).show()

                    updateUI()

                }catch (e: IOException){
                    Toast.makeText(
                        requireContext(),
                        R.string.error_saving,
                        Toast.LENGTH_SHORT
                    ).show()
                }


            })
            .setNeutralButton(R.string.cancel){_, _ ->

                // Click negativo

            }.create()*/


    }

    //Para destruir
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //Se llama despues de que se crea el dialog en pantalla
    override fun onStart() {
        super.onStart()

        val alertDialog = dialog as AlertDialog

        saveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)

        saveButton?.isEnabled = false

        binding.tietAlias.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                saveButton?.isEnabled = validateFields()
            }

        })

        binding.tietName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                saveButton?.isEnabled = validateFields()
            }

        })

        binding.tietSurname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                saveButton?.isEnabled = validateFields()
            }

        })


        binding.spinnerState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                saveButton?.isEnabled = validateFields()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No es necesario realizar ninguna acción aquí en este caso
            }
        }

    }

    private fun validateFields(): Boolean {
        val aliasNotEmpty = binding.tietAlias.text.toString().isNotEmpty()
        val nameNotEmpty = binding.tietName.text.toString().isNotEmpty()
        val surnameNotEmpty = binding.tietSurname.text.toString().isNotEmpty()
        val isSpinnerValid = binding.spinnerState.selectedItem.toString() != "Estado del prestatario"

        return aliasNotEmpty && nameNotEmpty && surnameNotEmpty && isSpinnerValid
    }

    private fun buildDialog(
        btn1Text: String,
        btn2Text: String,
        positiveButton: () -> Unit,
        negativeButton: () -> Unit
    ): Dialog =
        builder.setView(binding.root)
            .setTitle(R.string.borrower)
            .setPositiveButton(btn1Text){_,_ ->
                //boton positivo
                positiveButton()

            }.setNegativeButton(btn2Text){_,_ ->
                //boton negativo
                negativeButton()

            }.create()


}
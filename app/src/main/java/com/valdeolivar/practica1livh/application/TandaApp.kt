package com.valdeolivar.practica1livh.application

import android.app.Application
import com.valdeolivar.practica1livh.data.BorrowerRepository
import com.valdeolivar.practica1livh.data.db.BorrowerDatabase

//Clase que representa a la aplicacion
//Util para despues inyectar dependencias

class TandaApp: Application() {

    private val database by lazy{
        BorrowerDatabase.getDatabase(this@TandaApp)
    }

    val repository by lazy{
        BorrowerRepository(database.borrowerDao())
    }

}
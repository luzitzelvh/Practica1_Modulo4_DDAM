package com.valdeolivar.practica1livh.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.valdeolivar.practica1livh.data.db.model.BorrowerEntity
import com.valdeolivar.practica1livh.utils.Constants

@Dao
interface BorrowerDao {

    //funciones para interactuar con la base de datos

    //Create
    @Insert
    suspend fun insertBorrower(borrower: BorrowerEntity)

    @Insert
    suspend fun insertBorrower(borrowers: MutableList<BorrowerEntity>)

    //Read
    @Query("SELECT * FROM ${Constants.DATABASE_BORROWERS_TABLE}")
    suspend fun getAllBorrowers(): MutableList<BorrowerEntity>

    //Update
    @Update
    suspend fun updateBorrower(borrower: BorrowerEntity)

    //Delate
    @Delete
    suspend fun deleteBorrower(borrower: BorrowerEntity)

}
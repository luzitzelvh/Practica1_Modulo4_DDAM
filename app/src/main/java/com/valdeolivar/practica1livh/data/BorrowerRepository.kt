package com.valdeolivar.practica1livh.data

import com.valdeolivar.practica1livh.data.db.BorrowerDao
import com.valdeolivar.practica1livh.data.db.model.BorrowerEntity

class BorrowerRepository(
    private val borrowerDao: BorrowerDao
) {

    suspend fun insertBorrower(borrower: BorrowerEntity){
        borrowerDao.insertBorrower(borrower)
    }

    suspend fun getAllBorrowers(): MutableList<BorrowerEntity> = borrowerDao.getAllBorrowers()


    suspend fun updateBorrower(borrower: BorrowerEntity){
        borrowerDao.updateBorrower(borrower)
    }

    suspend fun deleteBorrower(borrower: BorrowerEntity){
        borrowerDao.deleteBorrower(borrower)
    }



}
package com.valdeolivar.practica1livh.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.valdeolivar.practica1livh.data.db.model.BorrowerEntity
import com.valdeolivar.practica1livh.utils.Constants

//esta clase debe de ser abstracta
@Database(
    entities = [BorrowerEntity::class],
    version = 1,
    exportSchema = true
)
abstract class BorrowerDatabase: RoomDatabase() {
    //DAO
    abstract fun borrowerDao(): BorrowerDao

    companion object{
        @Volatile
        private var INSTANCE: BorrowerDatabase? = null


        fun getDatabase(context: Context): BorrowerDatabase{
            //Patrón singleton

            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BorrowerDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}
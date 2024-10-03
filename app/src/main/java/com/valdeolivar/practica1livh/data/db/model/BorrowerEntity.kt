package com.valdeolivar.practica1livh.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.valdeolivar.practica1livh.utils.Constants

@Entity(tableName = Constants.DATABASE_BORROWERS_TABLE)
data class BorrowerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "borrower_id")
    var id: Long = 0,
    @ColumnInfo(name = "borrower_alias")
    var alias: String,
    @ColumnInfo(name = "borrower_name")
    var name: String,
    @ColumnInfo(name = "borrower_surname", defaultValue = "Desconocido")
    var surname: String,
    @ColumnInfo(name = "borrower_state", defaultValue = "Desconocido")
    var state: String,
    @ColumnInfo(name = "borrower_image", defaultValue = "Desconocido")
    var image: Int
)

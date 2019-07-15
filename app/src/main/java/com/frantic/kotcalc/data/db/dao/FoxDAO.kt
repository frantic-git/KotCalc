package com.frantic.kotcalc.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.frantic.kotcalc.data.db.Tables
import com.frantic.kotcalc.data.db.entity.FoxEntity

@Dao
interface FoxDAO {

    @Insert
    fun insertFox(fox: FoxEntity)

    @Query("select * from ${Tables.FOX_TABLE}")
    fun getAllFox(): List<FoxEntity>

}
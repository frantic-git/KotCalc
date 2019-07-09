package com.frantic.kotcalc.data

import android.arch.persistence.room.*

@Dao
interface TranslateRowDao {

    @Query("select * from translateRow")
    fun getAll():List<TranslateRow>

    @Query("select * from translateRow where id = :id")
    fun getById(id: Long):TranslateRow

    @Insert
    fun insert(translateRow: TranslateRow);

    @Update
    fun update(translateRow: TranslateRow);

    @Delete
    fun delete(translateRow: TranslateRow);
}
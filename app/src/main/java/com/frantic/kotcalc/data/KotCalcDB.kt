package com.frantic.kotcalc.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(TranslateRow::class), version = 1, exportSchema = false)
abstract class KotCalcDB : RoomDatabase(){
    abstract fun taskDao(): TranslateRowDao
}
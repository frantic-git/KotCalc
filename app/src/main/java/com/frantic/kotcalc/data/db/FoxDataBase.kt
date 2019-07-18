package com.frantic.kotcalc.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.frantic.kotcalc.data.db.dao.FoxDAO
import com.frantic.kotcalc.data.db.entity.FoxEntity
import com.frantic.kotcalc.data.db.entity.FoxImageEntity

@Database(entities = [FoxEntity::class, FoxImageEntity::class], version = 1, exportSchema = false)
abstract class FoxDataBase : RoomDatabase() {

    abstract fun foxDao(): FoxDAO

    companion object {

        private var INSTANCE: FoxDataBase? = null

        fun getInstance(context: Context): FoxDataBase? {
            if (INSTANCE == null) {
                synchronized(FoxDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        FoxDataBase::class.java,
                        "FoxDataBase"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
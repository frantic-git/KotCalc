package com.frantic.kotcalc.data.db.dao

import androidx.room.*
import com.frantic.kotcalc.data.db.Fields
import com.frantic.kotcalc.data.db.Tables
import com.frantic.kotcalc.data.db.entity.FoxEntity
import com.frantic.kotcalc.data.db.entity.FoxImageEntity
import com.frantic.kotcalc.domain.FoxItem

@Dao
interface FoxDAO {

    @Insert
    fun insertFox(fox: FoxEntity): Long

    @Query("select * from ${Tables.FOX_TABLE}")
    fun getAllFox(): List<FoxEntity>

    @Query(
        "select ${Tables.FOX_TABLE}.${Fields.FOX_NAME_FIELD} as name," +
                " ${Tables.FOX_IMAGE_TABLE}.${Fields.FOX_IMAGE_FIELD} as image" +
                " from ${Tables.FOX_TABLE}, ${Tables.FOX_IMAGE_TABLE}" +
                " where ${Tables.FOX_TABLE}.id == ${Tables.FOX_IMAGE_TABLE}.id"
    )
    fun getAllFoxItem(): List<FoxItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFoxImage(foxImage: FoxImageEntity)

    @Query("select ${Fields.FOX_IMAGE_FIELD} from ${Tables.FOX_IMAGE_TABLE} where id = :foxImageId")
    fun getFoxImage(foxImageId: Long): List<ByteArray>

    @Transaction
    fun insertFoxTransaction(fox: FoxEntity, foxImage: ByteArray) {
        insertFoxImage(FoxImageEntity(insertFox(fox), foxImage))
    }

}
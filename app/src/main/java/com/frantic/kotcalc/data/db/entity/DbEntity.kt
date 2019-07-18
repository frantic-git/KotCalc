package com.frantic.kotcalc.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.frantic.kotcalc.data.db.Fields
import com.frantic.kotcalc.data.db.Tables

@Entity(tableName = Tables.FOX_TABLE)
class FoxEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = Fields.FOX_NAME_FIELD) val foxName: String
)

@Entity(tableName = Tables.FOX_IMAGE_TABLE)
class FoxImageEntity(
    @PrimaryKey(autoGenerate = false) val id: Long? = null,
    @ColumnInfo(name = Fields.FOX_IMAGE_FIELD) val foxImage: ByteArray? = null
)

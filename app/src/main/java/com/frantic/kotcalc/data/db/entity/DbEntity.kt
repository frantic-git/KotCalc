package com.frantic.kotcalc.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.frantic.kotcalc.data.db.Fields
import com.frantic.kotcalc.data.db.Tables

@Entity(tableName = Tables.FOX_TABLE)
class FoxEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = Fields.FOX_NAME_FIELD) val foxName: String
)

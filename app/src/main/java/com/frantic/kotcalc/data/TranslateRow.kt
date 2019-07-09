package com.frantic.kotcalc.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class TranslateRow {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    lateinit var textIn: String
    lateinit var textOut: String

}
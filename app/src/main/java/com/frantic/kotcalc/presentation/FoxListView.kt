package com.frantic.kotcalc.presentation

import com.frantic.kotcalc.domain.FoxItem

interface FoxListView {
    fun setItemsList(itemsList: MutableList<FoxItem>)
}
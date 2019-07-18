package com.frantic.kotcalc.domain

import com.frantic.kotcalc.App
import com.frantic.kotcalc.data.db.entity.FoxEntity
import com.frantic.kotcalc.presentation.FoxListView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FoxListPresenter() {

    lateinit var mView: FoxListView

    constructor(_mView: FoxListView) : this() {
        mView = _mView
    }

    fun getFoxItemList() {

        val itemList = mutableListOf<FoxItem>()

        val job = GlobalScope.launch(Dispatchers.IO) {
            val foxList: List<FoxEntity>? = App.foxDataBase?.foxDao()?.getAllFox()
            if (foxList != null) {
                for (foxItem in foxList) {
                    itemList.add(FoxItem(foxItem.foxName))
                }
            }
        }

        GlobalScope.launch(Dispatchers.Main) {
            job.join()
            mView.setItemsList(itemList)
        }
    }
}
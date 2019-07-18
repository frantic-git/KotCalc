package com.frantic.kotcalc.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.frantic.kotcalc.R
import com.frantic.kotcalc.domain.FoxItem
import com.frantic.kotcalc.domain.FoxListAdapter
import com.frantic.kotcalc.domain.FoxListPresenter
import kotlinx.android.synthetic.main.fragment_foxlist.*

class FoxListFragment : Fragment(), FoxListView {

    lateinit var mPresenter: FoxListPresenter
    val adapter = FoxListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_foxlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        mPresenter = FoxListPresenter(this)
        recyclerView.adapter = adapter
        mPresenter.getFoxItemList()
    }

    override fun setItemsList(itemsList: MutableList<FoxItem>) {
        adapter.setItemsList(itemsList)
    }
}
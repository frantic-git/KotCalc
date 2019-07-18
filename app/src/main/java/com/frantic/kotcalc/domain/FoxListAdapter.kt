package com.frantic.kotcalc.domain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frantic.kotcalc.R
import kotlinx.android.synthetic.main.fox_item.view.*

class FoxListAdapter : RecyclerView.Adapter<FoxListAdapter.ViewHolder>() {

    private var itemsList = mutableListOf<FoxItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)!!.inflate(R.layout.fox_item, parent, false))
    }

    override fun onBindViewHolder(holder: FoxListAdapter.ViewHolder, position: Int) {
        holder.cardTextView.text = itemsList[position].name
    }

    override fun getItemCount(): Int = itemsList.size

    fun setItemsList(itemsList: MutableList<FoxItem>) {
        this.itemsList = itemsList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardLayout = view
        val cardTextView = view.cardTextView
    }
}
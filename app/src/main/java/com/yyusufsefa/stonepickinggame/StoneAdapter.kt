package com.yyusufsefa.stonepickinggame

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class StoneAdapter(val context: Context, val gridItem: List<GridItem>) : BaseAdapter() {

    override fun getCount(): Int = gridItem.size

    override fun getItem(p0: Int): Any = gridItem[p0]

    override fun getItemId(p0: Int): Long = 0

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val temp = if (gridItem[p0].isBlack)
            R.layout.blackboard
        else
            R.layout.whiteboard
        return LayoutInflater.from(context).inflate(temp, p2, false)
    }

}
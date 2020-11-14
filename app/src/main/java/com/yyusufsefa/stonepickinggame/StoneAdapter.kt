package com.yyusufsefa.stonepickinggame

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class StoneAdapter(
    private val context: Context,
    private val gridItemList: List<GridItem>,
    private val onClick: (gridItemList: List<GridItem>, clickedItem: GridItem) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = gridItemList.size

    override fun getItem(p0: Int): Any = gridItemList[p0]

    override fun getItemId(p0: Int): Long = 0

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val item = gridItemList[p0]
        val temp = if (item.isBlack)
            R.layout.blackboard
        else
            R.layout.whiteboard
        val view = LayoutInflater.from(context).inflate(temp, p2, false)
        view.setOnClickListener{
            onClick(gridItemList,item)
        }
        return view
    }

}
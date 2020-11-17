package com.yyusufsefa.stonepickinggame.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.yyusufsefa.stonepickinggame.R
import com.yyusufsefa.stonepickinggame.model.GridItem
import com.yyusufsefa.stonepickinggame.model.StoneType
import kotlinx.android.synthetic.main.blackboard.view.*
import kotlinx.android.synthetic.main.whiteboard.view.*

class StoneAdapter(
    private val context: Context,
    val gridItemList: List<GridItem>,
    private val onClick: (clickedItem: GridItem) -> Unit
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

        var view = LayoutInflater.from(context).inflate(temp, p2, false)

        view.setOnClickListener {
            onClick(item)
        }

        if (item.isBlack) {

            view.imgBbMain.visibility = View.GONE
            view.imgBbNormal.visibility = View.GONE
            view.imgBbWall.visibility = View.GONE
            when (item.mode) {
                StoneType.MAINSTONE -> {
                    view.imgBbMain.visibility = View.VISIBLE
                }
                StoneType.NORMALSTONE -> {
                    view.imgBbNormal.visibility = View.VISIBLE
                    view.txtBlackBoard.text = item.maxMove.toString()
                }
                StoneType.WALL -> {
                    view.imgBbWall.visibility = View.VISIBLE
                }
            }
        }

        if (!item.isBlack) {
            view.imgWbMain.visibility = View.GONE
            view.imgWbNormal.visibility = View.GONE
            view.imgWbWall.visibility = View.GONE
            when (item.mode) {
                StoneType.MAINSTONE -> {
                    view.imgWbMain.visibility = View.VISIBLE
                }
                StoneType.NORMALSTONE -> {
                    view.imgWbNormal.visibility = View.VISIBLE
                    view.txtWhiteBoard.text = item.maxMove.toString()
                }
                StoneType.WALL -> {
                    view.imgWbWall.visibility = View.VISIBLE
                }
            }
        }

        if (item.isBackgroundActive){
            view.setBackgroundColor(Color.GREEN)
        }
        return view
    }

}
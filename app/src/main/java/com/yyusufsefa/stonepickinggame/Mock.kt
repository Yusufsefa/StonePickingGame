package com.yyusufsefa.stonepickinggame

import java.util.*

object MockList {
    fun getMockList(): List<GridItem> {

        val stoneList: ArrayList<GridItem> = ArrayList()

        val stoneModel = GridItem(
            1,
            1,
            StoneType.MAıNSTONE,
            false
        )

        stoneList.add(stoneModel)

        return stoneList
    }
}
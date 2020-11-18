package com.yyusufsefa.stonepickinggame

import com.yyusufsefa.stonepickinggame.model.GridItem
import com.yyusufsefa.stonepickinggame.model.StoneType

object MockList {
    fun getMockList(): List<GridItem> {

        val stoneList = mutableListOf<GridItem>()

        for (x in 1..10) {
            for (y in 1..10) {
                stoneList.add(
                    GridItem(
                        y,
                        x,
                        StoneType.NONE,
                        (x + y) % 2 == 0
                    )
                )
            }
        }
        return stoneList
    }
}

object AutoGenerator {

    fun getAutoGridList(
        mainStoneLimit: Int,
        normalStoneLimit: Int,
        wallLimit: Int
    ): List<GridItem> {

        var _mainStoneLimit = mainStoneLimit
        var _normalStoneLimit = normalStoneLimit
        var _wallLimit = wallLimit

        var temp = (_mainStoneLimit + _normalStoneLimit + _wallLimit)

        var randomNumber = (0..99).shuffled().take(temp)

        val autoStoneList = MockList.getMockList()

        for (x in 0..temp) {

            if (_mainStoneLimit > 0) {
                autoStoneList[randomNumber[x]].mode = StoneType.MAINSTONE
                _mainStoneLimit--
            } else if (_normalStoneLimit > 0) {
                autoStoneList[randomNumber[x]].mode = StoneType.NORMALSTONE
                _normalStoneLimit--
            } else if (_wallLimit > 0) {
                autoStoneList[randomNumber[x]].mode = StoneType.WALL
                _wallLimit--
            }
        }
        return autoStoneList
    }


}
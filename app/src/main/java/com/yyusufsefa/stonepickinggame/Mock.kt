package com.yyusufsefa.stonepickinggame


object MockList {
    fun getMockList(): List<GridItem> {

        val stoneList = mutableListOf<GridItem>()

       for (x in 0..9){
           for (y in 0..9){
               stoneList.add(GridItem(
                       x,
                       y,
                       StoneType.NONE,
                       (x+y)%2 == 0
               ))
           }
       }
        return stoneList
    }
}
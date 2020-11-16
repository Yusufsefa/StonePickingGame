package com.yyusufsefa.stonepickinggame.db

import com.yyusufsefa.stonepickinggame.model.GridItem

class GridItemRepository(private val gridItemDAO: GridItemDAO) {

    var allGridItem = gridItemDAO.getAllGridItem()

    suspend fun insert(listGridItem: List<GridItem>) {
        gridItemDAO.insert(listGridItem)
    }

}
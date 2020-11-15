package com.yyusufsefa.stonepickinggame.db

import com.yyusufsefa.stonepickinggame.model.GridItem

class GridItemRepository(private val gridItemDAO: GridItemDAO) {

    val allGridItem = gridItemDAO.getAllGridItem()

    suspend fun insert(listGridItem: GridItem) {
        gridItemDAO.insert(listGridItem)
    }

}
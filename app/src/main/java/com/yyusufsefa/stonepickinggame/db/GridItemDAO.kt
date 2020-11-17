package com.yyusufsefa.stonepickinggame.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yyusufsefa.stonepickinggame.model.GridItem

@Dao
interface GridItemDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gridItem: List<GridItem>)

    @Query("SELECT * from grid_item")
    fun getAllGridItem(): LiveData<List<GridItem>>

    @Query("DELETE from grid_item WHERE level= :level")
    suspend fun deleteToLevel(level: Int)

}
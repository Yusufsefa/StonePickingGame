package com.yyusufsefa.stonepickinggame.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yyusufsefa.stonepickinggame.GridItem

@Dao
interface GridItemDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gridItem: GridItem)

    @Query("SELECT * from grid_item")
    fun getAllGridItem(): LiveData<List<GridItem>>

}
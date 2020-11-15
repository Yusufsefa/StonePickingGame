package com.yyusufsefa.stonepickinggame.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grid_item")
data class GridItem(
    @ColumnInfo(name = "column")
    val x: Int,
    @ColumnInfo(name = "row")
    val y: Int,
    @ColumnInfo(name = "stonetype")
    var mode: StoneType,
    @ColumnInfo(name = "isblack")
    val isBlack: Boolean
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var _id: Int? = null
}
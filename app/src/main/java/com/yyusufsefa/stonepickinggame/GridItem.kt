package com.yyusufsefa.stonepickinggame

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grid_item")
data class GridItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val _id: Int,
    @ColumnInfo(name = "column")
    val x: Int,
    @ColumnInfo(name = "row")
    val y: Int,
    @ColumnInfo(name = "stonetype")
    var mode: StoneType,
    @ColumnInfo(name = "isblack")
    val isBlack: Boolean
)
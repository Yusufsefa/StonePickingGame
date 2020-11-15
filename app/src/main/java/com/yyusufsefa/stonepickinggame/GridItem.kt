package com.yyusufsefa.stonepickinggame

data class GridItem(
    val x: Int,
    val y: Int,
    var mode: StoneType,
    val isBlack: Boolean
)
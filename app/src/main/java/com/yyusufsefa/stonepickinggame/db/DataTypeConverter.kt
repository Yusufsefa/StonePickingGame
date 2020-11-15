package com.yyusufsefa.stonepickinggame.db

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yyusufsefa.stonepickinggame.StoneType
import java.lang.reflect.Type

object DataTypeConverter {
    @androidx.room.TypeConverter
    @JvmStatic
    fun stringToStoneType(value: String?): StoneType? {
        if (value == null) {
            return null
        }

        val type: Type = object : TypeToken<StoneType?>() {}.type
        return Gson().fromJson<StoneType>(value, type)
    }
}
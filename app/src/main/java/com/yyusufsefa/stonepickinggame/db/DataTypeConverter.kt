package com.yyusufsefa.stonepickinggame.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yyusufsefa.stonepickinggame.model.StoneType
import java.lang.reflect.Type

object DataTypeConverter {
    @TypeConverter
    @JvmStatic
    fun stringToStoneType(value: String?): StoneType? {
        if (value == null) {
            return null
        }

        val type: Type = object : TypeToken<StoneType?>() {}.type
        return Gson().fromJson<StoneType>(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun stoneTypeToString(value: StoneType?): String? {
        if (value == null) {
            return null
        }
        return Gson().toJson(value)
    }

}
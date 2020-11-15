package com.yyusufsefa.stonepickinggame.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yyusufsefa.stonepickinggame.model.GridItem
import kotlinx.coroutines.InternalCoroutinesApi

@Database(
    entities = [GridItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataTypeConverter::class)
abstract class GridRoomDatabase : RoomDatabase() {

    abstract fun gridItemDao(): GridItemDAO

    companion object {

        @Volatile
        private var INSTANCE: GridRoomDatabase? = null

        @InternalCoroutinesApi
        fun getDatabase(
            context: Context
        ): GridRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GridRoomDatabase::class.java,
                    "pickstone_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
package com.sidarta.myalarme.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sidarta.myalarme.data.Alarme

@Database(entities = [Alarme::class], version = 3)
abstract class RoomManager: RoomDatabase() {
    abstract fun getAlarme() : AlarmeDao

    companion object{
        var INSTANCE : RoomManager? = null

        fun getInstance(context: Context):RoomManager?{
            synchronized(RoomManager::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RoomManager::class.java,
                        "alarmes.db"
                    ).allowMainThreadQueries()
                        .build()
                }
                return INSTANCE
            }
        }

    }
}
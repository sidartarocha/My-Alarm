package com.sidarta.myalarme.dao

import androidx.room.*
import com.sidarta.myalarme.data.Alarme

@Dao
interface AlarmeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAlarme(alarme: Alarme):Long

    @Delete
    fun deleteAlarme(alarme: Alarme)

    @Query("Select * from Alarme")
    fun allAlarmes():List<Alarme>

    @Query("Select * from Alarme where id = :id")
    fun findAlarme(id:Int): Alarme


}
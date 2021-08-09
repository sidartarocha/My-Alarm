package com.sidarta.myalarme.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Alarme (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val nomeAlarme: String?,
    val timeRepeat: Boolean,
    val timeAlarm: Long,
): Serializable
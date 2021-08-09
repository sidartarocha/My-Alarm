package com.sidarta.myalarme.helper

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.sidarta.myalarme.data.Alarme
import com.sidarta.myalarme.receiver.AlarmBroadcastReceiver

object AlarmeHelper {

    fun scheduleRTC(
        context: Context,
        alarmeManager: AlarmManager,
        alarme: Alarme
    ) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)

        intent.putExtra("ID", alarme.id)
        intent.putExtra("NAME",alarme.nomeAlarme)
        intent.putExtra("REPEAT", alarme.timeRepeat)

        val pendingIntent =
            PendingIntent.getBroadcast(context, (0..2147483647).random(), intent, 0)

        if (alarme.timeRepeat) {
            alarmeManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                alarme.timeAlarm,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        } else {
            alarmeManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC,
                alarme.timeAlarm,
                pendingIntent
            )
        }

        Log.i("alarmHelper", "alarm criado para tempo em mils: ${alarme.timeAlarm}")
    }

    fun getAlarmManager(context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

}
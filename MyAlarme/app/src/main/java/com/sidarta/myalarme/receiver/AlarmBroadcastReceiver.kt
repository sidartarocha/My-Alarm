package com.sidarta.myalarme.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.sidarta.myalarme.adapter.AlarmeAdapter

import com.sidarta.myalarme.dao.AlarmeDao
import com.sidarta.myalarme.dao.RoomManager
import com.sidarta.myalarme.data.Alarme
import com.sidarta.myalarme.notification.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlarmBroadcastReceiver: BroadcastReceiver() {

    private val alarmeAdapter : AlarmeAdapter by lazy {
        AlarmeAdapter()
    }

    override fun onReceive(context: Context, intent: Intent) {

        val db = context?.let { RoomManager.getInstance(it) }

        Log.i("Alarme Receiver", "Received broadcast from AlarmManager")

        if (intent.action != "sync") {
            Log.i("Alarme Receiver", "entrou")
            val alarme =
                Alarme(
                    intent.getIntExtra("ID", 0),
                    intent.getStringExtra("NAME"),
                    intent.getBooleanExtra("REPEAT", false),
                    0
                )

            Log.i("Alarme Receiver", "Received broadcast from AlarmManager: $alarme")
            NotificationHelper.createNotification(context, alarme)
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    if (alarme.timeRepeat.not()) {
                        db?.getAlarme()?.deleteAlarme(alarme)
                        db?.getAlarme()?.allAlarmes()?.let { alarmeAdapter.setData(it) }

                    }
                }
                context.sendBroadcast(Intent("sync"))
            }
        }

    }
}
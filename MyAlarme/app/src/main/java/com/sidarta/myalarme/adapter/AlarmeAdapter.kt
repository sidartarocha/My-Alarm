package com.sidarta.myalarme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sidarta.myalarme.R
import com.sidarta.myalarme.data.Alarme
import com.sidarta.myalarme.databinding.ItemAlarmBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AlarmeAdapter : RecyclerView.Adapter<AlarmeAdapter.ViewHolder>(){

    private var alarmes: List<Alarme> = ArrayList()
    private val dateFormat = SimpleDateFormat("HH:mm")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm,parent, false)
        val binding = ItemAlarmBinding.bind(view)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alarme = alarmes[position]
        holder.txtAlarmeTimer.text = dateFormat.format((Date(alarme.timeAlarm)))
        holder.txtAlarmeTitle.text = alarme.nomeAlarme
    }

    override fun getItemCount(): Int {
        return alarmes.size
    }
    fun setData(data: List<Alarme>) {
        alarmes = data
        notifyDataSetChanged()
    }

    class ViewHolder(itemAlarmeView: ItemAlarmBinding):RecyclerView.ViewHolder(itemAlarmeView.root) {
        val txtAlarmeTitle: TextView = itemAlarmeView.alarmeTitle
        val txtAlarmeTimer: TextView = itemAlarmeView.alarmeTime
    }
}
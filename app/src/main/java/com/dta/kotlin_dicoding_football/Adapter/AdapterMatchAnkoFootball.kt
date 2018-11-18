package com.dta.kotlin_dicoding_football.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dta.kotlin_dicoding_football.Model.DataEvent
import com.dta.kotlin_dicoding_football.UI.UIMatchAnkoAdapter
import org.jetbrains.anko.AnkoContext
import java.text.ParseException
import java.text.SimpleDateFormat

class AdapterMatchAnkoFootball : RecyclerView.Adapter<AdapterMatchAnkoFootball.FootballViewHolder>() {
    private var listItems: List<DataEvent> = emptyList()

    private var itemClickListener : AdapterMatchFootballClickListener? = null

    fun setClickListener(itemClickListener: AdapterMatchFootballClickListener){
        this.itemClickListener = itemClickListener
    }
    fun getClickListener(): AdapterMatchFootballClickListener?{
        return itemClickListener
    }
    fun setItems(items: List<DataEvent>?) {
        this.listItems = items!!
        notifyItemRangeInserted(0, items.size)
    }
    fun getItems(): List<DataEvent>?{
        return this.listItems
    }

    fun getItemByPosition(position: Int): DataEvent {
        return this.listItems[position]
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FootballViewHolder {
        return FootballViewHolder(UIMatchAnkoAdapter().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FootballViewHolder, position: Int) {
        val football = listItems.get(position)
        holder.tvDate.text = football.eventDate?.let { getLongDate(it) }
        holder.tvTeam1.text = football.homeTeam
        holder.tvTeam1Score.text = football.homeScore
        holder.tvTeam2.text = football.awayTeam
        holder.tvTeam2Score.text = football.awayScore
        holder.itemView.setOnClickListener {
            if (getClickListener()!=null){
                football.let { it1 -> getClickListener()?.onItemClickListener(it1) }
            }
        }
    }

    override fun getItemCount() = listItems.size

    inner class FootballViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvDate: TextView = itemView.findViewById(UIMatchAnkoAdapter.tv_tanggal)
        val tvTeam1: TextView = itemView.findViewById(UIMatchAnkoAdapter.tv_team_1)
        val tvTeam1Score: TextView = itemView.findViewById(UIMatchAnkoAdapter.tv_team_1_score)
        val tvTeam2: TextView = itemView.findViewById(UIMatchAnkoAdapter.tv_team_2)
        val tvTeam2Score: TextView = itemView.findViewById(UIMatchAnkoAdapter.tv_team_2_score)

    }

    private fun formatDate(date: String, format: String): String {
        var result = ""
        val old = SimpleDateFormat("yyyy-MM-dd")

        try {
            val oldDate = old.parse(date)
            val newFormat = SimpleDateFormat(format)

            result = newFormat.format(oldDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return result
    }

    fun getShortDate(date: String): String {
        return formatDate(date, "dd MMMM yyyy")
    }

    fun getLongDate(date: String): String {
        return formatDate(date, "EEE, dd MMM yyyy")
    }
}
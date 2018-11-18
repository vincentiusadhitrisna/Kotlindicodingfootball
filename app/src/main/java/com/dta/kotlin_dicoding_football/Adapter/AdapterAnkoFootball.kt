package com.dta.kotlin_dicoding_football.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dta.kotlin_dicoding_football.Model.FootballClub
import com.dta.kotlin_dicoding_football.UI.UIAnkoFootball
import org.jetbrains.anko.AnkoContext

class AdapterAnkoFootball : RecyclerView.Adapter<AdapterAnkoFootball.FootballViewHolder>() {
    private var listItems: ArrayList<FootballClub> ?= null

    private var itemClickListener : AdapterFootballClickListener? = null

    fun setClickListener(itemClickListener: AdapterFootballClickListener){
        this.itemClickListener = itemClickListener
    }
    fun getClickListener(): AdapterFootballClickListener?{
        return itemClickListener
    }
    fun setItems(items: ArrayList<FootballClub>?) {
        this.listItems = items
        notifyItemRangeInserted(0, items!!.size)
    }
    fun getItems(): ArrayList<FootballClub>?{
        return this.listItems
    }

    fun getItemByPosition(position: Int): FootballClub {
        return this.listItems!![position]
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FootballViewHolder {
        return FootballViewHolder(UIAnkoFootball().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FootballViewHolder, position: Int) {
        val football = listItems?.get(position)
        Glide.with(holder.itemView.context).load(football?.img).apply(RequestOptions().fitCenter()).into(holder.ivClub)
        holder.tvDetail.text = football?.detail
        holder.itemView.setOnClickListener {
            if (getClickListener()!=null){
                football?.let { it1 -> getClickListener()?.onItemClickListener(it1) }
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems?.size!!
    }

    inner class FootballViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ivClub: ImageView = itemView.findViewById(UIAnkoFootball.iv_adapter_football)
        var tvDetail: TextView = itemView.findViewById(UIAnkoFootball.tv_adapter_football)

    }
}
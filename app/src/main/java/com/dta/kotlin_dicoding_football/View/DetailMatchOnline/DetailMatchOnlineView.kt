package com.dta.kotlin_dicoding_football.View.DetailMatchOnline

import com.dta.kotlin_dicoding_football.Model.DataEvent

interface DetailMatchOnlineView {

    fun showLoading()
    fun hideLoading()
    fun detail(eventDetail: DataEvent?, homeTeam: DataEvent?, awayTeam: DataEvent?)
}
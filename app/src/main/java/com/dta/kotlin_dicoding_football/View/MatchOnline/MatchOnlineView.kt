package com.dta.kotlin_dicoding_football.View.MatchOnline

import com.dta.kotlin_dicoding_football.Model.DataEvent

interface MatchOnlineView {

    fun event(data: List<DataEvent>)
}
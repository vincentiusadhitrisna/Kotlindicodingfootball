package com.dta.kotlin_dicoding_football.View.MatchOnline

import com.dta.kotlin_dicoding_football.Model.DataResponseEvent
import com.dta.kotlin_dicoding_football.Network.ApiRepository
import com.dta.kotlin_dicoding_football.Network.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchOnlinePresenter(private val view: MatchOnlineView,
                           private val api: String,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getLiga() {
        async(context.main) {
            val data = bg {
                gson.fromJson(ApiRepository().doRequest(api), DataResponseEvent::class.java)
            }
            data.await().events?.let { view.event(it) }
        }

    }
}

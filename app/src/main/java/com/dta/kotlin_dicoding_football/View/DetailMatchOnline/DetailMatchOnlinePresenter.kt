package com.dta.kotlin_dicoding_football.View.DetailMatchOnline

import com.dta.kotlin_dicoding_football.Model.DataResponseEvent
import com.dta.kotlin_dicoding_football.Model.DataResponseTeam
import com.dta.kotlin_dicoding_football.Network.ApiRepository
import com.dta.kotlin_dicoding_football.Network.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailMatchOnlinePresenter(private val view: DetailMatchOnlineView,
                                 private val apiMatchDetail: String,
                                 private val apiHomeTeam: String,
                                 private val apiAwayTeam: String,
                                 private val gson: Gson,
                                 private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getDetail() {
        view.showLoading()
        async(context.main) {
            val matchDetail = bg {
                gson.fromJson(ApiRepository().doRequest(apiMatchDetail), DataResponseEvent::class.java)
            }
            val homeTeam = bg {
                gson.fromJson(ApiRepository().doRequest(apiHomeTeam), DataResponseTeam::class.java)
            }
            val awayTeam = bg {
                gson.fromJson(ApiRepository().doRequest(apiAwayTeam), DataResponseTeam::class.java)
            }
            view.detail(matchDetail.await().events?.get(0), homeTeam.await().teams?.get(0), awayTeam.await().teams?.get(0))
            view.hideLoading()
        }
    }
}

package com.dta.kotlin_dicoding_football

import com.dta.kotlin_dicoding_football.Model.DataEvent
import com.dta.kotlin_dicoding_football.Model.DataResponseEvent
import com.dta.kotlin_dicoding_football.Model.DataResponseTeam
import com.dta.kotlin_dicoding_football.Network.ApiRepository
import com.dta.kotlin_dicoding_football.Network.TheSportDBApi
import com.dta.kotlin_dicoding_football.View.DetailMatchOnline.DetailMatchOnlinePresenter
import com.dta.kotlin_dicoding_football.View.DetailMatchOnline.DetailMatchOnlineView
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PresenterTest{

    @Mock
    private lateinit var view: DetailMatchOnlineView

    @Mock
    private lateinit var gson: Gson

    @Mock
    lateinit var apiRequest: ApiRepository

    private lateinit var presenter: DetailMatchOnlinePresenter
    val apiMatchDetail = TheSportDBApi("526916").getmatchDetail()
    val apiHomeTeam = TheSportDBApi("134778").getTeamDetail()
    val apiAwayTeam = TheSportDBApi("133613").getTeamDetail()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = DetailMatchOnlinePresenter(
                view,
                apiMatchDetail,
                apiHomeTeam,
                apiAwayTeam,
                gson,
                TestContextProvider())
    }

    @Test
    fun testGetNextEvent() {
        val events: MutableList<DataEvent> = mutableListOf()
        val home: MutableList<DataEvent> = mutableListOf()
        val away: MutableList<DataEvent> = mutableListOf()
        val response : DataResponseEvent? = DataResponseEvent(events)
        val responseHome : DataResponseTeam? = DataResponseTeam(home)
        val responseAway : DataResponseTeam? = DataResponseTeam(away)

        `when`(gson.fromJson(apiRequest.doRequest(apiMatchDetail),
                DataResponseEvent::class.java)).thenReturn(response)
        `when`(gson.fromJson(apiRequest.doRequest(apiHomeTeam),
                DataResponseTeam::class.java)).thenReturn(responseHome)
        `when`(gson.fromJson(apiRequest.doRequest(apiAwayTeam),
                DataResponseTeam::class.java)).thenReturn(responseAway)

        presenter.getDetail()

        Mockito.verify(view).showLoading()
        response?.events?.let {it->
            responseHome?.teams?.let {itl->
                responseAway?.teams?.let { itll->
                    Mockito.verify(view).detail(it[0],itl[0],itll[0])
                }
            }
        }
        Mockito.verify(view).hideLoading()
    }
}
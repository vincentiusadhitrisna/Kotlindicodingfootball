package com.dta.kotlin_dicoding_football.Network

import android.net.Uri
import com.dta.kotlin_dicoding_football.BuildConfig

class TheSportDBApi(val id: String?) {

    fun getPrevLeague() = BuildConfig.BASE_URL+"eventspastleague.php?id=$id"
    fun getNextLeague() = BuildConfig.BASE_URL+"eventsnextleague.php?id=$id"
    fun getmatchDetail() = BuildConfig.BASE_URL+"lookupevent.php?id=$id"
    fun getTeamDetail() = BuildConfig.BASE_URL+"lookupteam.php?id=$id"

}
package com.dta.kotlin_dicoding_football.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataFavorite (
        val  id: Long?,
        var eventId: String?,
        var eventName: String?,
        var eventDate: String?,
        var homeTeamId: String?,
        var homeTeam: String?,
        var homeScore: String?,
        var awayTeamId: String?,
        var awayTeam: String?,
        var awayScore: String?): Parcelable
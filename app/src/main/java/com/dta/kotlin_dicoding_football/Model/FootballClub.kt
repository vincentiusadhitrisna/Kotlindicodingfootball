package com.dta.kotlin_dicoding_football.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FootballClub (var img: String, var detail: String): Parcelable

package com.dta.kotlin_dicoding_football.Network

import java.net.URL

class ApiRepository {

    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}
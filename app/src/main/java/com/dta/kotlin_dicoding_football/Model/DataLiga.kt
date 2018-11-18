package com.dta.kotlin_dicoding_football.Model

data class DataLiga(val idLeague: String?, val strLeague: String?) {

    override fun toString(): String {
        return strLeague.toString()
    }
}
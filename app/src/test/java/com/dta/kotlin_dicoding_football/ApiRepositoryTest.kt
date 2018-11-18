package com.dta.kotlin_dicoding_football

import com.dta.kotlin_dicoding_football.Network.ApiRepository
import com.dta.kotlin_dicoding_football.Network.TheSportDBApi
import org.junit.Test

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepositoryTest {

    @Test
    fun getMatchDetail() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = TheSportDBApi("4328").getmatchDetail()
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
    @Test
    fun getPrevMatch() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = TheSportDBApi("4328").getPrevLeague()
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}

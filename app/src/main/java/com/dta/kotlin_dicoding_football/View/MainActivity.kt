package com.dta.kotlin_dicoding_football.View

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.dta.kotlin_dicoding_football.Adapter.AdapterAnkoFootball
import com.dta.kotlin_dicoding_football.Adapter.AdapterFootballClickListener
import com.dta.kotlin_dicoding_football.Model.FootballClub
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity(){

    private val adapterAnkoFootball = AdapterAnkoFootball()
    private lateinit var recylerViewClub : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            padding = dip(5)

            recylerViewClub =  recyclerView {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
            }.lparams(width = matchParent, height = matchParent) {
                margin = dip(8)
            }
        }
        setDataFootballClub()

    }

    private fun setDataFootballClub(){

        recylerViewClub.adapter = adapterAnkoFootball
        val list: ArrayList<FootballClub> = arrayListOf()
        list.add(FootballClub("https://images.cdn.fourfourtwo.com/sites/fourfourtwo.com/files/styles/inline-image/public/asante_kotoko.jpg", "There’s only one way to start: with an evil porcupine.\n" +
                "\n" +
                "Ghana’s Asante Kotoko were named Africa’s Club of the Century in 2000 by football nerd-do-wells IFFHS (International Federation of Football History and Statistics), ostensibly based on results. We know the real reason: that porcupine struck terror into IFFHS’s cold, mathematical hearts. After all, the club’s motto means, ‘Kill a thousand and a thousand more will come’. A-porc-alypse!\n" +
                "\n" +
                "Kotoko also have an unofficial nickname that has previously resulted in the proposed building of a Fabulous Arena, and their women’s team being called Fabulous Ladies of Asante Kotoko. Which is just delightful.\n" +
                "\n"))
        list.add(FootballClub("https://images.cdn.fourfourtwo.com/sites/fourfourtwo.com/files/styles/inline-image/public/boca_juniors_badge.jpg", "They’re going to need a bigger badge. But in the ’70s Boca started adding a star for every title they won, and damn it, they’re not going to stop now.\n"))
        list.add(FootballClub("https://images.cdn.fourfourtwo.com/sites/fourfourtwo.com/files/styles/inline-image/public/inter_badge.jpg?", "To win the European Cup three times, a club needs a classic crest. That’s why Nottingham Forest won it only twice. Inter’s emblem, though, has a timeless design, despite a pointlessly minor 2014 rebrand that scaled new heights of pretentiousness.\n" +
                "\n" +
                "Inside ever-decreasing circles sit the letters I, M, F and C in an Escher-like arrangement. Which letter’s on top? Which is bottom? Who knows? They’re intricately interwoven, whereas other clubs’ attempts at a similar design can create a clustered mess.\n" +
                "\n" +
                "\n"))
        list.add(FootballClub("https://images.cdn.fourfourtwo.com/sites/fourfourtwo.com/files/styles/inline-image/public/bohemians_badge.jpg", "Moving from the sublime to the ridiculous, the complex to the simple, Milan to Prague, this is the intriguing badge worn by Czech top-flight outfit Bohemians 1905. It’s a kangaroo. In central Europe. Frankly, FFT agrees with the badge: the less explanation, the better.\n" +
                "\n" +
                "Oh, fine: they adopted the nickname after a 1927 tour of Australia ended with Bohemians being given two kangaroos. Happy now?\n" +
                "\n" +
                "\n"))
        list.add(FootballClub("https://images.cdn.fourfourtwo.com/sites/fourfourtwo.com/files/styles/inline-image/public/gandzasar.jpg", "Few crests are more epic than this effort from the Armenian Premier League (we researched hard, OK?). A bear clutching a key atop a retro football resembling a planet is stirring stuff. The only shame is that ‘Ganzasar’ actually means ‘treasure mountain’. Now that’s a badge we want to see.\n" +
                " "))
        list.add(FootballClub("https://images.cdn.fourfourtwo.com/sites/fourfourtwo.com/files/styles/inline-image/public/orlando_pirates.jpg", "Where St Pauli talk the talk, Orlando Pirates walk the walk. The South African giants’ crest has a skull ’n’ crossbones that wouldn’t look out of place on a Motörhead LP, having thankfully run with the most well-known of their many nicknames, rather than the somewhat less intimidating ‘Happy People’.\n" +
                " "))
        list.add(FootballClub("https://images.cdn.fourfourtwo.com/sites/fourfourtwo.com/files/styles/inline-image/public/chornomorets.jpg", "We like this badge for one reason and one reason only: the Adidas Tango. While every other club badge in the world features the tried and tested models – your classic leather football with volleyball-esque lines, or a ‘modern’ white-with-black-polygons design that hasn’t seen action since the ’70s – Chernomorets elect for the greatest ball ever created in sport. Shine on, you crazy Ukrainian diamonds.\n" +
                " "))

        adapterAnkoFootball.setItems(list)
        adapterAnkoFootball.notifyDataSetChanged()
        adapterAnkoFootball.setClickListener(object : AdapterFootballClickListener{
            override fun onItemClickListener(football: FootballClub) {
                startActivity<DetailFootballActivity>("detailFootballClub" to football)
            }
        })



    }

}
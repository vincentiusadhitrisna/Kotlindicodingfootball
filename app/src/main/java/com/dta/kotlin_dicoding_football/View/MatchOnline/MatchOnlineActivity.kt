package com.dta.kotlin_dicoding_football.View.MatchOnline

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import com.dta.kotlin_dicoding_football.Adapter.AdapterFavMatchAnkoFootball
import com.dta.kotlin_dicoding_football.Adapter.AdapterFavMatchFootballClickListener
import com.dta.kotlin_dicoding_football.Adapter.AdapterMatchAnkoFootball
import com.dta.kotlin_dicoding_football.Adapter.AdapterMatchFootballClickListener
import com.dta.kotlin_dicoding_football.Database.DBHelper.Companion.TABLE_FAVORITE
import com.dta.kotlin_dicoding_football.Database.database
import com.dta.kotlin_dicoding_football.Model.DataEvent
import com.dta.kotlin_dicoding_football.Model.DataFavorite
import com.dta.kotlin_dicoding_football.Network.TheSportDBApi
import com.dta.kotlin_dicoding_football.R
import com.dta.kotlin_dicoding_football.View.DetailMatchOnline.DetailMatchOnlineActivity
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MatchOnlineActivity : AppCompatActivity(), MatchOnlineView{

    lateinit var presenter: MatchOnlinePresenter
    var adapter = AdapterMatchAnkoFootball()
    var adapterFav = AdapterFavMatchAnkoFootball()
    lateinit var spinner: Spinner
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var emptyDataView: LinearLayout
    private var cekShow = 1
    private var leagueId = "4328"

    var events: MutableList<DataEvent> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayout {
            orientation = LinearLayout.VERTICAL

            relativeLayout {
                emptyDataView = linearLayout {
                    orientation = LinearLayout.VERTICAL

                    imageView {
                        setImageResource(R.drawable.ic_action_no_data)
                    }

                    textView {
                        gravity = Gravity.CENTER
                        padding = dip(8)
                        text = "No Data Provided"
                    }
                }.lparams {
                    centerInParent()
                }

                recyclerView = recyclerView {
                    id = rvListMatch
                    layoutManager = LinearLayoutManager(ctx)
                }.lparams(matchParent, matchParent) {
                    topOf(bottomNavigation)
                }

                progressBar = progressBar {
                    indeterminateDrawable.setColorFilter(
                            ContextCompat.getColor(ctx, R.color.colorPrimary),
                            PorterDuff.Mode.SRC_IN
                    )
                }.lparams {
                    centerInParent()
                }

                bottomNavigationView {
                    id = bottomNavigation
                    backgroundColor = Color.WHITE

                    menu.apply {
                        add("Prev. Match")
                                .setIcon(R.drawable.ic_action_back)
                                .setOnMenuItemClickListener {
                                    cekShow = 1
                                    setupActivity()
                                    false
                                }

                        add("Next Match")
                                .setIcon(R.drawable.ic_action_next)
                                .setOnMenuItemClickListener {
                                    cekShow = 2
                                    setupActivity()
                                    false
                                }
                        add("Favorite")
                                .setTitleCondensed("Favorite")
                                .setTitle("Favorite")
                                .setIcon(R.drawable.ic_action_favorite)
                                .setOnMenuItemClickListener {
                                    cekShow = 3
                                    setupDatabase()
                                    false
                                }
                    }
                }.lparams(matchParent, wrapContent) {
                    alignParentBottom()
                }
            }
        }
        setupActivity()
    }
    private fun setupActivity() {
        progressBar.visibility = View.VISIBLE
        emptyDataView.visibility = View.GONE
        val theSportDBApi = TheSportDBApi(leagueId)
        val api = if(cekShow ==1) theSportDBApi.getPrevLeague() else theSportDBApi.getNextLeague()
        val gson = Gson()
        presenter = MatchOnlinePresenter(this,api,gson)
        recyclerView.adapter = adapter

        presenter.getLiga()
        adapter.setClickListener(object : AdapterMatchFootballClickListener {
            override fun onItemClickListener(football: DataEvent) {
                startActivity<DetailMatchOnlineActivity>("DATA_MATCH" to football)
            }
        })
    }
    override fun event(data: List<DataEvent>) {
        events.clear()
        events.addAll(data)
        adapter.setItems(data)
        adapter.notifyDataSetChanged()
        recyclerView.scrollToPosition(0)
        progressBar.visibility = View.GONE
        emptyDataView.visibility = View.GONE
    }

    private fun setupDatabase(){
        events.clear()
        this.database.use {
            val result = select(TABLE_FAVORITE)
            val favorite = result.parseList(classParser<DataFavorite>())
            recyclerView.adapter = adapterFav
            adapterFav.setClickListener(object : AdapterFavMatchFootballClickListener {
                override fun onItemClickListener(fav: DataFavorite) {
                    val event = DataEvent(
                            eventId = fav.eventId,
                            eventDate = fav.eventDate,
                            eventName = fav.eventName,
                            homeTeamId = fav.homeTeamId,
                            homeTeam = fav.homeTeam,
                            homeScore = fav.homeScore,
                            awayTeamId = fav.awayTeamId,
                            awayTeam = fav.awayTeam,
                            awayScore = fav.awayScore
                    )
                    startActivity<DetailMatchOnlineActivity>(DATA_MATCH to event)
                }
            })
            recyclerView.scrollToPosition(0)
            adapterFav.setItems(favorite)
            adapterFav.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
    }
    companion object {
        const val DATA_MATCH = "DATA_MATCH"
        const val rvListMatch = 99
        const val bottomNavigation = 98

    }
}
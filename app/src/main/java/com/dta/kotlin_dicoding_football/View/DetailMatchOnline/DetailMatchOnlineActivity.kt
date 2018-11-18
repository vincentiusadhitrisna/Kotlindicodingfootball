package com.dta.kotlin_dicoding_football.View.DetailMatchOnline

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import com.dta.kotlin_dicoding_football.Database.DBHelper.Companion.AWAY_TEAM_ID
import com.dta.kotlin_dicoding_football.Database.DBHelper.Companion.AWAY_TEAM_NAME
import com.dta.kotlin_dicoding_football.Database.DBHelper.Companion.AWAY_TEAM_SCORE
import com.dta.kotlin_dicoding_football.Database.DBHelper.Companion.EVENT_DATE
import com.dta.kotlin_dicoding_football.Database.DBHelper.Companion.EVENT_ID
import com.dta.kotlin_dicoding_football.Database.DBHelper.Companion.EVENT_NAME
import com.dta.kotlin_dicoding_football.Database.DBHelper.Companion.HOME_TEAM_ID
import com.dta.kotlin_dicoding_football.Database.DBHelper.Companion.HOME_TEAM_NAME
import com.dta.kotlin_dicoding_football.Database.DBHelper.Companion.HOME_TEAM_SCORE
import com.dta.kotlin_dicoding_football.Database.DBHelper.Companion.TABLE_FAVORITE
import com.dta.kotlin_dicoding_football.Database.database
import com.dta.kotlin_dicoding_football.Model.DataEvent
import com.dta.kotlin_dicoding_football.Model.DataFavorite
import com.dta.kotlin_dicoding_football.Network.TheSportDBApi
import com.dta.kotlin_dicoding_football.R
import com.dta.kotlin_dicoding_football.View.MatchOnline.MatchOnlineActivity.Companion.DATA_MATCH
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import java.text.ParseException
import java.text.SimpleDateFormat

class DetailMatchOnlineActivity : AppCompatActivity(), DetailMatchOnlineView {


    lateinit var presenter: DetailMatchOnlinePresenter
    lateinit var progressView: ProgressBar
    lateinit var dataView: ScrollView

    lateinit var imgHomeBadge: ImageView
    lateinit var imgAwayBadge: ImageView

    private lateinit var event: DataEvent

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        event = intent.getParcelableExtra(DATA_MATCH)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.detail)
        isFav()
        setupDetail()

        relativeLayout {
            progressView = progressBar {
                indeterminateDrawable.setColorFilter(
                        ContextCompat.getColor(ctx, R.color.colorPrimary),
                        PorterDuff.Mode.SRC_IN
                )
            }.lparams {
                centerInParent()
            }
        }
        presenter.getDetail()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_match, menu)
        menuItem = menu
        setFav()

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFav() else addToFavorite()
                isFavorite = !isFavorite
                setFav()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupDetail() {
        val apiMatchDetail = TheSportDBApi(event.eventId).getmatchDetail()
        val apiHomeTeam = TheSportDBApi(event.homeTeamId).getTeamDetail()
        val apiAwayTeam = TheSportDBApi(event.awayTeamId).getTeamDetail()
        val gson = Gson()
        presenter = DetailMatchOnlinePresenter(this, apiMatchDetail, apiHomeTeam, apiAwayTeam, gson)

    }

    override fun detail(eventDetail: DataEvent?, homeTeam: DataEvent?, awayTeam: DataEvent?) {

        eventDetail?.let { homeTeam?.let { it1 -> awayTeam?.let { it2 -> setDetail(it, it1, it2) } } }

        Picasso.get()
                .load(homeTeam?.teamBadge)
                .into(imgHomeBadge)

        Picasso.get()
                .load(awayTeam?.teamBadge)
                .into(imgAwayBadge)

    }
    override fun showLoading() {
        progressView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressView.visibility = View.GONE
    }
    private fun setDetail(eventDetail: DataEvent, homeTeam: DataEvent, awayTeam: DataEvent){
        relativeLayout {
            dataView = scrollView {
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    padding = dip(16)

                    // date
                    textView {
                        gravity = Gravity.CENTER
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = eventDetail.eventDate?.let { getLongDate(it) }
                    }

                    // score
                    linearLayout {
                        padding = dip(16)
                        gravity = Gravity.CENTER

                        textView {
                            textSize = 48f
                            setTypeface(null, Typeface.BOLD)
                            text = eventDetail.homeScore
                        }

                        textView {
                            padding = dip(16)
                            textSize = 24f
                            text = "vs"
                        }

                        textView {
                            textSize = 48f
                            setTypeface(null, Typeface.BOLD)
                            text = eventDetail.awayScore
                        }
                    }

                    // team
                    linearLayout {
                        linearLayout {
                            orientation = LinearLayout.VERTICAL

                            imgHomeBadge = imageView() {
                            }.lparams {
                                width = dip(100)
                                height = dip(100)
                                gravity = Gravity.CENTER
                            }

                            textView {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                textSize = 24f
                                setTypeface(null, Typeface.BOLD)
                                text = eventDetail.homeTeam
                            }

                            textView {
                                gravity = Gravity.CENTER
                                text = eventDetail.homeFormation
                            }
                        }.lparams(matchParent, wrapContent, 1f)

                        linearLayout {
                            orientation = LinearLayout.VERTICAL

                            imgAwayBadge = imageView() {
                            }.lparams {
                                width = dip(100)
                                height = dip(100)
                                gravity = Gravity.CENTER
                            }

                            textView {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                textSize = 24f
                                setTypeface(null, Typeface.BOLD)
                                text = eventDetail.awayTeam
                            }

                            textView {
                                gravity = Gravity.CENTER
                                text = eventDetail.awayFormation
                            }
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    view {
                        backgroundColor = Color.LTGRAY
                    }.lparams(matchParent, dip(1)) {
                        topMargin = dip(8)
                    }

                    // goals
                    linearLayout {
                        topPadding = dip(8)

                        textView {
                            text = eventDetail.homeGoalDetails
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Goals"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = eventDetail.awayGoalsDetails
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // shots
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = eventDetail.homeShots
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Shots"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = eventDetail.awayShots
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    view {
                        backgroundColor = Color.LTGRAY
                    }.lparams(matchParent, dip(1)) {
                        topMargin = dip(8)
                    }

                    // lineups
                    textView {
                        topPadding = dip(8)
                        gravity = Gravity.CENTER
                        textSize = 18f
                        setTypeface(null, Typeface.BOLD)
                        text = "Lineups"
                    }

                    // goal keeper
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = eventDetail.homeLineupGoalKeeper
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Goal Keeper"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = eventDetail.awayLineupGoalKeeper
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // defense
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = eventDetail.homeLineupDefense
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Defense"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = eventDetail.awayLineupDefense
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // midfield
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = eventDetail.homeLineupMidfield
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Midfield"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = eventDetail.awayLineupMidfield
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // forward
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = eventDetail.homeLineupForward
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Forward"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = eventDetail.awayLineupForward
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // substitutes
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = eventDetail.homeLineupSubstitutes
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Substitutes"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = eventDetail.awayLineupSubstitutes
                        }.lparams(matchParent, wrapContent, 1f)
                    }
                }
            }


        }
    }

    private fun formatDate(date: String, format: String): String {
        var result = ""
        val old = SimpleDateFormat("yyyy-MM-dd")

        try {
            val oldDate = old.parse(date)
            val newFormat = SimpleDateFormat(format)

            result = newFormat.format(oldDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return result
    }

    fun getShortDate(date: String): String {
        return formatDate(date, "dd MMMM yyyy")
    }

    fun getLongDate(date: String): String {
        return formatDate(date, "EEE, dd MMM yyyy")
    }

    //    Favorite Function
    private fun addToFavorite() {
        try {
            database.use {
                insert(TABLE_FAVORITE,
                        EVENT_ID to event.eventId,
                        EVENT_NAME to event.eventName,
                        EVENT_DATE to event.eventDate,
                        HOME_TEAM_ID to event.homeTeamId,
                        HOME_TEAM_NAME to event.homeTeam,
                        HOME_TEAM_SCORE to event.homeScore,
                        AWAY_TEAM_ID to event.awayTeamId,
                        AWAY_TEAM_NAME to event.awayTeam,
                        AWAY_TEAM_SCORE to event.awayScore)
            }
            snackbar(dataView, getString(R.string.add_fav)).show()
        } catch (e: SQLiteConstraintException) {
            snackbar(dataView, e.localizedMessage).show()
        }
    }

    private fun removeFav() {
        try {
            database.use {
                delete(TABLE_FAVORITE, "(EVENT_ID = {id})", "id" to event.eventId.orEmpty())
            }
            snackbar(dataView, getString(R.string.remove_fav)).show()
        } catch (e: SQLiteConstraintException) {
            snackbar(dataView, e.localizedMessage).show()
        }
    }

    private fun setFav() {
        val icon = if (isFavorite) R.drawable.ic_action_favorite_true else R.drawable.ic_action_favorite

        menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, icon)
    }

    private fun isFav() {
        database.use {
            val result = select(TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})", "id" to event.eventId.orEmpty())
            val favorite = result.parseList(classParser<DataFavorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}
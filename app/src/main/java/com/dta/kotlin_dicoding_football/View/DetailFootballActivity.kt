package com.dta.kotlin_dicoding_football.View

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dta.kotlin_dicoding_football.Model.FootballClub
import org.jetbrains.anko.*

class DetailFootballActivity : AppCompatActivity(){

    lateinit var footballClub : FootballClub
    private var detailClubFootball: String = ""

    private lateinit var imgViewClub : ImageView
    private lateinit var tvDetailClub : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            padding = dip(10)

            imgViewClub = imageView{}
                    .lparams(matchParent, wrapContent){
                        gravity = Gravity.CENTER
                    }
            tvDetailClub = textView{}
                    .lparams(matchParent, wrapContent){
                        gravity = Gravity.CENTER
                    }
        }

        val i = intent.extras
        if (i!=null) {
            footballClub = intent.getParcelableExtra("detailFootballClub")

            Glide.with(this).asDrawable().load(footballClub.img).apply(RequestOptions().fitCenter()).into(imgViewClub)
            tvDetailClub.text = footballClub.detail
        }
    }
}
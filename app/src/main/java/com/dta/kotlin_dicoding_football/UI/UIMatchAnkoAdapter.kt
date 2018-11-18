package com.dta.kotlin_dicoding_football.UI

import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.dta.kotlin_dicoding_football.R
import org.jetbrains.anko.*

class UIMatchAnkoAdapter : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            lparams(matchParent, wrapContent)
            orientation = LinearLayout.VERTICAL

            linearLayout {
                backgroundColor = Color.WHITE
                orientation = LinearLayout.VERTICAL
                padding = dip(8)

                textView {
                    id = tv_tanggal
                    textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                    gravity = Gravity.CENTER
                }.lparams(matchParent, wrapContent)

                linearLayout {
                    gravity = Gravity.CENTER_VERTICAL

                    textView {
                        id = tv_team_1
                        gravity = Gravity.CENTER
                        textSize = 18f
                        text = "home"
                    }.lparams(matchParent, wrapContent, 1f)

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        textView {
                            id = tv_team_1_score
                            padding = dip(8)
                            textSize = 20f
                            setTypeface(null, Typeface.BOLD)
                            text = "0"
                        }

                        textView {
                            text = "vs"
                        }

                        textView {
                            id = tv_team_2_score
                            padding = dip(8)
                            textSize = 20f
                            setTypeface(null, Typeface.BOLD)
                            text = "0"
                        }
                    }

                    textView {
                        id = tv_team_2
                        gravity = Gravity.CENTER
                        textSize = 18f
                        text = "away"
                    }.lparams(matchParent, wrapContent, 1f)
                }
            }.lparams(matchParent, matchParent) {
                setMargins(dip(16), dip(8), dip(16), dip(8))
            }
        }
    }
    companion object {
        val tv_tanggal = 1
        val tv_team_1 = 2
        val tv_team_1_score = 3
        val tv_team_2 = 4
        val tv_team_2_score = 5
    }
}
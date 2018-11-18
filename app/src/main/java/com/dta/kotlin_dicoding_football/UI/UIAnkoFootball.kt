package com.dta.kotlin_dicoding_football.UI

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.dta.kotlin_dicoding_football.R
import org.jetbrains.anko.*

class UIAnkoFootball : AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout{
            lparams(matchParent, wrapContent)
            padding = dip(5)

            imageView{
                id = iv_adapter_football
                setImageDrawable(resources.getDrawable(R.drawable.ic_launcher_background))
            }.lparams(200,200){
                rightMargin = dip(10)
            }

            textView{
                id = tv_adapter_football
                textSize = 16f
                textColor = Color.BLACK
                text = context.getString(R.string.loading)
            }.lparams(matchParent, 200){
                gravity = Gravity.CENTER_VERTICAL
            }

        }
    }
    companion object {
        const val iv_adapter_football = 0
        const val tv_adapter_football = 1
    }
}
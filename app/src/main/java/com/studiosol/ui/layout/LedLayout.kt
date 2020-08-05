package com.studiosol.ui.layout

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.studiosol.R


class LedLayout(
    context: Context,
    layoutParams: ViewGroup.LayoutParams,
    ledWidth: Int = 100,
    ledHeight: Int = 100
) : LinearLayout(context) {

    private val ledList = listOf(ImageView(context), ImageView(context), ImageView(context))
    private val scale = context.resources.displayMetrics.density

    init {
        this.orientation = HORIZONTAL
        this.layoutParams = layoutParams
        ledList.forEach {
            val params = ViewGroup.LayoutParams(
                (ledWidth * scale + 0.5f).toInt(),
                (ledHeight * scale + 0.5f).toInt()
            )
            it.layoutParams = params
            it.visibility = View.GONE
            this.addView(it)
        }
    }

    fun setNumber(num:String) {
        setNumber(num.toInt())
    }

    fun setNumber(num:Int) {
        if (num < 0 || num > 999) {
            throw Exception("Invalid Number")
        }

        num.toString().forEachIndexed { i, a ->
            setDisplay(Character.getNumericValue(a), ledList[i])
        }
    }

    fun turnOffDisplay() {
        ledList.forEach {
            it.visibility = View.GONE
        }
    }

    private fun setDisplay(num: Int, display: ImageView) {
        println(num)
        display.visibility = View.VISIBLE
        when (num) {
            0 -> {
                display.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.led_0,
                        null
                    )
                )
            }
            1 -> {
                display.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.led_1,
                        null
                    )
                )
            }
            2 -> {
                display.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.led_2,
                        null
                    )
                )
            }
            3 -> {
                display.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.led_3,
                        null
                    )
                )
            }
            4 -> {
                display.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.led_4,
                        null
                    )
                )
            }
            5 -> {
                display.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.led_5,
                        null
                    )
                )
            }
            6 -> {
                display.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.led_6,
                        null
                    )
                )
            }
            7 -> {
                display.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.led_7,
                        null
                    )
                )
            }
            8 -> {
                display.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.led_8,
                        null
                    )
                )
            }
            else -> {
                display.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.led_9,
                        null
                    )
                )
            }
        }
    }
}
package com.studiosol.ui.layout

import android.content.Context
import android.util.AttributeSet
import android.view.Display
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updateLayoutParams
import com.studiosol.R
import com.studiosol.utils.showToastMessage

class LedLayout(
    context: Context,
    attr: AttributeSet,
    ledWidth: Int = 100,
    ledHeight: Int = 100
) : LinearLayout(context, attr) {

    private val ledList = listOf(ImageView(context), ImageView(context), ImageView(context))

    init {
        this.orientation = HORIZONTAL
        ledList.forEach {
            val params = it.layoutParams
            params.height = ledHeight
            params.width = ledWidth
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
package com.studiosol.ui.activity

import android.app.ActionBar
import android.app.PendingIntent.getActivity
import android.graphics.drawable.VectorDrawable
import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.studiosol.Constants
import com.studiosol.R
import com.studiosol.service.RandService
import com.studiosol.utils.showToastMessage
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private var magicNumber: Int? = null
    private var ledViewList: List<ImageView>? = null
    private var attemps = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ledViewList = listOf(ledDisplayLeft, ledDisplayMiddle, ledDisplayRight)
        // setting listeners
        setSendButtonListener()
        setNewMatchListener()

    }

    private fun setSendButtonListener() {
        sendButton.setOnClickListener {
            try {
                turnOffDisplay()
                val stringGuess = inputGuess.text.toString()
                val guess = stringGuess.toInt()
                if (stringGuess.length > 3 || guess < 0) {
                    showToastMessage(this, resources.getString(R.string.invalidNumberInput))
                    return@setOnClickListener
                }

                stringGuess.forEachIndexed { i, a ->
                    setDisplay(Character.getNumericValue(a), ledViewList!![i])
                }

                if (guess == magicNumber) {
                    setEndGame(1)
                    return@setOnClickListener
                }

                if (attemps >= 3) {
                    setEndGame(0)
                    return@setOnClickListener
                }

                attemps++
                setAttempsText()
                attemptResult.text = if (guess > magicNumber!!) {
                    resources.getString(R.string.itsGreater)
                } else {
                    resources.getString(R.string.itsLess)
                }

            } catch (nfe: NumberFormatException) {
                showToastMessage(this, resources.getString(R.string.randBadRequest))
            }
        }
    }

    private fun setNewMatchListener() {
        newMatch.setOnClickListener { setMagicNumber() }
    }

    private fun setMagicNumber() {
        newMatch.visibility = View.GONE
        RandService.getInstance(this).getRandomNum(
            {
                magicNumber = it.getInt("value")
                println(magicNumber!!)
                setNewGame()
            },
            {
                when (val statusCode = it.networkResponse?.statusCode) {
                    in 400..499 -> {
                        statusCode.toString().forEachIndexed { i, a ->
                            setDisplay(Character.getNumericValue(a), ledViewList!![i])
                        }
                        setEndGame(2)
                        showToastMessage(this, resources.getString(R.string.randBadRequest))
                    }
                    in 500..999 -> {
                        statusCode.toString().forEachIndexed { i, a ->
                            setDisplay(Character.getNumericValue(a), ledViewList!![i])
                        }
                        setEndGame(2)
                    }
                    else -> {
                        turnOffDisplay()
                        setEndGame(2)
                        showToastMessage(this, resources.getString(R.string.connectionError))
                    }
                }
            }
        )
    }

    private fun setNewGame() {
        turnOffDisplay()
        attemptResult.text = ""
        sendButton.isEnabled = true
        newMatch.visibility = View.GONE
        attemps = 1
        setAttempsText()
    }


    private fun setEndGame(status: Int) {
        attemptResult.text = when (status) {
            0 -> {
                resources.getString(R.string.lostGame)
            }
            1 -> {
                resources.getString(R.string.hit)
            }
            else -> {
                resources.getString(R.string.error)
            }
        }

        sendButton.isEnabled = false
        newMatch.visibility = View.VISIBLE
    }


    private fun setAttempsText() {
        val newText = "$attemps/${Constants.MAX_ATTEMPTS}"
        attemptsDisplay.text = newText
    }

    private fun turnOffDisplay() {
        ledViewList!!.forEach {
            it.visibility = View.GONE
        }
    }

    private fun setDisplay(num: Int, display: ImageView) {
        if (num  >= 10 || num <= -1) {
            showToastMessage(this, resources.getString(R.string.ledError))
            return
        }
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
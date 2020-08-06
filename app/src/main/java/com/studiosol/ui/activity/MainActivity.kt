package com.studiosol.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import com.studiosol.Constants
import com.studiosol.R
import com.studiosol.service.RandService
import com.studiosol.ui.layout.LedLayout
import com.studiosol.utils.showToastMessage
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private val ledLayout: LedLayout by lazy {
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        LedLayout(this, params)
    }

    private var magicNumber: Int? = null
    private var attemps = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        attachLedLayout()
        // setting listeners
        setSendButtonListener()
        setNewMatchListener()

        // setting app bar

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun attachLedLayout() {
        ledLayout.gravity = Gravity.CENTER
        container1.addView(ledLayout, 1)
        // set layout params
        val params = ledLayout.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(0, 40, 0, 0)
        ledLayout.layoutParams = params
    }

    private fun setSendButtonListener() {
        sendButton.setOnClickListener {
            try {
                ledLayout.turnOffDisplay()
                val guess = inputGuess.text.toString().toInt()
                ledLayout.setNumber(guess)

                if (guess == magicNumber!!) {
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
                        if (statusCode != null) {
                            ledLayout.setNumber(statusCode)
                        }
                        setEndGame(2)
                        showToastMessage(this, resources.getString(R.string.randBadRequest))
                    }
                    in 500..999 -> {
                        if (statusCode != null) {
                            ledLayout.setNumber(statusCode)
                        }
                        setEndGame(2)
                    }
                    else -> {
                        ledLayout.turnOffDisplay()
                        setEndGame(2)
                        showToastMessage(this, resources.getString(R.string.connectionError))
                    }
                }
            }
        )
    }

    private fun setNewGame() {
        ledLayout.turnOffDisplay()
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

}
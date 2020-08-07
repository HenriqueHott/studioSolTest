/**
 * @author: Henrique Ribeiro Hott
 */
package com.studiosol.ui.activity

import android.os.Bundle
import android.text.Editable
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.studiosol.MAX_ATTEMPTS
import com.studiosol.MAX_LIMIT_STANDARD
import com.studiosol.MIN_LIMIT_STANDARD
import com.studiosol.R
import com.studiosol.exception.InvalidNumberException
import com.studiosol.service.RandService
import com.studiosol.ui.layout.LedLayout
import com.studiosol.utils.showToastMessage
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Enum para o status de final de jogo.
 */
enum class EndGameStatus {
    WIN,
    LOST,
    ERROR,
}

/**
 * Classe com o funcionamento da principal e única acitivty do projeto
*/
class MainActivity : AppCompatActivity() {

    // Mensagens de fim de jogo
    private val endGameMessages = mapOf (
        EndGameStatus.WIN to R.string.hit,
        EndGameStatus.LOST to R.string.lostGame,
        EndGameStatus.ERROR to R.string.error
    )

    // Layout do display dos leds
    private val ledLayout: LedLayout by lazy {
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        LedLayout(this, params)
    }

    private var magicNumber: Int? = null // Número mágico do qual o jogador tenta adivinhar
    private var attemps = 1


    // Metódo pai invocado na hora da criação da activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        attachLedLayout()

        // Atribuindo listeners
        setSendButtonListener()
        setNewMatchListener()
    }

    // Metódo pai invocado na hora da criação do menu superior(AppBar)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appmenu, menu)
        return true
    }

    // Metódo pai invocado na hora que um item do menu superior é selecionado
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    /**
     * Método que anexa o layout do displays de led no layout da activity
     */
    private fun attachLedLayout() {
        ledLayout.gravity = Gravity.CENTER
        container1.addView(ledLayout, 1)

        // Criando parâmetros para layout dos leds
        val params = ledLayout.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(0, 40, 0, 0)
        ledLayout.layoutParams = params
    }

    /**
     * Atribuição listener que testa se o input do usuário corresponde ao número mágico
     * e em seguida o mostra na tela. Caso o input corresponda ao número, o botão de novo
     * jogo é exibido junto com a mensagem de "acertou", caso contrário, exibe o texto de
     * dica dizendo se o input enviado é maior ou menor.
     *
     *  Este Listener também realiza os tratamentos para os possíveis erros de input que
     *  podem acontecer.
     */
    private fun setSendButtonListener() {
        sendButton.setOnClickListener {
            try {
                val guess = inputGuess.text.toString().toInt()
                ledLayout.setNumber(guess)

                if (guess == magicNumber!!) {
                    setEndGame(EndGameStatus.WIN)
                    return@setOnClickListener
                }

                if (attemps >= MAX_ATTEMPTS) {
                    setEndGame(EndGameStatus.LOST)
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
                showToastMessage(this, resources.getString(R.string.noNumberInput))
            } catch (ine: InvalidNumberException) {
                showToastMessage(this, resources.getString(R.string.invalidNumberInput))
            }
        }
    }

    /**
     * Cria uma nova partida
     */
    private fun setNewMatchListener() {
        newMatch.setOnClickListener { setMagicNumber() }
    }

    /**
     * Realiza uma requisição para API através do service(RandService) para buscar
     * o novo número mágico aleatório.
     *
     * Este método também fica responsável pela definição dos listeners que fazem
     * o tratamento da resposta vinda do servidor e dos possíveis erros que podem
     * ocorrer com a requisição. Caso um erro aconteça  o status é exibido na tela
     * e o botão de jogar novamente é exibido.
     */
    private fun setMagicNumber() {
        newMatch.visibility = View.GONE
        RandService.getInstance(this).getRandomNum(
            MIN_LIMIT_STANDARD,
            MAX_LIMIT_STANDARD,
            {
                magicNumber = it.getInt("value")
                println(magicNumber!!) // Print para você vizualizar o número magico
                setNewGame()
            },
            {

                val statusCode = it.networkResponse?.statusCode
                if (statusCode == null) {
                    ledLayout.turnOffDisplay()
                    setEndGame(EndGameStatus.ERROR)
                    showToastMessage(this, resources.getString(R.string.connectionError))
                    return@getRandomNum
                }
                // Exibe erro de acordo com o status da reposta
                when (statusCode) {
                    in 400..499 -> {
                        ledLayout.setNumber(statusCode)
                        showToastMessage(this, resources.getString(R.string.randBadRequest))
                    }
                    in 500..999 -> {
                        ledLayout.setNumber(statusCode)
                    }
                    else -> {
                        ledLayout.turnOffDisplay()
                        showToastMessage(this, resources.getString(R.string.amenoDorime)) // status > 1000?
                    }
                }

                setEndGame(EndGameStatus.ERROR)
            }
        )
    }


    /**
     *  Configura a tela para um novo jogo.
     */
    private fun setNewGame() {
        ledLayout.setNumber(0)
        attemptResult.text = ""
        sendButton.isEnabled = true
        inputGuess.isEnabled = true
        inputGuess.setText("", TextView.BufferType.EDITABLE)
        newMatch.visibility = View.GONE
        attemps = 1
        setAttempsText()
    }

    /**
     *  Configura a tela para um novo jogo.
     */
    private fun setEndGame(status: EndGameStatus) {
        // Apesar de não existir no nosso endGameMessages um status que não seja um elemento do enum
        // ele não consegue em tempo de compilação aferir essa existência, legal né?
        attemptResult.text = endGameMessages[status]?.let { resources.getString(it) }

        sendButton.isEnabled = false
        inputGuess.isEnabled = false
        newMatch.visibility = View.VISIBLE
    }


    /**
     *  Exibe o número de tentativas.
     */
    private fun setAttempsText() {
        val newText = "$attemps/${MAX_ATTEMPTS}"
        attemptsDisplay.text = newText
    }

}
/**
 * @author: Henrique Ribeiro Hott
 */
package com.studiosol.ui.layout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginBottom
import com.studiosol.R
import com.studiosol.exception.InvalidNumberException
import com.studiosol.utils.convertDpToPixels

/**
 *
 * Classe de implementação do layout para os displays de leds de 7 segmentos.
 *
 * Descrição: São utilizada 3 ImageViews para representação de cada display. Para a representar
 * os dígitos, foi utilizado um conjunto de imagens vetoriais contendo a visualização de cada um
 * dos 10 dígitos existentes, tais imagens são atribuídas dinamicamente aos displays conforme o
 * número requisitado para exibição.
 *
 * O arquivo xml das imagens usadas nos displays podem ser encontradas em app/res/drawable.
 *
 * @param [context] Contexto em que o layout será criado.
 * @param [layoutParams] Conjunto de parâmetros relativos a posição dentro a ViewGroup sobre
 * a qual o layout será criado.
 * @param [ledHeight] Tamanho(int) em DP dos segmentos.
 * @param [ledWidth] Largura(int) em DP dos segmentos.
 *
 */
class LedLayout(
    context: Context,
    layoutParams: ViewGroup.LayoutParams,
    ledWidth: Int = 100,
    ledHeight: Int = 100
) : LinearLayout(context) {

    private val ledList = listOf(ImageView(context), ImageView(context), ImageView(context))

    init {
        // Configurações iniciais do layout
        this.orientation = HORIZONTAL
        this.layoutParams = layoutParams

        // Configuração das imagesView e inserção das mesmas no layout
        ledList.forEach {
            val params = MarginLayoutParams(
                convertDpToPixels(ledWidth, context),
                convertDpToPixels(ledHeight, context)
            )
            val marginX = convertDpToPixels(-12, context)
            params.setMargins(marginX,0, marginX, 0)
            it.layoutParams = params
            it.visibility = View.GONE
            this.addView(it)
        }
    }

    /**
     * Exibe um número no layout
     * @param [num] número a ser exibido
     * @throws InvalidNumberException
     * @throws NumberFormatException
     */
    fun setNumber(num:String) {
        setNumber(num.toInt())
    }

    /**
     * Exibe um número layout
     * @param [num] número a ser exibido(Máximo de 3 dígitos)
     * @throws InvalidNumberException
     * @throws NumberFormatException
     */
    fun setNumber(num:Int) {
        if (num < 0 || num > 999) {
            throw InvalidNumberException("Invalid number")
        }

        turnOffDisplay()
        num.toString().forEachIndexed { i, a ->
            setDisplay(Character.getNumericValue(a), ledList[i])
        }
    }

    /**
     * Retira a visualização  atual dos displays
     */
    fun turnOffDisplay() {
        ledList.forEach {
            it.visibility = View.GONE
        }
    }

    /**
     * Atribui a um dado display o dígito que será exibido
     * @param: [num] Dígito a ser exibido no display
     * @param [display] ImageView contendo o display a ser atribuído
     */
    private fun setDisplay(num: Int, display: ImageView) {
        display.visibility = View.VISIBLE
        display.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                resources.getIdentifier("led_$num", "drawable", context.packageName),
                null
            )
        )
    }
}
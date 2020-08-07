/**
 * @author: Henrique Ribeiro Hott
 */
package com.studiosol.utils

import android.content.Context

fun convertDpToPixels(dp: Int, context: Context) : Int {
    val scale = context.resources.displayMetrics.density // Escala usada para conversão da medida padrão em pixels para dp
    return (dp * scale + 0.5f).toInt()
}
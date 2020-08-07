package com.studiosol.utils

import android.content.Context
import android.widget.Toast

/**
 * @author Henrique Ribeiro Hott
 */


/**
 * Função para mostrar uma menssagem Toast em determinado contexto
 * @param: [Context] contexto no qual a menssagem será mostrada
 * @param [String] String contento a menssagem a ser exibida
 */
fun showToastMessage(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

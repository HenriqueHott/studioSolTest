/**
 * @author: Henrique Ribeiro Hott
 */
package com.studiosol.service

import android.content.Context
import com.android.volley.Request
import com.android.volley.VolleyError
import com.studiosol.RAND_ENDPOINT
import com.studiosol.utils.SingletonHolder
import org.json.JSONObject

/**
 * Classe para criação do service responsável por se comunicar com RandApi.
 */
class RandService(context: Context): BaseService(context) {

    // Objeto que fornerce implementação do getInstance para a classe
    companion object : SingletonHolder<RandService, Context>(::RandService)

    /**
     * Requisição do serviço para gerar um número aleatório da RandAPI.
     * @param [min] Limite inferior do número aleatório gerado.
     * @param [max] Limite superior para o número  aleatório gerado.
     */
    fun getRandomNum(
        min: Int,
        max: Int,
        listener: (JSONObject) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val params = mapOf("min" to min.toString(), "max" to max.toString())
        sendJsonObjectRequest(RAND_ENDPOINT, Request.Method.GET, listener, onError, params )
    }

}
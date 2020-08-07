/**
 * @author: Henrique Ribeiro Hott
 */
package com.studiosol.service

import android.content.Context
import android.net.Uri
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.studiosol.utils.SingletonHolder
import org.json.JSONObject
import java.net.URL
import java.net.URLEncoder

/**
 * Classe abstrata para definir o comportamento de como os services irão
 * realizar as requisições.
 *
 * A classe foi feita utilizando a biblioteca  oficial do Android chamada Volley
 * que fornece  uma interface mais "amigável" para a envio e tratamento das requisições.
 *
 * Link: https://developer.android.com/training/volley
 *
 * Nas classes filhas é implementado o padrão Singleton porém não consegui criar tal
 * padrão na classe base.
 *
 */


abstract class BaseService (context: Context) {

    // Fila de requisições do volley - As requisições instanciadas
    // são mantidas na fila aguardando o seu envio
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    /**
     * Adiciona uma requisição para a fila.
     * @param [req] Requisição a ser adicionada.
     **/
    private fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    /**
     *
     * Envia uma requisição para um dada url esperando um JSON object como resposta.
     * @param: [url] URL do serviço.
     * @param [method] Volley Enum(Int) para o metódo da requisição.
     * @param [listener] Listener para o tratamento da response.
     * @param [onError] Listener para o tratamento em response.
     * @param [params] Query params para a requisição.
     * @TODO Parâmetro JSON object para criação do body da requisição
     */
    protected fun sendJsonObjectRequest(
        url: String,
        method: Int,
        listener: (JSONObject) -> Unit,
        onError: (VolleyError) -> Unit,
        params: Map<String, String>? = null
    ) {

        // Formatação do map para query params
        var formattedUrl = url
        params?.let { it ->
            formattedUrl += "?" + it.map { "${it.key}=${URLEncoder.encode(it.value, "utf-8")}" }.joinToString("&")
        }

        // Json Object Request - Interface simples para decodificar respostas que tenham um JSON body
        val jsonObjectRequest = JsonObjectRequest(
            method,
            formattedUrl,
            null,
            Response.Listener(listener),
            Response.ErrorListener(onError)
        )

        addToRequestQueue(jsonObjectRequest)
    }

}


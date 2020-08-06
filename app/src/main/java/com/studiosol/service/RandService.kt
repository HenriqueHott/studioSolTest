package com.studiosol.service

import android.content.Context
import com.android.volley.Request
import com.android.volley.VolleyError
import com.studiosol.RAND_ENDPOINT
import com.studiosol.utils.SingletonHolder
import org.json.JSONObject

class RandService(context: Context): BaseService(context) {

    companion object : SingletonHolder<RandService, Context>(::RandService)

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
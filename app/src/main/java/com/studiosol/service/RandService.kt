package com.studiosol.service

import android.content.Context
import com.android.volley.Request
import com.android.volley.VolleyError
import com.studiosol.Constants
import com.studiosol.utils.SingletonHolder
import org.json.JSONObject

class RandService(context: Context): GenericService(context) {

    companion object : SingletonHolder<RandService, Context>(::RandService)

    fun getRandomNum(
        listener: (JSONObject) -> Unit,
        onError: (VolleyError) -> Unit,
        min: Int = 1,
        max: Int = 279
    ) {
        val params = mapOf("min" to min.toString(), "max" to max.toString())
        this.sendJsonObjectRequest(Constants.RAND_ENDPOINT, Request.Method.GET, listener, onError, params )
    }

}